package ru.vadim.hostel.apartment.repository;

import liquibase.pro.packaged.A;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.vadim.hostel.entity.Apartment;
import ru.vadim.hostel.repository.ApartmentRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class ApartmentRepositoryTest {

    @Autowired
    private ApartmentRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckWhenApartmentExistByNumber() {
        //given
        Apartment apartment = new Apartment();
        apartment.setNumber(12L);
        apartment.setCountOfRooms(34);
        underTest.save(apartment);
        //when
        boolean expected = underTest.findApartmentByNumber(12L).isPresent();
        //then
        assertThat(expected).isTrue();
    }

    @Test
    void itShouldCheckWhenApartmentIfNumberIsntExist() {
        //given
        Apartment apartment = new Apartment();
        apartment.setNumber(12L);
        //when
        boolean expected = underTest.findApartmentByNumber(12L).isPresent();
        //then
        assertThat(expected).isFalse();
    }
}