package com.agms.sensor_service.controller;

import com.agms.sensor_service.DTO.TelemetryDTO;
import com.agms.sensor_service.service.TelemetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sensors")
public class SensorController {

    @Autowired
    private TelemetryService telemetryService;

    /**
     * API: GET /api/v1/sensors/latest
     * Objective: Returns the last fetched reading for debug view.
     */
    @GetMapping("/latest")
    public ResponseEntity<TelemetryDTO> getLatest() {
        return ResponseEntity.ok(telemetryService.getLatestReading());
    }
}
