package com.agms.sensor_service.service;

import com.agms.sensor_service.DTO.ExternalAuthRequest;
import com.agms.sensor_service.DTO.ExternalAuthResponse;
import com.agms.sensor_service.DTO.TelemetryDTO;
import com.agms.sensor_service.client.AutomationClient;
import com.agms.sensor_service.client.ExternalIotClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class TelemetryService {

    @Autowired
    private ExternalIotClient externalIotClient;

    @Autowired
    private AutomationClient automationClient;

    private String cachedToken;
    private TelemetryDTO latestReading; // For debug view

    /**
     * The Fetcher: Runs every 10 seconds as per specifications.
     */
    @Scheduled(fixedRateString = "${sensor.fetch-rate}")
    public void fetchAndPushTask() {
        try {
            // 1. Authenticate with External API if token is missing
            if (cachedToken == null) {
                ExternalAuthResponse auth = externalIotClient.login(new ExternalAuthRequest("Anjana", "1234"));
                cachedToken = "Bearer " + auth.getAccessToken();
            }

            // 2. Fetch latest telemetry
            // Note: You should ideally get the deviceId from Zone Service or DB.
            // Using a sample deviceId for the workflow.
            String sampleDeviceId = "b751b8c9-644a-484c-ba3f-be63f9b27ad0";
            TelemetryDTO data = externalIotClient.getLatestTelemetry(cachedToken, sampleDeviceId);

            if (data != null) {
                this.latestReading = data;

                // 3. The Pusher: Immediately send data to Automation Service
                automationClient.pushData(data);
                System.out.println("Data fetched and pushed to Automation Service successfully.");
            }

        } catch (Exception e) {
            System.err.println("Telemetry Fetching Error: " + e.getMessage());
            cachedToken = null; // Clear token to retry login next time
        }
    }

    public TelemetryDTO getLatestReading() {
        return latestReading;
    }
}
