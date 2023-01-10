package ru.practicum.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.category.model.Category;
import ru.practicum.location.LocationDto;
import ru.practicum.user.dto.UserShortDto;


@Data
@Builder
@AllArgsConstructor
public class EventFullDto {

    private Long id;
    private String annotation;
    private Category category;
    private int confirmedRequests;
    private String createdOn;
    private String description;
    private String eventDate;
    private UserShortDto initiator;
    private LocationDto location;
    private boolean paid;
    private long participantLimit;
    private String publishedOn;
    private boolean requestModeration;
    private String state;
    private String title;
    private long views;
}
