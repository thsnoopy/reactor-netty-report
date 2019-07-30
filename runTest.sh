#!/bin/bash

./killTestApp.sh

cd server-actor
mvn clean package
java -jar target/server-actor-0.0.1-SNAPSHOT.jar  > /dev/null 2>&1 &
sleep 3.0

cd ..
cd client-actor
mvn clean package
java -jar target/client-actor-0.0.1-SNAPSHOT.jar  > /dev/null 2>&1 &
sleep 3.0

cd ..
cd test-runner
mvn clean test

