package ru.vadim.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vadim.hostel.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
