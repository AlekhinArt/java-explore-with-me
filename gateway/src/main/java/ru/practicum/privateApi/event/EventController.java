package ru.practicum.privateApi.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.privateApi.event.dto.EventDto;
import ru.practicum.privateApi.event.dto.EventUpdateDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Slf4j
@RestController
@RequestMapping(path = "/users/{userId}")
@RequiredArgsConstructor
@Validated
public class EventController {

    private final EventClient eventClient;

    @GetMapping("/events")
    public ResponseEntity<Object> getEventsCreatedThisUser(@Positive @PathVariable Long userId,
                                                           @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                           @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get events created this User userId: {}, from: {}, size: {};", userId, from, size);
        return eventClient.getEventsCreatedThisUser(userId, from, size);
    }

    @PatchMapping("/events")
    public ResponseEntity<Object> changeEventCreatedThisUser(@Positive @PathVariable Long userId,
                                                             @Valid @RequestBody EventUpdateDto eventDto) {
        log.info("Change events created this User userId: {}, eventDto: {};", userId, eventDto);
        return eventClient.changeEventCreatedThisUser(userId, eventDto);
    }

    @PostMapping("/events")
    public ResponseEntity<Object> createEventThisUser(@Positive @PathVariable Long userId,
                                                      @Valid @RequestBody EventDto eventDto) {
        log.info("Create events created this User userId: {}, eventDto: {};", userId, eventDto);
        return eventClient.createEventThisUser(userId, eventDto);
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<Object> getEventCreatedThisUser(@Positive @PathVariable Long userId,
                                                          @Positive @PathVariable Long eventId) {
        log.info("Get events created this User userId: {}, eventId: {};", userId, eventId);
        return eventClient.getEventCreatedThisUser(userId, eventId);
    }

    @PatchMapping("/events/{eventId}")
    public ResponseEntity<Object> cancelEventCreatedThisUser(@Positive @PathVariable Long userId,
                                                             @Positive @PathVariable Long eventId) {
        log.info("Cancel events created this User userId: {}, eventId: {};", userId, eventId);
        return eventClient.cancelEventCreatedThisUser(userId, eventId);
    }
}