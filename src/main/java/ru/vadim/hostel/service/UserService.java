package ru.vadim.hostel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vadim.hostel.entity.Role;
import ru.vadim.hostel.entity.User;
import ru.vadim.hostel.entity.dto.RoleDto;
import ru.vadim.hostel.entity.dto.UserDto;
import ru.vadim.hostel.exception.NoEntityException;
import ru.vadim.hostel.mapper.RoleMapper;
import ru.vadim.hostel.mapper.UserMapper;
import ru.vadim.hostel.repository.RoleRepository;
import ru.vadim.hostel.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NoEntityException(username));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public UserDto saveUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userMapper.map(userRepository.save(userMapper.map(userDto)));
    }

    public RoleDto saveRole(RoleDto roleDto) {
        return roleMapper.map(roleRepository.save(roleMapper.map(roleDto)));
    }

    public void addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NoEntityException(username));
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new NoEntityException(roleName));
        user.getRoles().add(role);
        userRepository.save(user);
    }

    public List<UserDto> getUsers() {
        return userMapper.map(userRepository.findAll());
    }
}
