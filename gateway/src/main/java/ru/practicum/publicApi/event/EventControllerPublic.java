package ru.practicum.publicApi.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.publicApi.event.dto.Sort;
import ru.practicum.stats.StatsClient;
import ru.practicum.stats.dto.HitDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
public class EventControllerPublic {
    private final EventClientPublic eventClient;
    private final StatsClient statsClient;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/events")
    public ResponseEntity<Object> getEvents(HttpServletRequest request,
                                            @RequestParam(required = false, defaultValue = "") String text,
                                            @RequestParam(required = false, defaultValue = "") List<String> categories,
                                            @RequestParam(required = false, defaultValue = "false") Boolean paid,
                                            @RequestParam(required = false, defaultValue = "") String rangeStart,
                                            @RequestParam(required = false, defaultValue = "") String rangeEnd,
                                            @RequestParam(required = false, defaultValue = "false") Boolean onlyAvailable,
                                            @RequestParam(required = false, defaultValue = "EVENT_DATE") Sort sort,
                                            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {

        sendHit(request);
        if (rangeEnd.isBlank()) rangeEnd = LocalDateTime.now().plusYears(50).format(formatter);
        if (rangeStart.isBlank()) rangeStart = LocalDateTime.now().minusYears(50).format(formatter);
        log.info("Get events with params text: {}, categories: {}, paid: {}, rangeStart: {}, rangeEnd: {}," +
                        " onlyAvailable: {}, sort: {}, from: {}, size: {};", text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size);
        return eventClient.getEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<Object> getEvent(HttpServletRequest request,
                                           @Positive @PathVariable Long eventId) {
        sendHit(request);
        log.info("get event with id: {};", eventId);
        return eventClient.getEvent(eventId);

    }

    private void sendHit(HttpServletRequest request) {
        statsClient.addStats(HitDto.builder()
                .app("ewm-main-service")
                .ip(request.getRemoteAddr())
                .uri(request.getRequestURI())
                .build());
    }
}
