package ru.practicum.privateApi.event.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@RequiredArgsConstructor
public class EventUpdateDto {
    @NotNull
    private String annotation;
    private int category;
    private String description;
    @NotNull
    private String eventDate;
    private Long eventId;
    private Boolean paid;
    private Long participantLimit;
    @NotNull
    private String title;


}
