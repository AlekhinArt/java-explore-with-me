package ru.practicum.event.dto;

import lombok.Builder;
import lombok.Getter;
import ru.practicum.location.LocationDto;
@Builder
@Getter
public class NewEventDto {
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private boolean paid;
    private Long participantLimit;
    private String title;
    private LocationDto location;
    private boolean requestModeration;
}
