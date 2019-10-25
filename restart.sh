#!/bin/bash
cd ~/storage-manager
fuser -k -n tcp 9000
./mvnw spring-boot:run