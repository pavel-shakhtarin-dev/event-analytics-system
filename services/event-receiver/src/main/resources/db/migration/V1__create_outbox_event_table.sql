
CREATE SCHEMA IF NOT EXISTS event;

CREATE TABLE IF NOT EXISTS event.outbox_event (
                                                  id UUID PRIMARY KEY,
                                                  aggregateid VARCHAR(255) NOT NULL,
                                                  aggregatetype VARCHAR(255) NOT NULL,
                                                  payload JSONB NOT NULL,
                                                  event_timestamp TIMESTAMPTZ NOT NULL,
                                                  created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_outbox_created_at ON event.outbox_event (created_at);