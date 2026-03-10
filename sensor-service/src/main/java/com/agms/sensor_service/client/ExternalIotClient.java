package com.agms.sensor_service.client;

import com.agms.sensor_service.DTO.ExternalAuthRequest;
import com.agms.sensor_service.DTO.ExternalAuthResponse;
import com.agms.sensor_service.DTO.TelemetryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "external-iot-service", url = "${sensor.external-api-url}")
public interface ExternalIotClient {

    @PostMapping("/auth/login")
    ExternalAuthResponse login(@RequestBody ExternalAuthRequest request);

    @GetMapping("/devices/telemetry/{deviceId}")
    TelemetryDTO getLatestTelemetry(
            @RequestHeader("Authorization") String token,
            @PathVariable String deviceId
    );
}