package ru.shpg.simulation.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import ru.shpg.simulation.property.SimulationProperty;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    private final SimulationProperty simulationProperty;

    @Bean
    public RestClient restClient() {
        return RestClient.create(simulationProperty.getUrl());
    }

}
