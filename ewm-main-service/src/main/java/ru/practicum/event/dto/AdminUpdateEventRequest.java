package ru.practicum.event.dto;

import lombok.*;
import ru.practicum.location.Location;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminUpdateEventRequest {

    private String annotation;
    private long category;
    private String description;
    private String eventDate;
    private boolean paid;
    private long participantLimit;
    private String title;
    private Location location;
    private boolean requestModeration;
}
