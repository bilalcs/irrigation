package com.irrigation.plot.batch.alert;

import com.irrigation.plot.entity.PlotConfig;
import com.irrigation.plot.util.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.SendFailedException;

@Slf4j
public class AlertProcessor implements ItemProcessor<PlotConfig, PlotConfig> {

    @Autowired
    private EmailService emailService;
    /**
     * @param plotConfig
     * @return
     * @throws Exception
     */
    @Override
    public PlotConfig process(PlotConfig plotConfig) throws Exception {
        try {
            log.info("Processing Job");
            emailService.sendEmail("bilalalerts@gmail.com",
                    "!Irrigation Failed for Config: " + plotConfig.getConfigName(), "Irrigation Failed");
            Integer alertCount = plotConfig.getAlertCount();
            plotConfig.setAlertCount(alertCount+1);
        } catch (SendFailedException e) {
            log.info(e.getMessage());
        }
        return plotConfig;
    }
}
