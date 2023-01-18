package ru.practicum.comment.mapper;

import ru.practicum.comment.dto.ShowCommentDto;
import ru.practicum.comment.model.Comment;

import java.time.format.DateTimeFormatter;

public class CommentMapper {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public static ShowCommentDto toShowCommentDto(Comment comment) {
        return ShowCommentDto.builder()
                .message(comment.getMessage())
                .authorName(comment.getAuthor().getName())
                .creatTime(comment.getCreateTime().format(formatter))
                .build();
    }
}
