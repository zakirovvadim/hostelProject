package ru.vadim.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vadim.hostel.entity.Apartment;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
}
