package ru.practicum.adminApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.privateApi.event.dto.Location;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class EventUpdateDto {
    private String annotation;
    private long category;
    private String description;
    private String eventDate;
    private boolean paid;
    private Long participantLimit;
    private String title;
    private Location location;
    private boolean requestModeration;
}
