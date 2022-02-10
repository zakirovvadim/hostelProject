package ru.vadim.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vadim.hostel.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
