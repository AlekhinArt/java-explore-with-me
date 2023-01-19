package ru.practicum.comment.service;

import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.ShowCommentDto;

import java.util.Collection;

public interface CommentService {
    ShowCommentDto createComment(Long userId, Long eventId, CommentDto commentDto);

    ShowCommentDto deleteComment(Long commentId, Long userId);

    Boolean changeAbilityToComment(Long userId, Long eventId);

    ShowCommentDto getComment(Long commentId);

    Collection<ShowCommentDto> getComments(Long eventId, Integer from, Integer size);

    Collection<ShowCommentDto> getCommentsOneUser(Long userId, Integer from, Integer size);
}
