package ru.practicum.event.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.category.model.Category;
import ru.practicum.user.dto.UserShortDto;
@Builder
@Setter
@Getter
public class EventShortDto {
    private long id;
    private String title;
    private String annotation;
    private Category category;
    private String eventDate;
    private Boolean paid;
    private long views;
    private UserShortDto initiator;
    private int confirmedRequests;

}
