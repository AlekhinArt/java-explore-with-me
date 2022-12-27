package ru.practicum.privateApi.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@Slf4j
@RestController
@RequestMapping(path = "/users/{userId}")
@RequiredArgsConstructor
@Validated
public class RequestController {

    private final RequestClient requestClient;

    @GetMapping("/events/{eventId}/requests")
    public ResponseEntity<Object> getRequestsThisUser(@Positive @PathVariable Long userId,
                                                      @Positive @PathVariable Long eventId) {
        log.info("Get requests created this User userId: {}, eventId: {};", userId, eventId);
        return requestClient.getRequestsThisUser(userId, eventId);
    }

    @PatchMapping("/events/{eventId}/requests/{reqId}/confirm")
    public ResponseEntity<Object> confirmOtherRequestsThisUser(@Positive @PathVariable Long userId,
                                                               @Positive @PathVariable Long eventId,
                                                               @Positive @PathVariable Long reqId) {
        log.info("Confirm requests created this User userId: {}, eventId: {}, reqId: {};", userId, eventId, reqId);
        return requestClient.confirmOtherRequestsThisUser(userId, eventId, reqId);
    }

    @PatchMapping("/events/{eventId}/requests/{reqId}/reject")
    public ResponseEntity<Object> rejectOtherRequestsThisUser(@Positive @PathVariable Long userId,
                                                              @Positive @PathVariable Long eventId,
                                                              @Positive @PathVariable Long reqId) {
        log.info("Reject requests created this User userId: {}, eventId: {}, reqId: {};", userId, eventId, reqId);
        return requestClient.rejectOtherRequestsThisUser(userId, eventId, reqId);
    }

    @GetMapping("/requests")
    public ResponseEntity<Object> getRequestsThisUserOtherEvents(@Positive @PathVariable Long userId) {
        log.info("Get requests created this User other events userId: {}", userId);
        return requestClient.getRequestsThisUserOtherEvents(userId);
    }

    @PostMapping("/requests")
    public ResponseEntity<Object> addRequestsThisUserOtherEvents(@Positive @PathVariable Long userId,
                                                                 @Positive @RequestParam Long eventId) {
        log.info("Add requests created this User other events userId: {}, eventId: {}", userId, eventId);
        return requestClient.addRequestsThisUserOtherEvents(userId, eventId);
    }

    @PatchMapping("/requests/{requestId}/cancel")
    public ResponseEntity<Object> cancelRequestsThisUserOtherEvents(@Positive @PathVariable Long userId,
                                                                    @Positive @PathVariable Long requestId) {
        log.info("Cancel requests created this User other events userId: {}, eventId: {}", userId, requestId);
        return requestClient.cancelRequestsThisUserOtherEvents(userId, requestId);
    }

}
