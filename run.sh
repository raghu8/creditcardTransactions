#!/bin/bash
git pull https://github.com/raghu8/creditcardTransactions.git
if [ $? -ne 0 ]; then
  echo "Unable to pull latest"
  exit 1
fi
./gradlew build -x test
if [ $? -ne 0 ]; then
  echo "Gradle build failures"
  exit 1
fi
docker-compose build
if [ $? -ne 0 ]; then
  echo "docker build failed"
  exit 1
fi
docker-compose up
if [ $? -ne 0 ]; then
  echo "Failed to start Docker containers"
  exit 1
fi
