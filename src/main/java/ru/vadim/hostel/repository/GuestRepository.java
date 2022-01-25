package ru.vadim.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vadim.hostel.entity.Guest;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
}
