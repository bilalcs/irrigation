package com.irrigation.plot.service;

import com.irrigation.plot.entity.Plot;
import com.irrigation.plot.repository.PlotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class PlotService {

    private PlotRepository plotRepository;

    @Autowired
    public PlotService(PlotRepository plotRepository) {
        this.plotRepository = plotRepository;
    }

    public Plot createPlot(Plot plot) {

        log.info("PlotService :: Saving Plot");
        return plotRepository.save(plot);
    }

    public Plot readPlot(Long id) {
        return plotRepository.findById(id).orElse(null);
    }

    public List<Plot> readAllPlots() {
        return plotRepository.findAll();
    }
    public Plot updatePlot(Plot plot) {
        Plot plotFromDB = readPlot(plot.getPlotId());
        if (Objects.isNull(plotFromDB))
            return null;
        else {
            plotFromDB.setPlotName(plot.getPlotName());
            plotRepository.save(plotFromDB);
            return plotFromDB;
        }
    }
    public String deletePlot(Long id) {
        plotRepository.deleteById(id);
        return "Delete Successfully";
    }
}
