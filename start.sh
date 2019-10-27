#!/bin/bash

kill -9 $(lsof -i:9000 -t) 2> /dev/null
./mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=prod
