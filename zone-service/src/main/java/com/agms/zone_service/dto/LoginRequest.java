package com.agms.zone_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

// For the external Login
public class LoginRequest {
    private String username;
    private String password;
}
