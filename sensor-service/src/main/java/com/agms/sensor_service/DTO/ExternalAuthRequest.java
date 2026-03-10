package com.agms.sensor_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ExternalAuthRequest {
    private String username;
    private String password;
}
