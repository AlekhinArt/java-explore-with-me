package ru.practicum.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.service.RequestService;

import java.util.Collection;


@Slf4j
@RestController
@RequestMapping(path = "/users/{userId}")
@RequiredArgsConstructor

public class RequestController {

    private final RequestService requestService;

    @GetMapping("/events/{eventId}/requests")
    public Collection<ParticipationRequestDto> getRequestsThisUser(@PathVariable Long userId,
                                                                   @PathVariable Long eventId) {
        log.info("Get requests created this User userId: {}, eventId: {};", userId, eventId);
        return requestService.getRequestsThisUser(userId, eventId);
    }

    @PatchMapping("/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmOtherRequestsThisUser(@PathVariable Long userId,
                                                                @PathVariable Long eventId,
                                                                @PathVariable Long reqId) {
        log.info("Confirm requests created this User userId: {}, eventId: {}, reqId: {};", userId, eventId, reqId);
        return requestService.confirmOtherRequestsThisUser(userId, eventId, reqId);
    }

    @PatchMapping("/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectOtherRequestsThisUser(@PathVariable Long userId,
                                                               @PathVariable Long eventId,
                                                               @PathVariable Long reqId) {
        log.info("Reject requests created this User userId: {}, eventId: {}, reqId: {};", userId, eventId, reqId);
        return requestService.rejectOtherRequestsThisUser(userId, eventId, reqId);
    }

    @GetMapping("/requests")
    public Collection<ParticipationRequestDto> getRequestsThisUserOtherEvents(@PathVariable Long userId) {
        log.info("Get requests created this User other events userId: {}", userId);
        return requestService.getRequestsThisUserOtherEvents(userId);
    }

    @PostMapping("/requests")
    public ParticipationRequestDto addRequestsThisUserOtherEvents(@PathVariable Long userId,
                                                                  @RequestParam Long eventId) {
        log.info("Add requests created this User other events userId: {}, eventId: {}", userId, eventId);
        return requestService.addRequestsThisUserOtherEvents(userId, eventId);
    }

    @PatchMapping("/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequestsThisUserOtherEvents(@PathVariable Long userId,
                                                                     @PathVariable Long requestId) {
        log.info("Cancel requests created this User other events userId: {}, eventId: {}", userId, requestId);
        return requestService.cancelRequestsThisUserOtherEvents(userId, requestId);
    }

}
