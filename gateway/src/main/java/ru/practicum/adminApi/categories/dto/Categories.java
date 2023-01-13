package ru.practicum.adminApi.categories.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.valid.Create;
import ru.practicum.valid.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Categories {
    @NotNull(groups = {Update.class})
    @Positive(groups = {Update.class})

    int id;
    @NotNull(groups = {Create.class})
    @NotBlank(groups = {Create.class})
    String name;
}
