#!/bin/bash

DATE_WITH_TIME=`date "+%Y%m%d-%H%M%S"`
echo DATE_WITH_TIME

kill -9 $(lsof -i:9000 -t) 2> /dev/null
nohup ./mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=prod > ~/storagemanager-${DATE_WITH_TIME}.log &
