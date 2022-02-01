package ru.vadim.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vadim.hostel.entity.Guest;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

    Optional<Guest> findGuestByPassport(Long passportNumber);

    Optional<List<Guest>> findGuestByApartmentNumber(Long apartmentNumber);
}
