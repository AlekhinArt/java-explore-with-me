package ru.practicum.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.ShowCommentDto;
import ru.practicum.comment.service.CommentService;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class CommentController {
    /*
    v 1.создать комментарий (Приват)
    v 2.получить комментарий(паблик)
    v 3.получить список комментариев для определнного события(паблик)
    v 4. получить список комментариев юзера (админ)
    v 5. удалить комментарий(приват)
    v 6. удалить комментарий(админ)
    v 7.разрешить/запретить комментировать своё событие (приват)
    v 8.разрешить комментировать события (админ)
    * */
    private final CommentService commentService;

    //private
    @PostMapping("/users/{userId}/events/{eventId}/comments")
    public ShowCommentDto createComment(@PathVariable Long userId,
                                        @PathVariable Long eventId,
                                        @RequestBody CommentDto commentDto) {

        return commentService.createComment(userId, eventId, commentDto);
    }

    @DeleteMapping("/users/{userId}/comments/{commentId}")
    public ShowCommentDto deleteComment(@PathVariable Long userId,
                                        @PathVariable Long commentId) {
        return commentService.deleteComment(commentId, userId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/comments")
    public Boolean changeAbilityToComment(@PathVariable Long userId,
                                          @PathVariable Long eventId) {
        return commentService.changeAbilityToComment(userId, eventId);
    }

    //public
    @GetMapping("/events/comments/{commentId}")
    public ShowCommentDto getComment(@PathVariable Long commentId) {
        return commentService.getComment(commentId);
    }

    @GetMapping("/events/{eventId}/comments")
    public Collection<ShowCommentDto> getComments(@PathVariable Long eventId,
                                                  @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                  @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return commentService.getComments(eventId, from, size);
    }

    //admin
    @DeleteMapping("/admin/comments/{commentId}")
    public ShowCommentDto deleteComment(@PathVariable Long commentId) {
        return commentService.deleteComment(commentId, null);
    }

    @PatchMapping("/admin/events/{eventId}/comments")
    public Boolean changeAbilityToComment(@PathVariable Long eventId) {
        return commentService.changeAbilityToComment(null, eventId);
    }

    @GetMapping("/admin/comments/{userId}")
    public Collection<ShowCommentDto> getCommentsOneUser(@PathVariable Long userId,
                                                         @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                         @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return commentService.getCommentsOneUser(userId, from, size);
    }

}
