#!/bin/sh
nohup java -javaagent:/opt/newrelic/newrelic.jar -javaagent:/opt/prometheus/jmx_prometheus_javaagent-0.1.0.jar=9090:tomcat.yml -jar restapp.jar &
while true; do
  sleep 1000
done
