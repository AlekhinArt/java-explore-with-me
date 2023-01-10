package ru.practicum.adminApi.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Setter
@Getter
@AllArgsConstructor
@Builder
public class UserDto {

    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String email;
}

