package ru.practicum.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.event.model.Event;


import java.util.List;
@AllArgsConstructor
@Builder
@Setter
@Getter
public class CompilationDto {

    private Long id;

    private boolean pinned;

    private String title;

    private List<Event> events;
}
