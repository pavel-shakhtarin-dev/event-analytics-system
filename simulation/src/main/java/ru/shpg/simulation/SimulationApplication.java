package ru.shpg.simulation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.shpg.simulation.property.SimulationProperty;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(SimulationProperty.class)
public class SimulationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimulationApplication.class, args);
	}

}
