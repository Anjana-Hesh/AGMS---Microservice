package com.agms.zone_service.controller;

import com.agms.zone_service.model.Zone;
import com.agms.zone_service.repository.ZoneRepository;
import com.agms.zone_service.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/zones")
public class ZoneController {

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private ZoneRepository zoneRepository;

    @PostMapping
    public ResponseEntity<?> createZone(@RequestHeader("Authorization") String token, @RequestBody Zone zone) {
        try {
//            return ResponseEntity.ok(zoneService.saveZone(zone, token));
            return ResponseEntity.ok(zoneService.saveZone(zone));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<Zone> getAllZones() {
        return zoneRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Zone> getZone(@PathVariable Long id) {
        return zoneRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateZone(@PathVariable Long id, @RequestBody Zone zoneDetails) {
        return zoneRepository.findById(id)
                .map(zone -> {
                    if (zoneDetails.getMinTemp() >= zoneDetails.getMaxTemp()) {
                        return ResponseEntity.badRequest().body("Min temp must be less than max temp");
                    }
                    zone.setName(zoneDetails.getName());
                    zone.setMinTemp(zoneDetails.getMinTemp());
                    zone.setMaxTemp(zoneDetails.getMaxTemp());
                    return ResponseEntity.ok(zoneRepository.save(zone));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZone(@PathVariable Long id) {
        zoneRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/thresholds")
    public ResponseEntity<?> getThresholds(@PathVariable Long id) {
        return zoneRepository.findById(id)
                .map(zone -> {
                    Map<String, Double> data = new HashMap<>();
                    data.put("minTemp", zone.getMinTemp());
                    data.put("maxTemp", zone.getMaxTemp());
                    return ResponseEntity.ok(data);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
