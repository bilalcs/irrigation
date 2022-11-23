package com.irrigation.plot.service;

import com.irrigation.plot.entity.PlotConfig;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class DeviceCommandService {

    @Value("${device.service.url}")
    private String deviceCommandServiceUrl;
    @Value("${device.service.retrycount}")
    private Integer retryCount;
    //TODO - Use WebClient instead of restTemplate
    private RestTemplate restTemplate;

    private PlotConfigService plotConfigService;

    @Autowired
    public DeviceCommandService(RestTemplateBuilder builder, PlotConfigService plotConfigService) {
        this.restTemplate = builder.build();
        this.plotConfigService = plotConfigService;
    }

    @CircuitBreaker(name = "commandRetry", fallbackMethod = "updateRetryCount")
    @Retry(name = "commandRetry")
    public void sendDeviceCommand(PlotConfig plotConfig)  {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        log.info("Sending request for command");
        HttpEntity<PlotConfig> request = new HttpEntity<>(plotConfig, headers);
        ResponseEntity<String> response = restTemplate.
                postForEntity(deviceCommandServiceUrl, request, String.class);
        if(! response.getStatusCode().equals(HttpStatus.OK)) {
            log.info("status not ok, throwing exception");
            throw new RestClientException("Device Command Not Sent");
        } else {
            markIrrigated(plotConfig.getConfigId());
        }
    }

    public void updateRetryCount(PlotConfig plotConfig, Exception ex) {
        log.info("Request for device command failed, updating retry count");
        log.info(ex.getMessage());
        plotConfigService.updateRetryCount(retryCount + 1, plotConfig.getConfigId());
    }
    private void markIrrigated(Long configId) {
        log.info("Marking Irrigated");
        plotConfigService.markIrrigated(configId);
    }
}
