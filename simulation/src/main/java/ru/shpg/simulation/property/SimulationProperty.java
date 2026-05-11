package ru.shpg.simulation.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "simulation")
@Getter
@Setter
public class SimulationProperty {
    private String url = "http://localhost:8080/events";
    private int usersCount = 100;
    private double usersLoadRatio = 0.1;
    private double otherTypesRatio = 0.1;
    private double duplicateRatio = 0.05;
    private double invalidRatio = 0.05;
    private int periodMs = 100;
}
