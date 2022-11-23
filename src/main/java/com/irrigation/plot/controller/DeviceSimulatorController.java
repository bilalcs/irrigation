package com.irrigation.plot.controller;

import com.irrigation.plot.entity.PlotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device")
@Slf4j
public class DeviceSimulatorController {


    @PostMapping("/command")
    public ResponseEntity<String> createPlotConfig(@RequestBody PlotConfig plotConfig) {
        log.info("DeviceSimulatorController :: Simulating device command for Plot Config: " + plotConfig);
        return new ResponseEntity<>(plotConfig.getConfigName(), HttpStatus.OK);
    }
}
