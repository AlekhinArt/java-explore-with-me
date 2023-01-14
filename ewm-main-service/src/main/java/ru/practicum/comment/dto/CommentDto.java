package ru.practicum.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private Long event;
    private Long author;
    private String message;
    private String creatTime;
}
