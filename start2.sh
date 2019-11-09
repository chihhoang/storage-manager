#!/bin/bash

DATE_WITH_TIME=`date "+%Y%m%d-%H%M%S"`
echo $DATE_WITH_TIME

kill -9 $(lsof -i:9000 -t) 2> /dev/null
echo "Killed port 9000"
mvn clean install

nohup java -Dspring.profiles.active=prod -jar target/storage-manager-0.0.1-SNAPSHOT.jar > ~/storagemanager-${DATE_WITH_TIME}.log &

echo "Started the application"
