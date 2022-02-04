package ru.vadim.hostel.auth;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static ru.vadim.hostel.security.ApplicationUserRole.ADMIN;
import static ru.vadim.hostel.security.ApplicationUserRole.MANAGER;

@RequiredArgsConstructor
public class FakeUserRepositoryService implements UserRepository {

    private final PasswordEncoder encoder;
    @Override
    public Optional<AuthUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(authUser -> username.equals(authUser.getUsername()))
                .findFirst();
    }

    private List<AuthUser> getApplicationUsers() {
        List<AuthUser> authUsers = Lists.newArrayList(
                new AuthUser(
                        "annasmith",
                        encoder.encode("password"),
                        MANAGER.getSimpleGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new AuthUser(
                        "linda",
                        encoder.encode("password"),
                        ADMIN.getSimpleGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                )
        );
        return authUsers;
    }
}
