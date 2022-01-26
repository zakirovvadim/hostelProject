package ru.vadim.hostel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vadim.hostel.entity.dto.CategoryDto;
import ru.vadim.hostel.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping(value = "api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService service;


    // Создание категории
    @PostMapping(value = "")
    public ResponseEntity<CategoryDto> saveCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto savedCategory = service.save(categoryDto);
        return new ResponseEntity<>(savedCategory, HttpStatus.OK);
    }

    //Удаление категории
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(name = "id") Long id) {
        final boolean deleted = service.deleteCategory(id);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    //Получение списка категорий
    @GetMapping(value = "")
    public List<CategoryDto> getAllCategories() {
        return service.getAllCategories();
    }

}
