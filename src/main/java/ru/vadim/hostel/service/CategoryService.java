package ru.vadim.hostel.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import ru.vadim.hostel.entity.Category;
import ru.vadim.hostel.entity.dto.CategoryDto;
import ru.vadim.hostel.exception.NoEntityException;
import ru.vadim.hostel.mapper.CategoryMapper;
import ru.vadim.hostel.repository.CategoryRepository;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"category"})
public class CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper categoryMapper;
    private final static Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    @CacheEvict(value = "category", allEntries = true)
    public CategoryDto save(CategoryDto categoryDto) {
        Category category = categoryMapper.map(categoryDto);
        return categoryMapper.map(repository.save(category));
    }

    @CacheEvict(value = "category", allEntries = true)
    public void deleteCategory(Long id) {
        Category category = repository.findCategoryById(id).orElseThrow(() -> new NoEntityException(id));
        repository.deleteById(category.getId());
    }

    @Cacheable
    public List<CategoryDto> getAllCategories() {
        return categoryMapper.map(repository.findAll());
    }

    @Cacheable(value = "category", key = "id")
    public CategoryDto getCategoryById(Long id) {
        LOG.info("Trying to get category information for id {} ", id);
        return categoryMapper.map(repository.findById(id).orElseThrow(() -> new NoEntityException(id)));
    }

    public CategoryDto findByName(String name) {
        return categoryMapper.map(repository.findCategoryByName(name).orElse(new Category()));
    }
}