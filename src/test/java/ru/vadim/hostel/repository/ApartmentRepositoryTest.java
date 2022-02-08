package ru.vadim.hostel.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.vadim.hostel.entity.Apartment;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ApartmentRepositoryTest {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @AfterEach
    void tearDown() {
        apartmentRepository.deleteAll();
    }

    @Test
    void itShouldCheckIfApartmentWasFoundByNumber() {
        //given
        Apartment apartment = new Apartment();
        apartment.setNumber(1L);
        apartment.setCountOfRooms(12);
        apartment.setDateOfCleaning(LocalDate.of(2022, 02, 07));
        apartmentRepository.save(apartment);
        // when
        Apartment expectedApart = apartmentRepository.findApartmentByNumber(1L).get();
        // then
        assertThat(expectedApart.getNumber()).isEqualTo(apartment.getNumber());
        assertThat(expectedApart.getCountOfRooms()).isEqualTo(apartment.getCountOfRooms());
        assertThat(expectedApart.getDateOfCleaning()).isEqualTo(apartment.getDateOfCleaning());
    }
}