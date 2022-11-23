package com.irrigation.plot.controller;

import com.irrigation.plot.entity.PlotConfig;
import com.irrigation.plot.service.PlotConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/plotconfigs")
@Slf4j
public class PlotConfigController {

    private PlotConfigService plotConfigService;

    @Autowired
    public PlotConfigController(PlotConfigService plotConfigService) {
        this.plotConfigService = plotConfigService;
    }

    @PostMapping
    public PlotConfig createPlotConfig(@RequestBody PlotConfig plotConfig) {
        log.info("PlotController :: Saving Plot Config" + plotConfig);
        return plotConfigService.createPlotConfig(plotConfig);
    }
    @GetMapping
    public List<PlotConfig> readAllPlotConfigs() {
        return plotConfigService.readAllPlotConfigs();
    }

    @GetMapping("/unsent")
    public List<PlotConfig> findAllUnsentCommands() {
        return plotConfigService.readAllUnsentCommands(LocalTime.now());
    }
}
