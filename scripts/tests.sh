#!/usr/bin/env bash
cd "$(dirname "$0")"
cd ..
./gradlew :app:test
./gradlew :app:connectedAndroidTest
./gradlew :cache:connectedAndroidTest
./gradlew :data:test
./gradlew :usecases:test
