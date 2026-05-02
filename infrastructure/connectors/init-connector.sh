#!/bin/sh

CONNECT_URL=${CONNECT_URL:-"http://connect:8083"}
CONFIG_FILE=${CONFIG_FILE:-"/configs/outbox-event-connector.json"}

echo "--- Starting Connector Setup (POST method) ---"

# 1. Ждем доступности API
until [ "$(curl -s -o /dev/null -w '%{http_code}' "$CONNECT_URL/connectors")" -eq 200 ]; do
  echo "Waiting for Kafka Connect API..."
  sleep 5
done

# Извлекаем имя коннектора из JSON файла (чтобы проверить, создан ли он)
# Требуется, чтобы в JSON был ключ "name"
CONNECTOR_NAME=$(grep -o '"name": *"[^"]*"' "$CONFIG_FILE" | cut -d'"' -f4)

if [ -z "$CONNECTOR_NAME" ]; then
    echo "ERROR: Could not find connector name in JSON file"
    exit 1
fi

# 2. Проверяем, существует ли уже такой коннектор
STATUS_CODE=$(curl -s -o /dev/null -w '%{http_code}' "$CONNECT_URL/connectors/$CONNECTOR_NAME")

if [ "$STATUS_CODE" -eq 200 ]; then
    echo "Connector '$CONNECTOR_NAME' already exists. Skipping creation."
else
    echo "Creating new connector: $CONNECTOR_NAME..."

    # 3. Отправляем POST запрос
    RESPONSE=$(curl -s -w '%{http_code}' -X POST \
         -H "Content-Type: application/json" \
         --data @"$CONFIG_FILE" \
         "$CONNECT_URL/connectors")

    HTTP_CODE=$(echo "$RESPONSE" | tail -c 4)

    if [ "$HTTP_CODE" -eq 201 ]; then
        echo "SUCCESS: Connector registered (HTTP 201)"
    else
        echo "ERROR: Failed to register. HTTP Code: $HTTP_CODE"
        echo "Response: $(echo "$RESPONSE" | sed 's/...$//')"
        exit 1
    fi
fi
