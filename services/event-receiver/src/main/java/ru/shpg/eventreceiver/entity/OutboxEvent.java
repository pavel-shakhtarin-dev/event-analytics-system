package ru.shpg.eventreceiver.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.Instant;

@Entity
@Table(name = "outbox_event")
@Data
public class OutboxEvent {

    @Id
    @Column(name = "event_id")
    private String eventId;

    @Column(name = "user_id")
    private String userId;

    private String type;

    @Column(columnDefinition = "jsonb")
    private String payload;

    @Column(name = "created_at")
    private Instant createdAt;

}

