-- пользователь для debezium
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT FROM pg_roles WHERE rolname = 'debezium'
    ) THEN
CREATE ROLE debezium WITH LOGIN PASSWORD 'debezium';
END IF;
END $$;

-- права на базу
GRANT CONNECT ON DATABASE event_db TO debezium;

-- права на схему
GRANT USAGE ON SCHEMA event TO debezium;

-- права на таблицы
GRANT SELECT ON ALL TABLES IN SCHEMA event TO debezium;

-- чтобы новые таблицы тоже были доступны
ALTER DEFAULT PRIVILEGES IN SCHEMA event
GRANT SELECT ON TABLES TO debezium;

ALTER TABLE event.outbox_event OWNER TO debezium;

-- для logical replication
ALTER ROLE debezium WITH REPLICATION;