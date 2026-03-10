package com.agms.automation_and_controller_service.client;

import com.agms.automation_and_controller_service.DTO.ZoneDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "zone-service")
public interface ZoneServiceClient {
    @GetMapping("/api/v1/zones/{id}")
    ZoneDTO getZoneDetails(@PathVariable("id") Long id);
}
