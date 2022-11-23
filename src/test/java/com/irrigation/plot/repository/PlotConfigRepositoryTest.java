package com.irrigation.plot.repository;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.LocalTime;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
@Transactional
class PlotConfigRepositoryTest {

    @Autowired
    private PlotConfigRepository repository;

    @Test
    public void testFindAllUnsent() {
        String.valueOf(repository.findAllUnsentCommands(LocalTime.now(), 5));
    }

    @Test
    public void testUpdateRetryCount() {
        repository.updateRetryCount(5, 2L);
    }
}