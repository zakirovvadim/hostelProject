package ru.vadim.hostel.apartment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vadim.hostel.entity.Category;
import ru.vadim.hostel.entity.dto.CategoryDto;
import ru.vadim.hostel.mapper.CategoryMapper;
import ru.vadim.hostel.mapper.CategoryMapperImpl;
import ru.vadim.hostel.repository.CategoryRepository;
import ru.vadim.hostel.service.CategoryService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;
    private CategoryService categoryService;
    private CategoryMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new CategoryMapperImpl();
        categoryService = new CategoryService(repository, mapper);
    }

    @Test
    void itShouldSaveNewCategoryAndReturn() {
        // given
        Category category = getCategory();
        // when
        Category saved = mapper.map(categoryService.save(mapper.map(category)));
        // then
        assertThat(saved).isEqualTo(category);
    }

    @Test
    void itShouldShowCountOfRequestToDeleteMethod() {
        // given
        Category category = mock(Category.class);
        category.setId(1L);
        when(repository.findCategoryById(1L)).thenReturn(Optional.ofNullable(category));
        // when
        categoryService.deleteCategory(1L);
        // then
        verify(repository, times(1)).deleteById(category.getId());
    }

    @Test
    void itShouldGetAllCategories() {
        //when
        categoryService.getAllCategories();
        // then
        verify(repository).findAll();
    }

    @Test
    void itShouldGetCategoryById() {
        // given
        Category category = getCategory();
        Optional<Category> categoryMock = Optional.of(category);
        // when
        when(repository.findById(1L)).thenReturn(categoryMock);
        // then
        assertEquals(category, categoryService.getCategoryById(1L));
    }

    @Test
    void itShouldFindCategoryByName() {
        // given
        Category category = new Category();
        category.setId(1L);
        category.setName("Luxury");
        category.setDescription("Luxury");
        Optional<Category> categoryMock = Optional.of(category);
        // when
        when(repository.findCategoryByName(category.getName())).thenReturn(categoryMock);
        // given
        assertEquals(category, mapper.map(categoryService.findByName(category.getName())));
    }
    public Category getCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Luxury");
        category.setDescription("Luxury");
        return category;
    }
}