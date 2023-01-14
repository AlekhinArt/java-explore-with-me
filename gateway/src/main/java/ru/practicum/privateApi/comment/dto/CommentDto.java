package ru.practicum.privateApi.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private Long event;
    private Long author;
    @NotBlank
    @NotNull
    @Size(max = 2000, message = "the message annotation size is 2000 characters")
    @Size(min = 20, message = "the minimum annotation size is 2 characters")
    private String message;
    private String creatTime;
}