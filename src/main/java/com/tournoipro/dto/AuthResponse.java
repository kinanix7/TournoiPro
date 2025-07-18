package com.tournoipro.dto;

import com.tournoipro.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String type = "Bearer";
    private String username;
    private String email;
    private Role role;
    
    public AuthResponse(String token, String username, String email, Role role) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.role = role;
    }
}

