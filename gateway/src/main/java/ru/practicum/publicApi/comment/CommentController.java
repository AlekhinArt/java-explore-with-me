package ru.practicum.publicApi.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;


@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
@Validated
public class CommentController {

    private final CommentClient commentClient;

    @GetMapping("/events/comments/{commentId}")
    public ResponseEntity<Object> getComment(@Positive @PathVariable Long commentId) {
        log.info("Get comment commentId {}", commentId);
        return commentClient.getComment(commentId);
    }

    @GetMapping("/events/{eventId}/comments")
    public ResponseEntity<Object> getComments(@Positive @PathVariable Long eventId,
                                              @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                              @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get comments eventId {}, from: {}, size: {}", eventId, from, size);
        return commentClient.getComments(eventId, from, size);
    }

}
