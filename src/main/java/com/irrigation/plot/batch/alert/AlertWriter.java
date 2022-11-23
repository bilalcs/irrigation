package com.irrigation.plot.batch.alert;

import com.irrigation.plot.entity.PlotConfig;
import com.irrigation.plot.service.PlotConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class AlertWriter implements ItemWriter<PlotConfig> {

    @Autowired
    private PlotConfigService plotConfigService;

    /**
     * @param list
     * @throws Exception
     */
    @Override
    public void write(List<? extends PlotConfig> list) throws Exception {
        log.info("Writing batch");
        plotConfigService.updateBatchConfig(list);
    }
}
