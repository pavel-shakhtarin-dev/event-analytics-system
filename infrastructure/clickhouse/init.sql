CREATE TABLE IF NOT EXISTS transactions
(
    userId String,
    amount Decimal(18,2),
    timestamp DateTime
    )
    ENGINE = MergeTree
    ORDER BY (userId, timestamp);