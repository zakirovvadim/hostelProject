package ru.vadim.hostel.apartment.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vadim.hostel.entity.Apartment;
import ru.vadim.hostel.entity.Category;
import ru.vadim.hostel.entity.dto.ApartmentDto;
import ru.vadim.hostel.entity.dto.CategoryDto;
import ru.vadim.hostel.mapper.ApartmentMapper;
import ru.vadim.hostel.mapper.ApartmentMapperImpl;
import ru.vadim.hostel.mapper.CategoryMapper;
import ru.vadim.hostel.mapper.CategoryMapperImpl;
import ru.vadim.hostel.repository.ApartmentRepository;
import ru.vadim.hostel.repository.CategoryRepository;
import ru.vadim.hostel.service.ApartmentService;
import ru.vadim.hostel.service.CategoryService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApartmentServiceTest {
    @Mock
    ApartmentRepository apartmentRepository;
    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    ApartmentService apartmentService;
    @Mock
    CategoryService categoryService;
    ApartmentMapper apartmentMapper;
    CategoryMapper categoryMapper;

    @BeforeEach
    void setUp() {
        apartmentMapper = new ApartmentMapperImpl();
        categoryMapper = new CategoryMapperImpl();
        apartmentService = new ApartmentService(apartmentRepository, apartmentMapper, new CategoryMapperImpl(), categoryService);
    }

    @Test
    void itShouldSaveNewApartmentAndReturnApartment() {
        // given
        Apartment apartment = new Apartment();
        apartment.setId(1L);
        apartment.setNumber(15L);
        apartment.setCountOfRooms(12);
        // when
        Apartment saved = apartmentMapper.map(apartmentService.save(apartmentMapper.map(apartment)));
        // then
        assertThat(saved).isEqualTo(apartment);

    }

    @Test
    void itShouldShowCountOfDeleteRequestWhenDeleting() {
        // given
        Apartment apartment = mock(Apartment.class);
        apartment.setNumber(1L);
        when(apartmentRepository.findApartmentByNumber(1l)).thenReturn(Optional.ofNullable(apartment));
        // when
        apartmentService.delete(1L);
        // then
        verify(apartmentRepository, times(1)).deleteById(apartment.getNumber());
    }

    @Test
    void itShouldReturnApartmentByNumber() {
        // given
        Apartment apartment = new Apartment();
        apartment.setId(1L);
        apartment.setNumber(15L);
        apartment.setCountOfRooms(12);
        Optional<Apartment> apartmentMock = Optional.of(apartment);
        // when
        when(apartmentRepository.findApartmentByNumber(15L)).thenReturn(apartmentMock);
        // then
        assertEquals(apartment.getCountOfRooms(), apartmentService.getCountOfRooms(15L));

    }

    @Test
    void appointCategoryToApartment() {
        //given
        Category category = getCategory();
        Apartment apartment = getApartment();

        // when
        when(apartmentRepository.findApartmentByNumber(apartment.getNumber())).thenReturn(Optional.of(apartment));
        when(categoryService.getCategoryById(category.getId())).thenReturn(category);

        apartmentService.appointCategoryToApartment(apartment.getNumber(), category.getId());
        CategoryDto actual = categoryMapper.map(category);
        ApartmentDto expected = apartmentMapper.map(apartment);

        // then
        assertTrue(new ReflectionEquals(expected.getCategory()).matches(actual));
    }

    public Apartment getApartment() {
        Apartment apartment = new Apartment();
        apartment.setId(1L);
        apartment.setNumber(15L);
        apartment.setCountOfRooms(12);
        return apartment;
    }

    public Category getCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Luxury");
        category.setDescription("Luxury");
        return category;
    }
}