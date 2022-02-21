package ru.vadim.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vadim.hostel.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
