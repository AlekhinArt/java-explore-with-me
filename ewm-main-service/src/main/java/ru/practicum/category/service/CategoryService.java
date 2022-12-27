package ru.practicum.category.service;

import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.model.Category;

import java.util.Collection;

public interface CategoryService {
    CategoryDto getCategory(Long catId);

    Collection<CategoryDto> getCategories(Integer from, Integer size);

    void deleteCategory(Long catId);

    CategoryDto createCategory(Category category);

    CategoryDto changeCategory(Category category);
}
