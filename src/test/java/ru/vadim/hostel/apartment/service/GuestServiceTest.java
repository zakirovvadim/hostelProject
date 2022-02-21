package ru.vadim.hostel.apartment.service;

import liquibase.pro.packaged.G;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.SpringVersion;
import ru.vadim.hostel.entity.Apartment;
import ru.vadim.hostel.entity.Guest;
import ru.vadim.hostel.entity.dto.ApartmentDto;
import ru.vadim.hostel.mapper.ApartmentMapper;
import ru.vadim.hostel.mapper.GuestMapper;
import ru.vadim.hostel.mapper.GuestMapperImpl;
import ru.vadim.hostel.repository.ApartmentRepository;
import ru.vadim.hostel.repository.GuestRepository;
import ru.vadim.hostel.service.ApartmentService;
import ru.vadim.hostel.service.GuestService;

import static org.hamcrest.CoreMatchers.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GuestServiceTest {

    @Mock
    GuestRepository repository;
    @Mock
    ApartmentRepository apartmentRepository;
    @Mock
    ApartmentService apartmentService;
    @Mock
    ApartmentMapper apartmentMapper;
    @Autowired
    GuestService service;
    @Autowired
    GuestMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new GuestMapperImpl();
        service = new GuestService(repository, mapper, apartmentService, apartmentMapper);
    }

    @Test
    void tShouldSaveNewGuest() {
        // given
        Guest guest = getGuestForTest();
        // when
        Guest saved = mapper.map(service.save(mapper.map(guest)));
        // then
        assertThat(saved).isEqualTo(guest);
    }

    @Test
    void itShouldDeleteGuestByPassport() {
        // given
        Guest guest = mock(Guest.class);
        guest.setPassport("6959485768");
        when(repository.findGuestByPassport("6959485768")).thenReturn(Optional.ofNullable(guest));
        // when
        service.delete("6959485768");
        // then
        verify(repository, times(1)).findGuestByPassport("6959485768");
    }

    @Test
    void itShouldReturnAllGuests() {
        //when
        service.getGuests();
        // then
        verify(repository).findAll();
    }

    @Test
    void itShouldUpdateGuestWithoutUpdatePassport() {
        // given
        Guest guest = getGuestForTest();
        Guest newGuest = getGuestForTest();
        newGuest.setFirstname("Кира");
        newGuest.setPatronymic("Олеговна");
        newGuest.setLastname("Закирова");
        // when
        given(repository.findGuestByPassport(guest.getPassport())).willReturn(Optional.of(guest));
        service.updateGuest(mapper.map(newGuest));
        // then

        verify(repository).save(newGuest);
        verify(repository).findGuestByPassport(newGuest.getPassport());
    }

    @Test
    void itShouldShowGuestLivesInApartment() {
        // given
        List<Guest> guestList = new ArrayList<>();
        guestList.add(getGuestForTest());
        Optional<List<Guest>> guestListMock = Optional.of(guestList);
        // when
        when(repository.findGuestByApartmentNumber(1L)).thenReturn(guestListMock);
        // then
        Guest expectedGuest = mapper.map(service.getGuestsFromApart(1L).get(0));
        List<Guest> expectedListGuest = new ArrayList<>();
        expectedListGuest.add(expectedGuest);
        assertEquals(guestList, expectedListGuest);
    }

    @Test
    void appointGuestToApartment() {
//        // given
//        Guest guest = getGuestForTest();
//
//        Apartment apartment = new Apartment();
//        apartment.setId(1L);
//        apartment.setNumber(15L);
//        apartment.setCountOfRooms(12);
//        apartmentRepository.save(apartment);
//
//        ApartmentDto apartmentDto = apartmentMapper.map(apartment);
//
//        // when
//        given(repository.findGuestByPassport(guest.getPassport())).willReturn(Optional.of(guest));
//        given(apartmentRepository.findApartmentByNumber(apartment.getNumber())).willReturn(Optional.of(apartment));
//
//        service.appointGuestToApartment(guest.getPassport(), apartment.getNumber());
//        // then
//        verify(repository).save(guest);
    }


    public Guest getGuestForTest() {
        Guest guest = new Guest();
        guest.setBirthdate(LocalDate.of(1995, 7, 12));
        guest.setEndDate(LocalDate.of(2022, 7, 12));
        guest.setFirstname("Вадим");
        guest.setLastname("Закиров");
        guest.setPassport("6959485768");
        guest.setPatronymic("Галиевич");
        guest.setStartDate(LocalDate.of(2022, 7, 10));
        guest.setImagepath("C:.Users.vzakirov.Pictures");
        guest.setId(1L);
        return guest;
    }
}