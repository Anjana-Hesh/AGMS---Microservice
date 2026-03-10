package com.agms.automation_and_controller_service.DTO;

import lombok.Data;

import java.util.Map;

@Data
public class TelemetryDTO {
    private String deviceId;
    private String zoneId;
    private Map<String, Object> value;
}
