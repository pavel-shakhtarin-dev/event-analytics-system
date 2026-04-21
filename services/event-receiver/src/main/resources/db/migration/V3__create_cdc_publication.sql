
CREATE PUBLICATION dbz_publication
FOR TABLE event.outbox_event;

ALTER PUBLICATION dbz_publication OWNER TO debezium;