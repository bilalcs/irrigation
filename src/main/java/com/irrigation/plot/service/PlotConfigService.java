package com.irrigation.plot.service;

import com.irrigation.plot.entity.Plot;
import com.irrigation.plot.entity.PlotConfig;
import com.irrigation.plot.repository.PlotConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
public class PlotConfigService {

    private PlotConfigRepository plotConfigRepository;
    private PlotService plotService;

    @Value("${device.service.retrycount}")
    private Integer retryCount;

    @Autowired
    public PlotConfigService(PlotConfigRepository plotConfigRepository, PlotService plotService) {
        this.plotConfigRepository = plotConfigRepository;
        this.plotService = plotService;
    }

    public PlotConfig createPlotConfig(PlotConfig plotConfig) {
        Plot plot = plotService.readPlot(plotConfig.getPlot().getPlotId());
        plotConfig.setPlot(plot);
        log.info("PlotService :: Saving PlotConfig");
        return plotConfigRepository.save(plotConfig);
    }

    public PlotConfig readPlotConfig(Long id) {
        return plotConfigRepository.findById(id).orElse(null);
    }

    public List<PlotConfig> readAllPlotConfigs() {
        return plotConfigRepository.findAll();
    }

    public String deletePlotConfig(Long id) {
        plotConfigRepository.deleteById(id);
        return "Delete Successfully";
    }
    public List<PlotConfig> readAllUnsentCommands(LocalTime time) {
        return plotConfigRepository.findAllUnsentCommands(time, retryCount);
    }
    List<PlotConfig> readRecordsForAlert() {
        return plotConfigRepository.readRecordsForAlert(retryCount);
    }
    ;
    @Transactional
    public void updateRetryCount(Integer count, Long configId) {
        plotConfigRepository.updateRetryCount(count, configId);
    }
    @Transactional
    public void markIrrigated(Long configId) {
        plotConfigRepository.markIrrigated(configId);
    }
    @Transactional
    public void resetIrrigationState() {
        plotConfigRepository.resetIrrigationState();
    }

    @Transactional
    public void updateBatchConfig(List<? extends PlotConfig> list) {
        plotConfigRepository.saveAllAndFlush(list);
    }

}
