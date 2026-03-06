package com.agms.zone_service.service;

import com.agms.zone_service.client.IoTClient;
import com.agms.zone_service.dto.DeviceRequest;
import com.agms.zone_service.model.Zone;
import com.agms.zone_service.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ZoneService {

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private IoTClient ioTClient;

    public Zone saveZone(Zone zone, String token) {
        // 1. Business Logic: minTemp strictly less than maxTemp
        if (zone.getMinTemp() >= zone.getMaxTemp()) {
            throw new RuntimeException("Min temperature must be less than Max temperature");
        }

        // 2. External API Call: Register Device
        // Use the token when get in gateway
        DeviceRequest deviceReq = new DeviceRequest(zone.getName(), "ZONE-" + zone.getName());
        Map<String, Object> response = ioTClient.registerDevice(token, deviceReq);

        // 3. Save the deviceId from response
        if (response != null && response.containsKey("deviceId")) {
            String externalId = (String) response.get("deviceId");
            zone.setDeviceId(externalId);
        }

        return zoneRepository.save(zone);
    }
}