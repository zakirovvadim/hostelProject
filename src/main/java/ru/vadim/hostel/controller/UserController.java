//package ru.vadim.hostel.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//import ru.vadim.hostel.entity.User;
//import ru.vadim.hostel.entity.dto.GuestDto;
//import ru.vadim.hostel.service.UserService;
//
//import java.util.List;
//
//@RequestMapping("/user")
//@RestController
//@RequiredArgsConstructor
//public class UserController {
//    private UserService userService;
//
//    @GetMapping(value = "")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
//    public List<User> userList() {
//        return userService.findAllUsers();
//    }
//
//    @DeleteMapping(value = "/{email}")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
//    public void deleteUser(@PathVariable(name = "email") String email) {
//        userService.deleteUser(email);
//    }
//
//    @PutMapping(value = "")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
//    public ResponseEntity<User> updateGuest(@RequestBody User user) {
//        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
//    }
//
//}
