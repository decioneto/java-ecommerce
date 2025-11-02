package com.dailycodework.dream_shops.service.category;

import com.dailycodework.dream_shops.exceptions.AlreadyExistsException;
import com.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.dailycodework.dream_shops.model.Category;
import com.dailycodework.dream_shops.repository.CateogoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
    private final CateogoryRepository cateogoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return cateogoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return cateogoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return cateogoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(c -> !cateogoryRepository.existsByName(c.getName()))
                .map(cateogoryRepository::save)
                .orElseThrow(() -> new AlreadyExistsException(category.getName() + "already exists"));
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        return Optional.ofNullable(getCategoryById(categoryId))
                .map(oldCategory -> {
                    oldCategory.setName(category.getName());
                    return cateogoryRepository.save(oldCategory);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public void deleteCategoryById(Long id) {
        cateogoryRepository.findById(id)
                .ifPresentOrElse(cateogoryRepository::delete, () -> { throw new ResourceNotFoundException("Category not found"); });
    }
}
