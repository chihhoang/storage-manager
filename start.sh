#!/bin/bash

DATE_WITH_TIME=`date "+%Y%m%d-%H%M%S"`

kill -9 $(lsof -i:9000 -t) 2> /dev/null
nohup ./mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=prod > ~/storagepro-${DATE_WITH_TIME}.log &
