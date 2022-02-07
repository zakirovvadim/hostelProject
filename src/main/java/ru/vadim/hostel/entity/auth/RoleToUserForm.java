package ru.vadim.hostel.entity.auth;

import lombok.Data;

@Data
public class RoleToUserForm {
    private String username;
    private String roleName;
}
