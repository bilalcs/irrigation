package com.irrigation.plot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//TODO - Future implementation
public class IrrigationStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long statusId;

    private Boolean isSent = false;

    private Integer retryCount = 0;

    private Integer alertCount = 0;

//    @OneToOne
//    private PlotConfig plotConfig;
}
