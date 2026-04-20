CREATE TABLE outbox_event (
                              id UUID NOT NULL,
                              aggregate_id VARCHAR(255) NOT NULL,
                              aggregate_type VARCHAR(255) NOT NULL,
                              payload JSONB NOT NULL,
                              client_timestamp TIMESTAMPTZ NOT NULL,
                              created_at TIMESTAMPTZ NOT NULL,
                              PRIMARY KEY (id, created_at)
) PARTITION BY RANGE (created_at);