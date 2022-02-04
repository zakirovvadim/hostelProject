//package ru.vadim.hostel.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import ru.vadim.hostel.entity.User;
//import ru.vadim.hostel.exception.NoEntityException;
//import ru.vadim.hostel.repository.UserRepository;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class UserService {
//    private final UserRepository repository;
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
//
//    public User createUser(User newUser) {
//        return repository.save(newUser);
//    }
//
//    public User findUserByEmail(String email) {
//        return repository.findByEmail(email).orElseThrow(() -> new NoEntityException(email));
//    }
//}
