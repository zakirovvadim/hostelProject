package ru.vadim.hostel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vadim.hostel.entity.Guest;
import ru.vadim.hostel.entity.dto.GuestDto;
import ru.vadim.hostel.exception.NoEntityException;
import ru.vadim.hostel.mapper.GuestMapper;
import ru.vadim.hostel.repository.GuestRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestService {
    private final GuestRepository repository;
    private final GuestMapper guestMapper;

    public GuestDto save(GuestDto guestDto) {
        Guest guest = guestMapper.map(guestDto);
        return guestMapper.map(repository.save(guest));
    }

    public boolean delete(Integer passportNumber) {
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
}
