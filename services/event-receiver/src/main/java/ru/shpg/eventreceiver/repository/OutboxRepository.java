package ru.shpg.eventreceiver.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.shpg.eventreceiver.entity.OutboxEvent;

@Repository
@RequiredArgsConstructor
public class OutboxRepository {

    private final EntityManager em;

    public void insert(OutboxEvent event) {
        em.persist(event);
    }

}
