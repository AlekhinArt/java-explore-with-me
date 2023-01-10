package ru.practicum.event.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class EventUpdateDto {

    private String annotation;
    private long category;
    private String description;
    private String eventDate;
    private Long eventId;
    private Boolean paid;
    private Long participantLimit;
    private String title;
}
