package ru.practicum.adminApi.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.adminApi.dto.EventUpdateDto;
import ru.practicum.adminApi.dto.State;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
@Validated
public class EventControllerAdmin {
    private final EventClientAdmin adminClient;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @GetMapping
    public ResponseEntity<Object> searchEvents(@RequestParam(required = false, defaultValue = "") List<String> users,
                                               @RequestParam(required = false, defaultValue = "") List<State> states,
                                               @RequestParam(required = false, defaultValue = "") List<String> categories,
                                               @RequestParam(required = false, defaultValue = "") String rangeStart,
                                               @RequestParam(required = false, defaultValue = "") String rangeEnd,
                                               @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                               @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        if (rangeEnd.isBlank()) rangeEnd = LocalDateTime.now().plusYears(50).format(formatter);
        if (rangeStart.isBlank()) rangeStart = LocalDateTime.now().format(formatter);
        log.info("SearchEvents with users: {}, states: {}, categories: {},rangeStart: {}," +
                " rangeEnd: {}, from: {}, size: {};", users, states, categories, rangeStart, rangeEnd, from, size);

        return adminClient.searchEvents(users, states, categories, rangeStart, rangeEnd, from, size);

    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Object> updateEvent(@Positive @PathVariable Long eventId,
                                              @RequestBody EventUpdateDto eventDto) {
        log.info("UpdateEvent eventId: {}, EventDto: {};", eventId, eventDto);
        return adminClient.updateEvent(eventId, eventDto);
    }

    @PatchMapping("/{eventId}/publish")
    public ResponseEntity<Object> publishEvent(@Positive @PathVariable Long eventId) {
        log.info("PublishEvent eventId: {};", eventId);
        return adminClient.publishEvent(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public ResponseEntity<Object> rejectEvent(@Positive @PathVariable Long eventId) {
        log.info("RejectEvent eventId: {};", eventId);
        return adminClient.rejectEvent(eventId);
    }
}
