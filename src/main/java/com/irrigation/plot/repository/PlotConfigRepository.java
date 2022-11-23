package com.irrigation.plot.repository;

import com.irrigation.plot.entity.PlotConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface PlotConfigRepository extends JpaRepository<PlotConfig, Long> {

    @Query(value = "SELECT c FROM PlotConfig c WHERE c.irrigationTime < :time AND c.isSent = false AND c.retryCount < :count")
    List<PlotConfig> findAllUnsentCommands(LocalTime time, Integer count);

    @Query(value = "SELECT c FROM PlotConfig c WHERE c.isSent = false AND c.retryCount > :count")
    List<PlotConfig> readRecordsForAlert(Integer count);

    @Modifying
    @Query("UPDATE PlotConfig c set c.retryCount = :count WHERE c.configId = :configId")
    void updateRetryCount(Integer count, Long configId);

    @Modifying
    @Query("UPDATE PlotConfig c set c.isSent = true WHERE c.configId = :configId")
    void markIrrigated(Long configId);
    @Modifying
    @Query("UPDATE PlotConfig c set c.isSent = false, c.retryCount = 0")
    void resetIrrigationState();
}
