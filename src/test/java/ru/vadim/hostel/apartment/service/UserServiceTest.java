package ru.vadim.hostel.apartment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.vadim.hostel.TestConfig;
import ru.vadim.hostel.entity.Role;
import ru.vadim.hostel.entity.User;
import ru.vadim.hostel.entity.dto.UserDto;
import ru.vadim.hostel.mapper.RoleMapper;
import ru.vadim.hostel.mapper.RoleMapperImpl;
import ru.vadim.hostel.mapper.UserMapper;
import ru.vadim.hostel.mapper.UserMapperImpl;
import ru.vadim.hostel.repository.RoleRepository;
import ru.vadim.hostel.repository.UserRepository;
import ru.vadim.hostel.service.UserService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Import({TestConfig.class})
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    private UserMapper userMapper;
    private RoleMapper roleMapper;
    private UserService userService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapperImpl();
        roleMapper = new RoleMapperImpl();
        userService = new UserService(userRepository, roleRepository, userMapper, roleMapper, passwordEncoder);
    }

    @Test
    void saveUser() {
        // given
        User userMocked = getUser();
        // when
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(userMocked);
        User saved = userMapper.map(userService.saveUser(userMapper.map(userMocked)));
        // then
        assertEquals(saved, userMocked);
    }

    @Test
    void saveRole() {
        // given
        Role roleMocked = getRole();
        // when
        when(roleRepository.save(ArgumentMatchers.any(Role.class))).thenReturn(roleMocked);
        Role saved = roleMapper.map(userService.saveRole(roleMapper.map(roleMocked)));
        // then
        assertThat(saved).isEqualTo(roleMocked);
    }

    @Test
    void addRoleToUser() {
        // given
        User user = getUser();
        Role role = getRole();
        // when
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(roleRepository.findByName(role.getName())).thenReturn(Optional.of(role));
        userService.addRoleToUser(user.getUsername(), role.getName());
        // then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser.getRoles()).isEqualTo(user.getRoles());

    }

    @Test
    void getUsers() {
        // when
        userService.getUsers();
        // then
        verify(userRepository).findAll();
    }

    public User getUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Vadim Zakirov");
        user.setUsername("vadim");
        return user;
    }
    public Role getRole() {
        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_ADMIN");
        return role;
    }
}