package ru.practicum.adminApi.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Setter
@Getter
@AllArgsConstructor
@Builder
public class UserDto {

    @NotNull
    @NotBlank(message = "The login cannot be empty.")
    private String name;
    @NotNull
    @NotBlank(message = "The email cannot be empty.")
    @Email(regexp = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}",
            message = "Электронная почта должна соответсвовать формату RFC 5322.")
    private String email;
}

