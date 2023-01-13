package ru.practicum.privateApi.event.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class EventDto {
    @NotBlank
    @Size(max = 2000, message = "the maximum annotation size is 2000 characters")
    @Size(min = 20, message = "the minimum annotation size is 20 characters")
    private String annotation;
    private int category;
    @NotBlank
    @Size(max = 10000, message = "the maximum description size is 10000 characters")
    @Size(min = 2, message = "the minimum description size is 2 characters")
    private String description;
    @NotNull
    private String eventDate;
    private boolean paid;
    private Long participantLimit;
    @NotBlank
    @Size(max = 150, message = "the maximum title size is 150 characters")
    @Size(min = 2, message = "the minimum title size is 2 characters")
    private String title;
    @NotNull
    private Location location;
    private boolean requestModeration;


}
