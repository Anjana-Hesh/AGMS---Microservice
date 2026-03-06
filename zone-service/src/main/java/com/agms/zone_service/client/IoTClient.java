package com.agms.zone_service.client;

import com.agms.zone_service.dto.DeviceRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "iot-external-api", url = "http://104.211.95.241:8080/api")
public interface IoTClient {

    @PostMapping("/devices")
    Map<String, Object> registerDevice(@RequestHeader("Authorization") String token, @RequestBody DeviceRequest request);
}
