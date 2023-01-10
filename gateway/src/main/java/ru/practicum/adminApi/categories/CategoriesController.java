package ru.practicum.adminApi.categories;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.adminApi.categories.dto.Categories;
import ru.practicum.valid.Create;
import ru.practicum.valid.Update;

import javax.validation.constraints.Positive;

@Slf4j
@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
@Validated
public class CategoriesController {
    private final CategoriesClientAdmin categoriesClient;

    @PatchMapping
    public ResponseEntity<Object> changeCategory(@Validated(Update.class) @RequestBody Categories categories) {
        log.info("Change category categories {}", categories);
        return categoriesClient.changeCategory(categories);
    }

    @PostMapping
    public ResponseEntity<Object> createCategory(@Validated(Create.class) @RequestBody Categories categories) {
        log.info("Create category categories {}", categories);
        return categoriesClient.createCategory(categories);
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<Object> deleteCategory(@Positive @PathVariable Integer catId) {
        log.info("Delete category catId {}", catId);
        return categoriesClient.deleteCategory(catId);
    }

}
