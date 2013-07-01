#!/bin/bash

DATE=$(date +%Y_%m_%d);
logFile=/usr/local/snsangelguardfb/harvested/logs/execute_harvesting_SNSAngelGuardFB_$DATE\.log;
cd /usr/local/snsangelguardfb/harvested/harvestedSNSAngelGuard/
mvn clean dependency:copy-dependencies package >> $logFile
mvn exec:java -Dexec.mainClass="es.uah.cc.ie.harvestedsnsangelguard.Main HOST PATH_KEYSTORE PASS_KEYSTORE" >> $logFile
