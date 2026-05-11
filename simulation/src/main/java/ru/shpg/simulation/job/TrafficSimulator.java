package ru.shpg.simulation.job;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.shpg.simulation.model.EventRequest;
import ru.shpg.simulation.property.SimulationProperty;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class TrafficSimulator {

    private final RestClient restClient;
    private final SimulationProperty property;

    private final Random random = new Random();

    private final List<String> types = List.of("test_type_1", "test_type_2", "test_type_3");

    private List<UUID> userIds;


    @PostConstruct
    public void init() {
        this.userIds = IntStream
                .range(0, property.getUsersCount())
                .mapToObj(i -> UUID.randomUUID())
                .toList();
    }

    @Scheduled(fixedRateString = "${simulation.period-ms}")
    public void sendTraffic() {
        var userAmount = property.getUsersCount() - (random.nextInt((int) (property.getUsersCount() * property.getUsersLoadRatio())));
        for (int i = 0; i < userAmount; i++) {
            var payload = generatePayload(userIds.get(i));
            restClient.post()
                    .body(payload)
                    .retrieve()
                    .toBodilessEntity();
        }
    }

    private EventRequest generatePayload(UUID userId) {
        var isInvalid = random.nextDouble() < property.getInvalidRatio();
        var isOtherType = random.nextDouble() < property.getOtherTypesRatio();
        var isDuplicate = random.nextDouble() < property.getDuplicateRatio();
        if (isDuplicate) {
            return new EventRequest(
                    UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                    UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                    "transaction",
                    Instant.now().toEpochMilli(),
                    createPayload()
            );
        }
        if (isOtherType) {
            return new EventRequest(
                    UUID.randomUUID(),
                    isInvalid ? null : userId,
                    types.get(random.nextInt(types.size())),
                    Instant.now().toEpochMilli(),
                    createPayload()
            );
        }
        return new EventRequest(
                UUID.randomUUID(),
                isInvalid ? null : userId,
                "transaction",
                Instant.now().toEpochMilli(),
                createPayload()
        );
    }

    private Map<String, Object> createPayload() {
        return Map.of("amount", BigDecimal.valueOf(random.nextDouble() * 10));
    }

}

