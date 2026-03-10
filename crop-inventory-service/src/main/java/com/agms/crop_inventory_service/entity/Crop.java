package com.agms.crop_inventory_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Crop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type; // e.g., Tomato, Strawberry
    private String batchName;
    private String status; // SEEDLING, VEGETATIVE, HARVESTED
    private LocalDateTime plantedDate;
}