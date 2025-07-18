package com.tournoipro.dto;

import com.tournoipro.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    
    private String username;
    private String email;
    private String password;
    private Role role = Role.USER;
}

