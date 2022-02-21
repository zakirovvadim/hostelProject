package ru.vadim.hostel.service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.routing.FromConfig;
import com.typesafe.config.ConfigFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.vadim.hostel.actors.GuestActor;
import ru.vadim.hostel.entity.Apartment;
import ru.vadim.hostel.entity.Guest;
import ru.vadim.hostel.entity.dto.GuestDto;
import ru.vadim.hostel.exception.NoEntityException;
import ru.vadim.hostel.mapper.ApartmentMapper;
import ru.vadim.hostel.mapper.GuestMapper;
import ru.vadim.hostel.repository.GuestRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"guest"})
public class GuestService {
    private final GuestRepository repository;
    private final GuestMapper guestMapper;

    private final ApartmentService apartmentService;
    private final ApartmentMapper apartmentMapper;

    @CacheEvict(value = "guest", allEntries = true)
    public GuestDto save(GuestDto guestDto) {
        Guest guest = guestMapper.map(guestDto);
        giveToActor(guest);
        return guestMapper.map(guest);
    }
    @CacheEvict(value = "guest", allEntries = true)
    public boolean delete(String passportNumber) {
        Guest guest = repository.findGuestByPassport(passportNumber).orElseThrow(() -> new NoEntityException(passportNumber));
        repository.deleteById(guest.getId());
        return true;
    }

    @Cacheable
    public List<GuestDto> getGuests() {
        return guestMapper.map(repository.findAll());
    }

    @CacheEvict(value = "guest", allEntries = true)
    public GuestDto updateGuest(GuestDto guestDto) {
        Guest currentGuest = repository.findGuestByPassport(guestDto.getPassport()).orElseThrow(() -> new NoEntityException(guestDto.getPassport()));
        Guest updatedGuest = guestMapper.map(guestDto);
        updatedGuest.setId(currentGuest.getId());
        return guestMapper.map(repository.save(updatedGuest));
    }
    @Cacheable
    public List<GuestDto> getGuestsFromApart(Long apartmentNumber) {
        List<Guest> guestDtoList = repository.findGuestByApartmentNumber(apartmentNumber).orElseThrow(() -> new NoEntityException(apartmentNumber));
        return guestMapper.map(guestDtoList);
    }
    @CacheEvict(value = "guest", allEntries = true)
    public GuestDto appointGuestToApartment(String passportNumber, Long apartmentNumber) {
        Guest guest = repository.findGuestByPassport(passportNumber).orElseThrow(() -> new NoEntityException(passportNumber));
        Apartment apartment = apartmentMapper.map(apartmentService.getApartmentByNumber(apartmentNumber));
        guest.setApartment(apartment);
        GuestDto dto = guestMapper.map(guest);
        dto.setApartment(apartmentMapper.map(apartment));
        return save(dto);
    }
    public void giveToActor(Guest guest) {
        final ActorSystem system = ActorSystem.create("TaskSystem", ConfigFactory.load().getConfig("TaskSystem"));
        ActorRef router = system.actorOf(GuestActor.props(repository).withRouter(new FromConfig()), "TaskRouter");
        router.tell(guest, null);
    }
}