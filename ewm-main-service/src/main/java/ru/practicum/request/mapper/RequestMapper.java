package ru.practicum.request.mapper;

import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.model.ParticipationRequests;

import java.time.format.DateTimeFormatter;

public class RequestMapper {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static ParticipationRequestDto toParticipationRequestDto(ParticipationRequests participationRequests) {
        return ParticipationRequestDto.builder()
                .requester(participationRequests.getRequester())
                .id(participationRequests.getId())
                .event(participationRequests.getEvent())
                .status(participationRequests.getStatus())
                .created(participationRequests.getCreated().format(formatter))
                .build();
    }
}
