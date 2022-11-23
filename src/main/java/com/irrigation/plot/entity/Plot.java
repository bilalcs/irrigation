package com.irrigation.plot.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = {"configs"})
@AllArgsConstructor
@NoArgsConstructor
public class Plot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long plotId;

    @NonNull
    private String plotName;

    @JsonIgnoreProperties("plot")
    @OneToMany(mappedBy = "plot")
    private Set<PlotConfig> configs;
}
