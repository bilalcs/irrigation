package com.irrigation.plot.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Data
@EqualsAndHashCode(exclude = {"plot"})
@AllArgsConstructor
@NoArgsConstructor
public class PlotConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long configId;
    @NonNull
    private String configName;

    @Column(name = "irrigation_time", columnDefinition = "TIME")
    private LocalTime irrigationTime;

    private String irrigationAmountOfWater;

    @JsonIgnoreProperties("configs")
    @ManyToOne
    private Plot plot;

    //TODO - Extract into separate Entity
    private Boolean isSent = false;

    private Integer retryCount = 0;

    private Integer alertCount = 0;
}
