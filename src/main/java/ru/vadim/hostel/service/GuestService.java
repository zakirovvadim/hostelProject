package ru.vadim.hostel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vadim.hostel.entity.Apartment;
import ru.vadim.hostel.entity.Guest;
import ru.vadim.hostel.entity.dto.ApartmentDto;
import ru.vadim.hostel.entity.dto.GuestDto;
import ru.vadim.hostel.exception.NoEntityException;
import ru.vadim.hostel.mapper.ApartmentMapper;
import ru.vadim.hostel.mapper.GuestMapper;
import ru.vadim.hostel.repository.GuestRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestService {
    private final GuestRepository repository;
    private final GuestMapper guestMapper;

    private final ApartmentService apartmentService;
    private final ApartmentMapper apartmentMapper;

    public GuestDto save(GuestDto guestDto) {
        Guest guest = guestMapper.map(guestDto);
        return guestMapper.map(repository.save(guest));
    }

    public boolean delete(Long passportNumber) {
        Guest guest = repository.findGuestByPassport(passportNumber).orElseThrow(() -> new NoEntityException(passportNumber));
        repository.deleteById(guest.getId());
        return true;
    }

    public List<GuestDto> getGuests() {
        return guestMapper.map(repository.findAll());
    }

    public GuestDto updateGuest(GuestDto guestDto) {
        Guest currentGuest = repository.findGuestByPassport(guestDto.getPassport()).orElseThrow(() -> new NoEntityException(guestDto.getPassport()));
        Guest updatedGuest = guestMapper.map(guestDto);
        updatedGuest.setId(currentGuest.getId());
        return guestMapper.map(repository.save(updatedGuest));
    }


    public GuestDto appointGuestToApartment(Long passportNumber, Long apartmentNumber) {

        Guest guest = repository.findGuestByPassport(passportNumber).orElseThrow(() -> new NoEntityException(passportNumber));
        ApartmentDto apartmentDto = apartmentService.getApartmentByNumber(apartmentNumber);

        guest.setApartment(apartmentMapper.map(apartmentDto));

        Apartment apartment = apartmentMapper.map(apartmentDto);

        List<Guest> guestList = apartment.getGuests();
        if (guestList != null) {
            guestList.add(guest);
        } else {
            apartment.setGuests(new ArrayList<>());
            apartment.getGuests().add(guest);
        }
        apartmentService.save(apartmentMapper.map(apartment));
        return guestMapper.map(repository.save(guest));
    }
}