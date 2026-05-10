
CREATE DATABASE IF NOT EXISTS event_db;

CREATE TABLE IF NOT EXISTS event_db.transactions
(
    userId UUID,
    amount Decimal(12,2),
    transactionTimestamp DateTime64(3)
)
    ENGINE = MergeTree
    ORDER BY (transactionTimestamp, userId);