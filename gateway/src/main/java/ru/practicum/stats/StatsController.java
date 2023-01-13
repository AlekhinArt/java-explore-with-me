package ru.practicum.stats;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.dto.HitDto;

import java.util.Collection;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
public class StatsController {
    private final StatsClient statsClient;

    @PostMapping("/hit")
    public void addStats(@RequestBody HitDto hit) {
        log.info("Add Stats: app: {}, uri: {}, api: {} ", hit.getApp(), hit.getUri(), hit.getIp());
        statsClient.addStats(hit);
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> getStats(@NonNull @RequestParam String start,
                                           @NonNull @RequestParam String end,
                                           @RequestParam(defaultValue = "", required = false) Collection<String> uris,
                                           @RequestParam(defaultValue = "false", required = false) Boolean unique) {
        log.info("Get Stats start: {}, end: {}, uris: {}, unique: {} ", start, end, uris, unique);
        return statsClient.getStats(start, end, uris, unique);
    }
}
