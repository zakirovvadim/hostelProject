//package ru.vadim.hostel.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RestController;
//import ru.vadim.hostel.entity.Guest;
//import ru.vadim.hostel.entity.User;
//import ru.vadim.hostel.entity.dto.GuestDto;
//import ru.vadim.hostel.exception.NoEntityException;
//import ru.vadim.hostel.repository.UserDetailsRepository;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class UserService {
//    private final UserDetailsRepository repository;
//
//    public List<User> findAllUsers() {
//        return repository.findAll();
//    }
//
//    public void deleteUser(String email) {
//        User user = repository.findByEmail(email).orElseThrow(() -> new NoEntityException(email));
//        repository.deleteById(user.getId());
//    }
//
//    public User updateUser(User newUser) {
//        User currentUser = repository.findByEmail(newUser.getEmail()).orElseThrow(() -> new NoEntityException(newUser.getEmail()));
//        newUser.setId(currentUser.getId());
//        return repository.save(newUser);
//    }
//}
