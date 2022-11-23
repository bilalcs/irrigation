package com.irrigation.plot.controller;

import com.irrigation.plot.entity.Plot;
import com.irrigation.plot.service.PlotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plots")
@Slf4j
public class PlotController {

    private PlotService plotService;

    @Autowired
    public PlotController(PlotService plotService) {
        this.plotService = plotService;
    }

    @PostMapping
    public Plot createPlot(@RequestBody Plot plot) {
        log.info("PlotController :: Saving Plot");
        return plotService.createPlot(plot);
    }

    @GetMapping("/{id}")
    public Plot readPlot(@PathVariable Long id) {
        return plotService.readPlot(id);
    }

    @GetMapping
    public List<Plot> readAllPlots() {
        return plotService.readAllPlots();
    }

    @PutMapping
    public Plot updatePlot(@RequestBody Plot plot) {
        return plotService.updatePlot(plot);
    }

    @DeleteMapping("/{id}")
    public String deletePlot(@PathVariable Long id) {
        return plotService.deletePlot(id);
    }


}
