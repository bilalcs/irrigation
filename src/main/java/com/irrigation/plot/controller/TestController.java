package com.irrigation.plot.controller;

import com.irrigation.plot.entity.PlotConfig;
import com.irrigation.plot.service.DeviceCommandService;
import com.irrigation.plot.service.PlotConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private DeviceCommandService service;
    @Autowired
    private PlotConfigService plotConfigService;

    @GetMapping("/testcommand/{id}")
    public void testcommand(@PathVariable Long id) {
        PlotConfig config = new PlotConfig();
        config.setConfigId(id);
        config.setConfigName("my config");
            service.sendDeviceCommand(config);

    }
    @GetMapping("/test2")
    //@ResponseStatus()
    public String test2() {
        log.info("Calling test2");

        return "returning string";

    }
    @GetMapping("/reset")
    //@ResponseStatus()
    public void reset() {
        log.info("resetting");
        plotConfigService.resetIrrigationState();
    }
}
