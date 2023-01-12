package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.client.StatsClient;
import ru.practicum.event.dto.*;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.State;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exceptions.NotFoundException;
import ru.practicum.exceptions.ValidationException;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;
import ru.practicum.user.service.UserService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserService userService;
    private final CategoryRepository categoryRepository;
    private final StatsClient statsClient;
    private final UserRepository userRepository;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Collection<EventFullDto> searchEvents(List<Long> usersIds,
                                                 List<State> states,
                                                 List<Long> categoriesIds,
                                                 String rangeStart,
                                                 String rangeEnd,
                                                 Integer from,
                                                 Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        List<Category> categories = new ArrayList<>(categoryRepository.findAllByIdIn(categoriesIds));
        List<User> users = userRepository.findAllByIdIn(usersIds);
        LocalDateTime start = LocalDateTime.parse(rangeStart, formatter);
        LocalDateTime end = LocalDateTime.parse(rangeEnd, formatter);
        return eventRepository.findAllWithParam(users, states, categories, start, end, pageable)
                .stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateEvent(Long eventId, AdminUpdateEventRequest eventDto) {
        Event event = getEventById(eventId);
        event.setPaid(eventDto.isPaid());
        event.setRequestModeration(event.isRequestModeration());
        if (eventDto.getEventDate() != null) {
            event.setEventDate(LocalDateTime.parse(eventDto.getEventDate(), formatter));
        }
        if (eventDto.getCategory() != 0) {
            event.setCategory(getCategory(eventDto.getCategory()));
        }
        if (eventDto.getDescription() != null) {
            event.setDescription(eventDto.getDescription());
        }
        if (eventDto.getAnnotation() != null) {
            event.setAnnotation(eventDto.getAnnotation());
        }
        if (eventDto.getTitle() != null) {
            event.setTitle(eventDto.getTitle());
        }
        if (eventDto.getLocation() != null) {
            event.setLocation(eventDto.getLocation());
        }
        if (eventDto.getParticipantLimit() != 0) {
            event.setParticipantLimit(eventDto.getParticipantLimit());
        }
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto publishEvent(Long eventId) {
        Event event = getEventById(eventId);
        if (event.getState() != State.PENDING) {
            throw new ValidationException("The event must be in the publication waiting state");
        }
        if (event.getEventDate().plusHours(1).isBefore(LocalDateTime.now())) {
            throw new ValidationException("The event cannot be published earlier than an hour before the start");
        }
        event.setState(State.PUBLISHED);
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto rejectEvent(Long eventId) {
        Event event = getEventById(eventId);
        if (event.getState().equals(State.PUBLISHED)) {
            throw new ValidationException("The event should not be published");
        }
        event.setState(State.CANCELED);
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }


    @Override
    public Collection<EventShortDto> getEventsCreatedThisUser(Long userId, Integer from, Integer size) {
        User user = userService.getById(userId);
        Pageable pageable = PageRequest.of(from / size, size);
        return eventRepository.findAllByInitiator(user, pageable).stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto changeEventCreatedThisUser(Long userId, EventUpdateDto eventDto) {
        userService.getById(userId);
        Event event = getEventById(eventDto.getEventId());
        Category category = getCategory(eventDto.getCategory());
        if (event.getState() == State.PUBLISHED) {
            throw new ValidationException("Only pending or canceled events can be changed");
        }

        event.setState(State.PENDING);
        event.setDescription(eventDto.getDescription());
        event.setAnnotation(eventDto.getAnnotation());
        event.setPaid(eventDto.getPaid());
        event.setTitle(eventDto.getTitle());
        event.setCategory(category);
        event.setParticipantLimit(eventDto.getParticipantLimit());
        event.setEventDate(eventDto.getEventDate() == null ? null : LocalDateTime.parse(eventDto.getEventDate(), formatter));

        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto createEventThisUser(Long userId, NewEventDto eventDto) {
        User user = userService.getById(userId);
        Category category = getCategory(eventDto.getCategory());
        Event event = EventMapper.toEvent(eventDto);
        event.setInitiator(user);
        event.setCategory(category);
        event.setCreatedOn(LocalDateTime.now());
        event.setState(State.PENDING);
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }


    @Override
    public EventFullDto getEventCreatedThisUser(Long userId, Long eventId) {
        User user = userService.getById(userId);
        Event event = getEventById(eventId);
        if (event.getInitiator() != user) {
            throw new ValidationException("The user did not create this event");
        }
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public EventFullDto cancelEventCreatedThisUser(Long userId, Long eventId) {
        Event event = getEventById(eventId);
        if (event.getState().equals(State.PUBLISHED)) {
            throw new ValidationException("The event must be in the publication waiting state");
        }
        event.setState(State.CANCELED);
        return EventMapper.toEventFullDto(eventRepository.save(event));

    }

    @Override
    public Collection<EventShortDto> getEvents(String text,
                                               List<Long> categories,
                                               Boolean paid,
                                               String rangeStart,
                                               String rangeEnd,
                                               Boolean onlyAvailable,
                                               String sort,
                                               Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        LocalDateTime rangeEndTime = LocalDateTime.parse(rangeEnd, formatter);
        LocalDateTime rangeStartTime = LocalDateTime.parse(rangeStart, formatter);
        Page<Event> events;
        if (onlyAvailable) {

            events = eventRepository.getEventsOnlyAvailable(text, text, categoryRepository.findAllByIdIn(categories),
                    paid, rangeEndTime, rangeStartTime, pageable);
            events.stream().forEach(event -> event.setViews(getViews(event.getId())));

        } else {

            events = eventRepository.findByAnnotationLikeIgnoreCaseOrDescriptionLikeIgnoreCaseAndCategoryInAndEventDateBetweenAndPaid(text, text, categoryRepository.findAllByIdIn(categories),
                    rangeEndTime, rangeStartTime, paid, pageable);
            events.stream().forEach(event -> event.setViews(getViews(event.getId())));
        }
        return events.stream().map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto getEvent(Long eventId) {
        return EventMapper.toEventFullDto(getEventById(eventId));
    }

    private Event getEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id: " + eventId + "not found."));
    }

    private Category getCategory(Long category) {
        return categoryRepository.findById(category)
                .orElseThrow(() -> new NotFoundException("Category with id: " + category + "not found."));
    }

    private Long getViews(Long id) {
        String uri = "/events/";
        String start = URLEncoder.encode(LocalDateTime.now().minusYears(1).toString(), StandardCharsets.UTF_8);
        String end = URLEncoder.encode(LocalDateTime.now().toString(), StandardCharsets.UTF_8);
        String parameterId = uri + id;

        ResponseEntity<Object> views = statsClient.getStats(start, end, parameterId, "false");
        List<ViewStats> v = (List<ViewStats>) views.getBody();
        return v.size() == 0 ? 0 : v.get(0).getHits();
    }

}
