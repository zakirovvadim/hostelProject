package ru.vadim.hostel.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UsernameAndPasswordAuthRequest {
    private String username;
    private String password;
}
