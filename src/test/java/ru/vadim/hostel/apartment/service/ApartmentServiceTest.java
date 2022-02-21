package ru.vadim.hostel.apartment.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vadim.hostel.entity.Apartment;
import ru.vadim.hostel.entity.Category;
import ru.vadim.hostel.entity.dto.ApartmentDto;
import ru.vadim.hostel.mapper.ApartmentMapper;
import ru.vadim.hostel.mapper.ApartmentMapperImpl;
import ru.vadim.hostel.mapper.CategoryMapperImpl;
import ru.vadim.hostel.repository.ApartmentRepository;
import ru.vadim.hostel.repository.CategoryRepository;
import ru.vadim.hostel.service.ApartmentService;
import ru.vadim.hostel.service.CategoryService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApartmentServiceTest {
    @Mock
    ApartmentRepository apartmentRepository;
    @Mock
    CategoryRepository categoryRepository;
    ApartmentService apartmentService;
    CategoryService categoryService;
    ApartmentMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ApartmentMapperImpl();
        categoryService = new CategoryService(categoryRepository, new CategoryMapperImpl());
        apartmentService = new ApartmentService(apartmentRepository, mapper, new CategoryMapperImpl(), categoryService);
    }

    @Test
    void itShouldSaveNewApartmentAndReturnApartment() {
        // given
        Apartment apartment = new Apartment();
        apartment.setId(1L);
        apartment.setNumber(15L);
        apartment.setCountOfRooms(12);
        // when
        Apartment saved = mapper.map(apartmentService.save(mapper.map(apartment)));
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
        assertEquals(apartment, mapper.map(apartmentService.getApartmentByNumber(15L)));

    }

    @Test
    void appointCategoryToApartment() {
                // given
//        Category category = new Category();
//        category.setId(1L);
//        category.setName("Luxury");
//        category.setDescription("Luxury");
//
//        Apartment apartment = new Apartment();
//        apartment.setId(1L);
//        apartment.setNumber(15L);
//        apartment.setCountOfRooms(12);
//        apartmentRepository.save(apartment);
//
//        // when
//        given(apartmentRepository.findApartmentByNumber(apartment.getNumber())).willReturn(Optional.of(apartment));
//        given(categoryRepository.findCategoryById(category.getId())).willReturn(Optional.of(category));
//        apartmentService.appointCategoryToApartment(apartment.getNumber(), category.getId());
//
//        // then
//        verify(apartmentRepository).findApartmentByNumber(apartment.getNumber());
//        verify(categoryRepository).findCategoryById(category.getId());
    }
}