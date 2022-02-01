package ru.vadim.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vadim.hostel.entity.User;

@Repository
public interface UserDetailsRepository extends JpaRepository<User, String> {
}
