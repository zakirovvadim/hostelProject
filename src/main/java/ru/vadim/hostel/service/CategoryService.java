package ru.vadim.hostel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vadim.hostel.entity.Category;
import ru.vadim.hostel.entity.dto.CategoryDto;
import ru.vadim.hostel.exception.NoEntityException;
import ru.vadim.hostel.mapper.CategoryMapper;
import ru.vadim.hostel.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper categoryMapper;

    public CategoryDto save(CategoryDto categoryDto) {
        Category category = categoryMapper.map(categoryDto);
        return categoryMapper.map(repository.save(category));
    }

    public void deleteCategory(Long id) {
        Category category = repository.findCategoryById(id).orElseThrow(() -> new NoEntityException(id));
        repository.deleteById(category.getId());
    }

    public List<CategoryDto> getAllCategories() {
        return categoryMapper.map(repository.findAll());
    }

    public CategoryDto getCategoryById(Long id) {
        return categoryMapper.map(repository.findById(id).orElseThrow(() -> new NoEntityException(id)));
    }

    public CategoryDto findByName(String name) {
        return categoryMapper.map(repository.findCategoryByName(name).orElse(new Category()));
    }
}