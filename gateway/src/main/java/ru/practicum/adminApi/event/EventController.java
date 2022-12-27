package ru.practicum.adminApi.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.adminApi.dto.State;
import ru.practicum.privateApi.event.dto.EventDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
@Validated
public class EventController {
    private final EventClient adminClient;


    @GetMapping
    public ResponseEntity<Object> searchEvents(@RequestParam(required = false, defaultValue = "") List<Long> users,
                                               @RequestParam(required = false, defaultValue = "") List<State> states,
                                               @RequestParam(required = false, defaultValue = "") List<Long> categories,
                                               @RequestParam(required = false, defaultValue = "") String rangeStart,
                                               @RequestParam(required = false, defaultValue = "") String rangeEnd,
                                               @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                               @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("SearchEvents with users: {}, states: {}, categories: {},rangeStart: {}," +
                " rangeEnd: {}, from: {}, size: {};", users, states, categories, rangeStart, rangeEnd, from, size);

        return adminClient.searchEvents(users, states, categories, rangeStart, rangeEnd, from, size);

    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Object> updateEvent(@Positive @PathVariable Long eventId,
                                              EventDto eventDto){
        log.info("UpdateEvent eventId: {}, EventDto: {};", eventId, eventDto);
        return adminClient.updateEvent(eventId,eventDto);
    }

    @PatchMapping("/{eventId}/publish")
    public ResponseEntity<Object> publishEvent(@Positive @PathVariable Long eventId){
        log.info("PublishEvent eventId: {};", eventId);
        return adminClient.publishEvent(eventId);
    }
}
