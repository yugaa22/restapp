#!/bin/sh
nohup java -javaagent:./jmx_prometheus_javaagent-0.1.0.jar=9090:tomcat.yml -jar restapp.jar &
#java -Dserver.port=8095 -jar /restapp.jar &
#hostname="hostname: `hostname`"
#nohup stress-ng --vm 4 &
#sed -i "s/# hostname: mymachine.mydomain/$hostname/g" /etc/dd-agent/datadog.conf
#service datadog-agent start
while true; do
  sleep 1000
done
