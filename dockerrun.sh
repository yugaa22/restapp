#!/bin/sh
nohup java -javaagent:/opt/newrelic/newrelic.jar -jar restapp.jar &
while true; do
  sleep 1000
done
