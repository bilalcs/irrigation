package com.irrigation.plot.batch.alert;

import com.irrigation.plot.entity.PlotConfig;
import com.irrigation.plot.service.PlotConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;
import java.util.List;

@Configuration
@EnableBatchProcessing
@Slf4j
public class AlertBatchJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private PlotConfigService plotConfigService;

    private final String ALERT_JOB_NAME = "alertJob";
    private final String ALERTL_STEP_NAME = "alertStep";

    @Bean
    public Job alertJob() {
        return jobBuilderFactory.get(ALERT_JOB_NAME)
                .start(alertStep())
                .build();
    }

    @Bean
    public Step alertStep() {
        return stepBuilderFactory.get(ALERTL_STEP_NAME)
                .<PlotConfig, PlotConfig>chunk(50)
                .reader(irrigationAlertsReader())
                .processor(alertProcessor())
                .writer(updateAlertCount())
                .build();
    }

    @Bean
    public ItemWriter<PlotConfig> updateAlertCount() {
        return new AlertWriter();
    }

    @Bean
    public ItemProcessor<PlotConfig, PlotConfig> alertProcessor() {
        return new AlertProcessor();
    }

    @Bean
    public ItemReader<PlotConfig> irrigationAlertsReader() {
        log.info("AlertBatchJob:: Calling reader");
        List<PlotConfig> plotConfigList = plotConfigService.readAllUnsentCommands(LocalTime.now());
        log.info("AlertBatchJob:: List " + plotConfigList);
        return new CustomItemReader<>(plotConfigList);
    }
}
