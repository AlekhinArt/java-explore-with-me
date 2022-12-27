﻿package ru.practicum.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

@AllArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
}
