package ru.practicum.privateApi.event.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@RequiredArgsConstructor
public class EventDto {
    @NotBlank
    @Size(max = 2000, message = "the maximum annotation size is 2000 characters")
    @Size(min = 20, message = "the minimum annotation size is 20 characters")
    private String annotation;
    private int category;
    @NotBlank
    @Size(max = 10000, message = "the maximum annotation size is 10000 characters")
    @Size(min = 2, message = "the minimum annotation size is 2 characters")
    private String description;
    @NotNull
    private String eventDate;
    private boolean paid;
    private Long participantLimit;
    @NotBlank
    @Size(max = 150, message = "the maximum header size is 150 characters")
    @Size(min = 2, message = "the minimum header size is 2 characters")
    private String title;
    @NotNull
    private LocationDto locationDto;
    private boolean requestModeration;


}
