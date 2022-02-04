//package ru.vadim.hostel.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//import ru.vadim.hostel.entity.User;
//import ru.vadim.hostel.service.UserService;
//
//import java.util.List;
//
//@RequestMapping("/user")
//@RestController
//@RequiredArgsConstructor
//public class UserController {
//    private final UserService service;
//
//    @GetMapping(value = "")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public List<User> findAllUser() {
//        return service.findAllUsers();
//    }
//
//    @GetMapping(value = "")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public User findUser(@PathVariable(name = "email") String email) {
//        return service.findUserByEmail(email);
//    }
//
//    @DeleteMapping(value = "/{email}")
//    @PreAuthorize("hasAuthority('user:delete')")
//    public void deleteUser(@PathVariable(name = "email") String email) {
//        service.deleteUser(email);
//    }
//
//    @PutMapping(value = "")
//    @PreAuthorize("hasAuthority('user:update')")
//    public ResponseEntity<User> updateGuest(@RequestBody User user) {
//        return new ResponseEntity<>(service.updateUser(user), HttpStatus.OK);
//    }
//
//    @PostMapping(value = "")
//    @PreAuthorize("hasAuthority('user:create')")
//    public User createUser(@RequestBody User user) {
//        return service.createUser(user);
//    }
//
//}
