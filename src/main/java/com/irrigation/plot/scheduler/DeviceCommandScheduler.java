package com.irrigation.plot.scheduler;

import com.irrigation.plot.entity.PlotConfig;
import com.irrigation.plot.service.DeviceCommandService;
import com.irrigation.plot.service.PlotConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@EnableScheduling
@Configuration
@Slf4j
public class DeviceCommandScheduler {

    private PlotConfigService plotConfigService;
    private DeviceCommandService deviceCommandService;
    private JobLauncher jobLauncher;

    private AtomicInteger batchRunCounter = new AtomicInteger(0);

    @Autowired
    private Job alertBatchJob;

    @Autowired
    public DeviceCommandScheduler(PlotConfigService plotConfigService, DeviceCommandService deviceCommandService, JobLauncher jobLauncher) {
        this.plotConfigService = plotConfigService;
        this.deviceCommandService = deviceCommandService;
        this.jobLauncher = jobLauncher;
    }

    @Scheduled(cron = "0 * * * * *")
    public void sendDeviceCommands() {
        List<PlotConfig> plotConfigList = plotConfigService.readAllUnsentCommands(LocalTime.now());
        for (PlotConfig config: plotConfigList) {
            log.info("Sending Command from scheduler for Plot Config: " + config.getConfigName());
            deviceCommandService.sendDeviceCommand(config);
        }
    }
    @Scheduled(cron = "0 0 * * * *")
    public void resetIrrigationState() {
        plotConfigService.resetIrrigationState();
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void batchJob() throws Exception {
        Date date = new Date();
        log.debug("scheduler starts at " + date);
        JobExecution jobExecution = jobLauncher.run(alertBatchJob, new JobParametersBuilder().addDate("launchDate", date)
                    .toJobParameters());
            batchRunCounter.incrementAndGet();
            log.debug("Batch job ends with status as " + jobExecution.getStatus());
        log.debug("scheduler ends ");
    }
}
