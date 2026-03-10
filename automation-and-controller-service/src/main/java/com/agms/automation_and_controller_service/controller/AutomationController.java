package com.agms.automation_and_controller_service.controller;

import com.agms.automation_and_controller_service.DTO.TelemetryDTO;
import com.agms.automation_and_controller_service.DTO.ZoneDTO;
import com.agms.automation_and_controller_service.client.ZoneServiceClient;
import com.agms.automation_and_controller_service.entity.AutomationLog;
import com.agms.automation_and_controller_service.repository.AutomationLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/automation")
public class AutomationController {

    @Autowired
    private ZoneServiceClient zoneClient;

    @Autowired
    private AutomationLogRepository repository;

    @PostMapping("/process")
    public void processTelemetry(@RequestBody TelemetryDTO data) {
        // 1. Get current temperature from Map
        double currentTemp = Double.parseDouble(data.getValue().get("temperature").toString());

        // 2. Fetch thresholds from Zone Service (Sync communication)
        Long id = Long.parseLong(data.getZoneId().replaceAll("[^0-9]", ""));
        ZoneDTO zone = zoneClient.getZoneDetails(id);

        if (zone != null) {
            String action = null;

            // 3. Apply Rules
            if (currentTemp > zone.getMaxTemp()) {
                action = "TURN_FAN_ON";
            } else if (currentTemp < zone.getMinTemp()) {
                action = "TURN_HEATER_ON";
            }

            // 4. Log Action if triggered
            if (action != null) {
                AutomationLog log = new AutomationLog(null, data.getZoneId(), action, currentTemp, LocalDateTime.now());
                repository.save(log);
                System.out.println("LOGGED ACTION: " + action + " for Zone: " + data.getZoneId());
            }
        }
    }

    @GetMapping("/logs")
    public List<AutomationLog> getLogs() {
        return repository.findAll();
    }
}
