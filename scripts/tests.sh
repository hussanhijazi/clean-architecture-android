#!/usr/bin/env bash
cd ..
./gradlew :app:test
./gradlew :app:connectedAndroidTest
./gradlew :cache:connectedAndroidTest
./gradlew :data:test
./gradlew :usecases:test
