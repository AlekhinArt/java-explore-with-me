package ru.practicum.stats.mapper;

import ru.practicum.stats.dto.StatsDto;
import ru.practicum.stats.model.Hit;

public class StatsMapper {

    public StatsDto statsToDto(Hit hit) {
        return StatsDto.builder()
                .app(hit.getApp())
                .uri(hit.getUri())
                //.hits()
                .build();

    }
}
