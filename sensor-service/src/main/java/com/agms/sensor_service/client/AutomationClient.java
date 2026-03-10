package com.agms.sensor_service.client;

import com.agms.sensor_service.DTO.TelemetryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "automation-service")
public interface AutomationClient {

    @PostMapping("/api/v1/automation/process")
    void pushData(@RequestBody TelemetryDTO data);
}