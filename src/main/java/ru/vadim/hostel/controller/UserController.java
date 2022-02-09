package ru.vadim.hostel.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vadim.hostel.entity.auth.RoleToUserForm;
import ru.vadim.hostel.entity.dto.RoleDto;
import ru.vadim.hostel.entity.dto.UserDto;
import ru.vadim.hostel.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Api(tags = "Users")
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @Operation(description = "Get list of users", responses = {@ApiResponse(responseCode = "200", description = "List of users received")})
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/users")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @Operation(description = "Create a new user", responses = {@ApiResponse(responseCode = "200", description = "User was saved")})
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok().body(userService.saveUser(userDto));
    }

    @PostMapping("/role")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @Operation(description = "Create a role", responses = {@ApiResponse(responseCode = "200", description = "Role was created")})
    public ResponseEntity<RoleDto> saveRole(@RequestBody RoleDto roleDto) {
        return ResponseEntity.ok().body(userService.saveRole(roleDto));
    }

    @PostMapping("/role/to-user")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @Operation(description = "Add the role to user", responses = {@ApiResponse(responseCode = "200", description = "Role was added")})
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }
}
