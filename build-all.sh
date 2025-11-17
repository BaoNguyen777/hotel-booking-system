#!/bin/bash
# build-all.sh - build jar và docker image cho tất cả services

set -e  # dừng khi có lỗi

SERVICES=(
  "user-service"
  "hotel-service"
  "booking-service"
  "gateway-service"
  "config-server"
  "eureka-server"
)

for SERVICE in "${SERVICES[@]}"; do
  echo "=== Building $SERVICE ==="
  cd $SERVICE
  ./gradlew clean bootJar -x test
  docker build -t "$SERVICE:latest" .
  cd ..
done

echo "=== All services built successfully! ==="
