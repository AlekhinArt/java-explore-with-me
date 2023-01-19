package ru.practicum.event.service;

import ru.practicum.event.dto.*;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.State;

import java.util.Collection;
import java.util.List;

public interface EventService {

    Collection<EventFullDto> searchEvents(List<Long> users, List<State> states, List<Long> categories, String rangeStart, String rangeEnd, Integer from, Integer size);

    EventFullDto updateEvent(Long eventId, AdminUpdateEventRequest eventDto);

    EventFullDto publishEvent(Long eventId);

    EventFullDto rejectEvent(Long eventId);

    Collection<EventShortDto> getEventsCreatedThisUser(Long userId, Integer from, Integer size);

    EventFullDto changeEventCreatedThisUser(Long userId, EventUpdateDto eventDto);

    EventFullDto createEventThisUser(Long userId, NewEventDto eventDto);

    EventFullDto getEventCreatedThisUser(Long userId, Long eventId);

    EventFullDto cancelEventCreatedThisUser(Long userId, Long eventId);

    Collection<EventShortDto> getEvents(String text, List<Long> categories, Boolean paid, String rangeStart, String rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size);

    EventFullDto getEvent(Long eventId);

    Event getEventById(Long eventId);
}
