#!/bin/bash
#Programa automático de backups de información para la aplicación SNSAngelGuardFB
#Actualiza la información de la base de datos y ejecuta los filtros

DATE=$(date +%Y_%m_%d);
logFile=/usr/local/snsangelguardfb/harvested/logs/execute_harvesting_SNSAngelGuardFB_$DATE\.log;
cd /Users/josejavierblecuadepedro1/Documents/angelnet/SNSANGELGUARD/SOURCES/harvestedSNSAngelGuard
mvn clean dependency:copy-dependencies package >> $logFile
mvn exec:java -Dexec.mainClass="es.uah.cc.ie.harvestedsnsangelguard.Main" -Dexec.args="https://localhost:8443/ /Users/josejavierblecuadepedro1/.keystore blecua" >> $logFile
