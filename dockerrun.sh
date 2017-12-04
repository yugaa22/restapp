#!/bin/sh
java -Dserver.port=8095 -jar /restapp.jar &
hostname="hostname: `hostname`"
sed -i "s/# hostname: mymachine.mydomain/$hostname/g" /etc/dd-agent/datadog.conf
nohup stress-ng --cpu 3 &
#service datadog-agent start
while true; do
  sleep 1000
done
