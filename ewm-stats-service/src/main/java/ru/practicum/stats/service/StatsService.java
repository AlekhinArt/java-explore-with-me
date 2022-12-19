package ru.practicum.stats.service;

import ru.practicum.stats.dto.StatsDto;
import ru.practicum.stats.model.Hit;

import java.util.Collection;

public interface StatsService {

    void addStats(Hit hit);

    Collection<StatsDto> getStats(String start, String end, Collection<String> uris, Boolean unique);
}
