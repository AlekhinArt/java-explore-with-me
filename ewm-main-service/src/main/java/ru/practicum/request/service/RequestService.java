package ru.practicum.request.service;

import ru.practicum.request.dto.ParticipationRequestDto;

import java.util.Collection;

public interface RequestService {
    Collection<ParticipationRequestDto> getRequestsThisUser(Long userId, Long eventId);

    ParticipationRequestDto confirmOtherRequestsThisUser(Long userId, Long eventId, Long reqId);

    ParticipationRequestDto rejectOtherRequestsThisUser(Long userId, Long eventId, Long reqId);

    Collection<ParticipationRequestDto> getRequestsThisUserOtherEvents(Long userId);


    ParticipationRequestDto addRequestsThisUserOtherEvents(Long userId, Long eventId);

    ParticipationRequestDto cancelRequestsThisUserOtherEvents(Long userId, Long requestId);
}
