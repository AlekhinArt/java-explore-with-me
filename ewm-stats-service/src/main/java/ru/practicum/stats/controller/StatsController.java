package ru.practicum.stats.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.dto.HitDto;
import ru.practicum.stats.dto.StatsDto;
import ru.practicum.stats.model.Hit;
import ru.practicum.stats.service.StatsService;

import java.util.Collection;

@RestController
@Slf4j
@RequiredArgsConstructor
public class StatsController {
    private final StatsService statsService;

    @PostMapping("/hit")
    public void addStats(@RequestBody HitDto hit) {
        log.info("Add Stats: app: {}, uri: {}, api: {} ", hit.getApp(), hit.getUri(), hit.getIp());
        statsService.addStats(hit);
    }

    @GetMapping("/stats")
    public Collection<StatsDto> getStats(@RequestParam(defaultValue = "") String start,
                                         @RequestParam(defaultValue = "") String end,
                                         @RequestParam(defaultValue = "", required = false) Collection<String> uris,
                                         @RequestParam(defaultValue = "false", required = false) Boolean unique) {
        log.info("Get Stats start: {}, end: {}, uris: {}, unique: {} ", start, end, uris, unique);
        return statsService.getStats(start, end, uris, unique);
    }

}
