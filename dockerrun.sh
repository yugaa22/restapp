#!/bin/bash
#nohup java -javaagent:./jmx_prometheus_javaagent-0.1.0.jar=9090:tomcat.yml -jar restapp.jar > /var/log/rest_service.log 2>&1 &
nohup java -Dserver.port=8095 -jar /restapp.jar  > /var/log/rest_service.log 2>&1 &
#hostname="hostname: `hostname`"
#nohup stress-ng --vm 4 &
#sed -i "s/# hostname: mymachine.mydomain/$hostname/g" /etc/dd-agent/datadog.conf
#service datadog-agent start
while true; do
  sleep 1000
done
