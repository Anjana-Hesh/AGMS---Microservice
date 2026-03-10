package com.agms.crop_inventory_service.controller;

import com.agms.crop_inventory_service.entity.Crop;
import com.agms.crop_inventory_service.repository.CropRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/crops")
public class CropController {

    @Autowired
    private CropRepository repository;

    // Register a new batch (Default Status: SEEDLING)
    @PostMapping
    public Crop registerCrop(@RequestBody Crop crop) {
        crop.setPlantedDate(LocalDateTime.now());
        crop.setStatus("SEEDLING");
        return repository.save(crop);
    }

    // Update lifecycle stage (SEEDLING -> VEGETATIVE -> HARVESTED)
    @PutMapping("/{id}/status")
    public Crop updateStatus(@PathVariable Long id, @RequestParam String status) {
        Crop crop = repository.findById(id).orElseThrow();
        crop.setStatus(status.toUpperCase());
        return repository.save(crop);
    }

    // View current inventory
    @GetMapping
    public List<Crop> getInventory() {
        return repository.findAll();
    }
}