package ru.vadim.hostel.apartment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vadim.hostel.entity.Apartment;
import ru.vadim.hostel.entity.Guest;
import ru.vadim.hostel.entity.dto.GuestDto;
import ru.vadim.hostel.mapper.ApartmentMapper;
import ru.vadim.hostel.mapper.CategoryMapper;
import ru.vadim.hostel.mapper.GuestMapper;
import ru.vadim.hostel.mapper.GuestMapperImpl;
import ru.vadim.hostel.repository.ApartmentRepository;
import ru.vadim.hostel.repository.GuestRepository;
import ru.vadim.hostel.service.ApartmentService;
import ru.vadim.hostel.service.CategoryService;
import ru.vadim.hostel.service.GuestService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    @InjectMocks
    GuestService guestService;

    GuestMapper mapper;
    @Mock
    CategoryMapper categoryMapper;
    @InjectMocks
    CategoryService categoryService;

    @BeforeEach
    void setUp() {
        mapper = new GuestMapperImpl();
//        apartmentService = new ApartmentService(apartmentRepository, apartmentMapper, categoryMapper, categoryService);
        guestService = new GuestService(repository, mapper, apartmentService, apartmentMapper);
    }

    @Test
    void tShouldSaveNewGuest() {
        // given
        Guest guest = getGuestForTest();
        // when
        Guest saved = mapper.map(guestService.save(mapper.map(guest)));
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
        guestService.delete("6959485768");
        // then
        verify(repository, times(1)).findGuestByPassport("6959485768");
    }

    @Test
    void itShouldReturnAllGuests() {
        //when
        guestService.getGuests();
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
        guestService.updateGuest(mapper.map(newGuest));
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
        Guest expectedGuest = mapper.map(guestService.getGuestsFromApart(1L).get(0));
        List<Guest> expectedListGuest = new ArrayList<>();
        expectedListGuest.add(expectedGuest);
        assertEquals(guestList, expectedListGuest);
    }

    @Test
    void appointGuestToApartment() {
        // given
        Guest guest = getGuestForTest();
        Apartment apartment = getApartmentForTest();

        // when
        when(repository.findGuestByPassport(guest.getPassport())).thenReturn(Optional.of(guest));
        when(apartmentService.getApartmentByNumber(15L)).thenReturn(apartment);

        GuestDto actual = guestService.appointGuestToApartment(guest.getPassport(), apartment.getNumber());
        GuestDto expected = mapper.map(guest);
        // then

        assertTrue(new ReflectionEquals(expected).matches(actual));
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

    public Apartment getApartmentForTest() {
        Apartment apartment = new Apartment();
        apartment.setId(1L);
        apartment.setNumber(15L);
        apartment.setCountOfRooms(12);
        return apartment;
    }
}