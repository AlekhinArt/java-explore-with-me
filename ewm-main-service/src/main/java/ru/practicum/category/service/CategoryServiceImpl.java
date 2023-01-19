package ru.practicum.category.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.exceptions.DataConflictException;
import ru.practicum.exceptions.NotFoundException;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto getCategory(Long catId) {
        return CategoryMapper.toCategoryDto(categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Category with catId: " + catId + " not found")));

    }

    @Override
    public Collection<CategoryDto> getCategories(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return categoryRepository.findAll(pageable).stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Long catId) {
        try {
            categoryRepository.deleteById(catId);
        } catch (Exception e) {
            log.debug("updateUser.NotFoundException(Пользователь не найден)");
            throw new NotFoundException("Пользователь не найден");
        }
    }

    @Override
    public CategoryDto createCategory(Category category) {
        Category newCategory;
        try {
            newCategory = categoryRepository.save(category);
        } catch (Exception e) {
            throw new DataConflictException("Category name: " + category.getName() + " is already exist ");
        }
        return CategoryMapper.toCategoryDto(newCategory);
    }

    @Override
    public CategoryDto changeCategory(Category category) {
        Category changeCategory;
        try {
            changeCategory = categoryRepository.save(category);
        } catch (Exception e) {
            throw new DataConflictException("Category name: " + category.getName() + " is already exist ");
        }
        return CategoryMapper.toCategoryDto(changeCategory);

    }
}
