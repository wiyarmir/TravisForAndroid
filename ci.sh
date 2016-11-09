#!/bin/bash

# This will: compile the project, run lint, run tests under JVM, package apk, check the code quality and run tests on the device/emulator.
./gradlew clean build checkStyle checkStyleHtml findbugs pmd connectedCheck --stacktrace --console=plain -PdisablePreDex