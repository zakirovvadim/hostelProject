package ru.vadim.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vadim.hostel.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
