package ru.practicum.publicApi.categories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
public class CategoryController {
    private final CategoryClient categoryClient;

    @GetMapping("/categories")
    public ResponseEntity<Object> getCategories(@PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get categories with params from: {}, size: {};", from, size);
        return categoryClient.getCategories(from, size);
    }

    @GetMapping("/categories/{catId}")
    public ResponseEntity<Object> getCategory(@Positive @PathVariable Long catId) {
        log.info("Get categories with params catId: {};", catId);
        return categoryClient.getCategory(catId);
    }

}
