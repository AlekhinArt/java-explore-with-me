package ru.practicum.request.dto;

import lombok.*;
import ru.practicum.request.model.StateRequest;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParticipationRequestDto {

    private Long id;
    private String created;
    private Long event;
    private long requester;
    private StateRequest status;
}
