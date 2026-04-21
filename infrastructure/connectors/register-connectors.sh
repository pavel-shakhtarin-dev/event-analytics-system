#!/bin/bash

echo "Waiting for Kafka Connect..."

# ждём пока connect поднимется
until curl -s http://kafka-connect:8083/connectors; do
  sleep 3
done

echo "Registering connector..."

curl -X POST http://kafka-connect:8083/connectors \
  -H "Content-Type: application/json" \
  -d @/kafka/connectors/outbox-event-connector.json

echo "Done"