package ru.practicum.stats.mapper;

import ru.practicum.stats.dto.HitDto;
import ru.practicum.stats.dto.StatsDto;
import ru.practicum.stats.model.Hit;

public class StatsMapper {

    public static StatsDto statsToDto(Hit hit) {
        return StatsDto.builder()
                .app(hit.getApp())
                .uri(hit.getUri())

                .build();

    }

    public static Hit toHit(HitDto hitDto) {
        return Hit.builder()
                .app(hitDto.getApp())
                .uri(hitDto.getUri())
                .ip(hitDto.getIp())
                .build();

    }
}
