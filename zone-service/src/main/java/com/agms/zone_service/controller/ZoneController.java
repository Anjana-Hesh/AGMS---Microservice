package com.agms.zone_service.controller;

import com.agms.zone_service.model.Zone;
import com.agms.zone_service.repository.ZoneRepository;
import com.agms.zone_service.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            return ResponseEntity.ok(zoneService.saveZone(zone, token));
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZone(@PathVariable Long id) {
        zoneRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
