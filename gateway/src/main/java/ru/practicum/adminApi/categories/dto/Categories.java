package ru.practicum.adminApi.categories.dto;

import lombok.AllArgsConstructor;
import ru.practicum.valid.Create;
import ru.practicum.valid.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
@AllArgsConstructor
public class Categories {
    @NotNull(groups = {Update.class})
    @Positive(groups = {Update.class, Create.class})
    int id;
    @NotNull(groups = {Create.class})
    @NotBlank(groups = {Create.class})
    String name;
}
