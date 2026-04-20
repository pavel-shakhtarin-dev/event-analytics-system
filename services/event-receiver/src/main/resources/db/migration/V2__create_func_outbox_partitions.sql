CREATE OR REPLACE FUNCTION create_outbox_partition(p_date DATE)
RETURNS void AS $$
DECLARE
partition_name TEXT;
    start_ts TIMESTAMPTZ;
    end_ts TIMESTAMPTZ;
BEGIN
    start_ts := p_date::timestamptz;
    end_ts := (p_date + INTERVAL '1 day')::timestamptz;

    partition_name := format('outbox_event_%s', to_char(p_date, 'YYYY_MM_DD'));

EXECUTE format(
        'CREATE TABLE IF NOT EXISTS event.%I PARTITION OF outbox_event
         FOR VALUES FROM (%L) TO (%L)',
        partition_name,
        start_ts,
        end_ts
        );

EXECUTE format(
        'CREATE INDEX IF NOT EXISTS %I_created_at_idx ON event.%I (created_at)',
        partition_name,
        partition_name
        );
END;
$$ LANGUAGE plpgsql;