package ru.practicum.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.model.Category;
import ru.practicum.category.service.CategoryService;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PatchMapping("/admin/categories")
    public CategoryDto changeCategory(@RequestBody Category category) {
        log.info("Change category categories {}", category);
        return categoryService.changeCategory(category);
    }

    @PostMapping("/admin/categories")
    public CategoryDto createCategory(@RequestBody Category category) {
        log.info("Create category categories: {}", category);
        return categoryService.createCategory(category);
    }

    @DeleteMapping("/admin/categories/{catId}")
    public void deleteCategory(@PathVariable Long catId) {
        log.info("Delete category catId: {}", catId);
        categoryService.deleteCategory(catId);
    }

    @GetMapping("/categories")
    public Collection<CategoryDto> getCategories(@RequestParam(name = "from", defaultValue = "0") Integer from,
                                                 @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get categories with params from: {}, size: {};", from, size);
        return categoryService.getCategories(from, size);
    }

    @GetMapping("/categories/{catId}")
    public CategoryDto getCategory(@PathVariable Long catId) {
        log.info("Get categories with params catId: {};", catId);
        return categoryService.getCategory(catId);
    }
}
