package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.model.State;
import ru.practicum.event.service.EventService;
import ru.practicum.exceptions.NotFoundException;
import ru.practicum.exceptions.ValidationException;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.mapper.RequestMapper;
import ru.practicum.request.model.ParticipationRequests;
import ru.practicum.request.model.StateRequest;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final EventService eventService;
    private final UserService userService;

    @Override
    public Collection<ParticipationRequestDto> getRequestsThisUser(Long userId, Long eventId) {
        userService.getById(userId);
        eventService.getEvent(eventId);
        return requestRepository.getParticipationRequestsByEvent(eventId)
                .stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto confirmOtherRequestsThisUser(Long userId, Long eventId, Long reqId) {
        userService.getById(userId);
        EventFullDto eventFullDto = eventService.getEvent(eventId);
        ParticipationRequests request = getById(reqId);
        Collection<ParticipationRequests> requests = requestRepository.getParticipationRequestsByEvent(eventId);
        if (eventFullDto.getConfirmedRequests() != 0 || eventFullDto.isRequestModeration()) {
            int countRequests = requestRepository.countByEventAndStatusEquals(eventId, StateRequest.CONFIRMED);
            if (countRequests == eventFullDto.getParticipantLimit()) {
                throw new ValidationException("The limit of participants int the event has been reached");
            }
            if ((eventFullDto.getParticipantLimit() - countRequests) == 1) {
                requests = requests.stream()
                        .filter(req -> req.getStatus() == StateRequest.PENDING)
                        .collect(Collectors.toList());
                requests.forEach(req -> req.setStatus(StateRequest.CANCELED));
                requestRepository.saveAll(requests);
            }
        }
        request.setStatus(StateRequest.CONFIRMED);

        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    public ParticipationRequestDto rejectOtherRequestsThisUser(Long userId, Long eventId, Long reqId) {
        userService.getById(userId);
        eventService.getEvent(eventId);
        ParticipationRequests requests = getById(reqId);
        requests.setStatus(StateRequest.REJECTED);
        return RequestMapper.toParticipationRequestDto(requestRepository.save(requests));
    }

    @Override
    public Collection<ParticipationRequestDto> getRequestsThisUserOtherEvents(Long userId) {
        return requestRepository.findAllByRequester(userId)
                .stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto addRequestsThisUserOtherEvents(Long userId, Long eventId) {
        userService.getById(userId);
        EventFullDto eventFullDto = eventService.getEvent(eventId);
        if (!(eventFullDto.getState().equals(State.PUBLISHED.toString()))) {
            throw new ValidationException("The event " + eventId + " not been published");
        }
        if (eventFullDto.getInitiator().getId() == userId) {
            throw new ValidationException("The initiator of the event cannot add a request to participate in his event.");
        }
        int countRequests = requestRepository.countByEventAndStatusEquals(eventId, StateRequest.CONFIRMED);
        if (countRequests == eventFullDto.getParticipantLimit()) {
            throw new ValidationException("The limit of participants int the event has been reached");
        }
        ParticipationRequests participationRequest = ParticipationRequests.builder()
                .requester(userId)
                .created(LocalDateTime.now())
                .event(eventId)
                .status(eventFullDto.isRequestModeration() ? StateRequest.PENDING : StateRequest.CONFIRMED)
                .build();
        return RequestMapper.toParticipationRequestDto(requestRepository.save(participationRequest));
    }

    @Override
    public ParticipationRequestDto cancelRequestsThisUserOtherEvents(Long userId, Long requestId) {
        userService.getById(userId);
        ParticipationRequests requests = getById(requestId);
        requests.setStatus(StateRequest.CANCELED);
        return RequestMapper.toParticipationRequestDto(requestRepository.save(requests));
    }

    private ParticipationRequests getById(long requestId) {
        return requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("ParticipationRequests with id: " + requestId + " not found"));
    }
}
