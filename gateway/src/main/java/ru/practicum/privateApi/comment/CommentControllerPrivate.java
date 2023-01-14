package ru.practicum.privateApi.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.privateApi.comment.dto.CommentDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
@Validated
public class CommentControllerPrivate {

    private final CommentClientPrivate commentClientAdmin;

    @PostMapping("/users/{userId}/events/{eventId}/comments")
    public ResponseEntity<Object> createComment(@Positive @PathVariable Long userId,
                                                @Positive @PathVariable Long eventId,
                                                @Valid @RequestBody CommentDto commentDto) {
        log.info("Create comment, userId{}, eventId {}, comment {}", userId, eventId, commentDto);
        return commentClientAdmin.createComment(userId, eventId, commentDto);
    }

    @DeleteMapping("/users/{userId}/comments/{commentId}")
    public ResponseEntity<Object> deleteComment(@Positive @PathVariable Long userId,
                                                @Positive @PathVariable Long commentId) {
        log.info("Delete comment, userId{}, commentId {}", userId, commentId);
        return commentClientAdmin.deleteComment(commentId, userId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/comments")
    public ResponseEntity<Object> changeAbilityToComment(@Positive @PathVariable Long userId,
                                                         @Positive @PathVariable Long eventId) {
        log.info("Change ability to comment eventId {}, userId {}", eventId, userId);
        return commentClientAdmin.changeAbilityToComment(userId, eventId);
    }
}
