CREATE OR REPLACE FUNCTION drop_old_outbox_partitions(p_retention INTERVAL)
RETURNS void AS $$
DECLARE
part RECORD;
    cutoff TIMESTAMPTZ;
BEGIN
    cutoff := now() - p_retention;

FOR part IN
SELECT
    c.relname AS partition_name,
    pg_get_expr(c.relpartbound, c.oid) AS partition_range
FROM pg_class c
         JOIN pg_inherits i ON c.oid = i.inhrelid
         JOIN pg_class parent ON i.inhparent = parent.oid
WHERE parent.relname = 'outbox_event'
    LOOP
        -- пример partition_range:
        -- FOR VALUES FROM ('2026-04-18 00:00:00+00') TO ('2026-04-19 00:00:00+00')
        -- извлекаем upper bound через regex (без имени таблицы)
        PERFORM 1;

IF (
            substring(part.partition_range FROM 'TO \(''(.*?)''\)')::timestamptz
        ) < cutoff
        THEN
            RAISE NOTICE 'Dropping partition: %', part.partition_name;

EXECUTE format('DROP TABLE IF EXISTS %I', part.partition_name);
END IF;
END LOOP;
END;
$$ LANGUAGE plpgsql;