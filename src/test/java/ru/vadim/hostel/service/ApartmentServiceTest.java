package ru.vadim.hostel.service;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.vadim.hostel.entity.Apartment;
import ru.vadim.hostel.exception.NoEntityException;
import ru.vadim.hostel.mapper.ApartmentMapper;
import ru.vadim.hostel.mapper.CategoryMapper;
import ru.vadim.hostel.repository.ApartmentRepository;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApartmentServiceTest {

    @Mock
    private ApartmentRepository apartmentRepository;
    @Autowired
    private  ApartmentMapper mapper;
    private  CategoryMapper categoryMapper;
    private  CategoryService categoryService;

    ApartmentService apartmentService;
    @Autowired
    ApartmentMapper apartmentMapper;

    @BeforeEach
    void setUp(){
        apartmentService = new ApartmentService(apartmentRepository, mapper, categoryMapper, categoryService);
    }

    @Test
    void getApartmentByNumber() {
        Apartment apartment = new Apartment();
        apartment.setNumber(1L);
        apartment.setCountOfRooms(12);
        apartment.setDateOfCleaning(LocalDate.of(2022, 02, 07));

        //when

        //then
        verify(apartmentRepository, times(1)).findApartmentByNumber(1L);
    }



//    @Test
//    void save() {
//        Apartment apartment = new Apartment();
//        apartment.setNumber(1L);
//        apartment.setCountOfRooms(12);
//        apartment.setDateOfCleaning(LocalDate.of(2022, 02, 07));
//
//        Mockito.when(apartmentService.save(apartmentMapper.map(apartment))).thenReturn(apartmentMapper.map(apartment));
//    }
//
//    @Test
//    void delete() throws Exception {
//        Apartment apartment = new Apartment();
//        apartment.setId(1L);
//        apartment.setNumber(1L);
//        apartment.setCountOfRooms(12);
//        apartment.setDateOfCleaning(LocalDate.of(2022, 02, 07));
//
//        doNothing().when(apartmentRepository.deleteById(1L));
//        assertEquals(apartmentMapper.map(apartmentService.getApartmentByNumber(1L)), apartment);
////        apartmentService.delete(1L);
////        Mockito.verify(apartmentRepository, Mockito.times(1)).deleteById(1L);
//    }
//
//    @Test
//    void getApartmentByNumber() {
//        Apartment apartment = new Apartment();
//        apartment.setId(1L);
//        apartment.setNumber(1L);
//        apartment.setCountOfRooms(12);
//        apartment.setDateOfCleaning(LocalDate.of(2022, 02, 07));
//
//
//
//        Mockito.doReturn(apartment).when(apartmentRepository).findApartmentByNumber(1L);
//
//        Mockito.when(apartmentService.getApartmentByNumber(1L)).thenReturn(apartmentMapper.map(apartment));
//    }
//
//    @Test
//    void appointCategoryToApartment() {
//    }
}