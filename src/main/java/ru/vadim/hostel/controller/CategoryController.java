package ru.vadim.hostel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('category:create')")
    public ResponseEntity<CategoryDto> saveCategory(@RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(service.save(categoryDto), HttpStatus.OK);
    }

    //Удаление категории
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('category:delete')")
    public ResponseEntity<Void> deleteCategory(@PathVariable(name = "id") Long id) {
        service.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

    //Получение списка категорий
    @GetMapping(value = "")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public List<CategoryDto> getAllCategories() {
        return service.getAllCategories();
    }
}