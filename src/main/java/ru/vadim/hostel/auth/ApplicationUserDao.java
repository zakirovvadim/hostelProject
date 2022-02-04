package ru.vadim.hostel.auth;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    Optional<AuthUser> selectApplicationUserByUsername(String username);
}
