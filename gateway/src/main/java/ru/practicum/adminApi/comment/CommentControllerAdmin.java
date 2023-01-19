package ru.practicum.adminApi.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Slf4j
@RestController
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
@Validated
public class CommentControllerAdmin {
    private final CommentClientAdmin commentClientAdmin;

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Object> deleteComment(@Positive @PathVariable Long commentId) {
        log.info("Delete comment {}", commentId);
        return commentClientAdmin.deleteComment(commentId);
    }

    @PatchMapping("/events/{eventId}/comments")
    public ResponseEntity<Object> changeAbilityToComment(@Positive @PathVariable Long eventId) {
        log.info("Change ability to comment eventId {}", eventId);
        return commentClientAdmin.changeAbilityToComment(eventId);
    }

    @GetMapping("/admin/comments/{userId}")
    public ResponseEntity<Object> getCommentsOneUser(@Positive @PathVariable Long userId,
                                                     @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                     @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get comments userId {}, from: {}, size: {}", userId, from, size);
        return commentClientAdmin.getCommentsOneUser(userId, from, size);
    }
}
