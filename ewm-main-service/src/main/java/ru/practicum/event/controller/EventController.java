package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.*;
import ru.practicum.event.model.State;
import ru.practicum.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor

public class EventController {
    private final EventService eventService;

    //admin
    @GetMapping("/admin/events")
    public Collection<EventFullDto> searchEvents(@RequestParam(required = false, defaultValue = "") List<Long> users,
                                                 @RequestParam(required = false, defaultValue = "") List<State> states,
                                                 @RequestParam(required = false, defaultValue = "") List<Long> categories,
                                                 @RequestParam(required = false, defaultValue = "") String rangeStart,
                                                 @RequestParam(required = false, defaultValue = "") String rangeEnd,
                                                 @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                 @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("SearchEvents with users: {}, states: {}, categories: {},rangeStart: {}," +
                " rangeEnd: {}, from: {}, size: {};", users, states, categories, rangeStart, rangeEnd, from, size);

        return eventService.searchEvents(users, states, categories, rangeStart, rangeEnd, from, size);

    }

    @PutMapping("/admin/events/{eventId}")
    public EventFullDto updateEvent(@PathVariable Long eventId,
                                    @RequestBody AdminUpdateEventRequest eventDto) {
        log.info("UpdateEvent eventId: {}, EventDto: {};", eventId, eventDto);
        return eventService.updateEvent(eventId, eventDto);
    }

    @PatchMapping("/admin/events/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable Long eventId) {
        log.info("PublishEvent eventId: {};", eventId);
        return eventService.publishEvent(eventId);
    }

    @PatchMapping("/admin/events/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable Long eventId) {
        log.info("RejectEvent eventId: {};", eventId);
        return eventService.rejectEvent(eventId);
    }

    //private path = "/users/{userId}")
    @GetMapping("/users/{userId}/events")
    public Collection<EventShortDto> getEventsCreatedThisUser(@PathVariable Long userId,
                                                              @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                              @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get events created this User userId: {}, from: {}, size: {};", userId, from, size);
        return eventService.getEventsCreatedThisUser(userId, from, size);
    }

    @PatchMapping("/users/{userId}/events")
    public EventFullDto changeEventCreatedThisUser(@PathVariable Long userId,
                                                   @RequestBody EventUpdateDto eventDto) {
        log.info("Change events created this User userId: {}, eventDto: {};", userId, eventDto);
        return eventService.changeEventCreatedThisUser(userId, eventDto);
    }

    @PostMapping("/users/{userId}/events")
    public EventFullDto createEventThisUser(@PathVariable Long userId,
                                            @RequestBody NewEventDto eventDto) {
        log.info("Create events created this User userId: {}, eventDto: {};", userId, eventDto);
        return eventService.createEventThisUser(userId, eventDto);
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    public EventFullDto getEventCreatedThisUser(@PathVariable Long userId,
                                                @PathVariable Long eventId) {
        log.info("Get events created this User userId: {}, eventId: {};", userId, eventId);
        return eventService.getEventCreatedThisUser(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public EventFullDto cancelEventCreatedThisUser(@PathVariable Long userId,
                                                   @PathVariable Long eventId) {
        log.info("Cancel events created this User userId: {}, eventId: {};", userId, eventId);
        return eventService.cancelEventCreatedThisUser(userId, eventId);
    }

    //public
    @GetMapping("/events")
    public Collection<EventShortDto> getEvents(HttpServletRequest request,
                                               @RequestParam(required = false, defaultValue = "") String text,
                                               @RequestParam(required = false, defaultValue = "") List<Long> categories,
                                               @RequestParam(required = false, defaultValue = "false") Boolean paid,
                                               @RequestParam(required = false, defaultValue = "") String rangeStart,
                                               @RequestParam(required = false, defaultValue = "") String rangeEnd,
                                               @RequestParam(required = false, defaultValue = "false") Boolean onlyAvailable,
                                               @RequestParam(required = false, defaultValue = "EVENT_DATE") String sort,
                                               @RequestParam(name = "from", defaultValue = "0") Integer from,
                                               @RequestParam(name = "size", defaultValue = "10") Integer size) {

        log.info("Get events with params text: {}, categories: {}, paid: {}, rangeStart: {}, rangeEnd: {}," +
                        " onlyAvailable: {}, sort: {}, from: {}, size: {};", text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size);
        return eventService.getEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping("/events/{eventId}")
    public EventFullDto getEvent(@PathVariable Long eventId) {

        log.info("get event with id: {};", eventId);
        return eventService.getEvent(eventId);

    }

}
