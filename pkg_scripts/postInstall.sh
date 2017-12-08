#!/bin/bash

ln -s /opt/restapp/restapp-0.1.0.jar /etc/init.d/restapp
##/etc/init.d/restapp start
update-rc.d restapp defaults
## PROMETHEUS AGENT TEST
/etc/init.d/restapp stop
wget -qO /opt/jmx_prometheus_javaagent-0.1.0.jar https://github.com/OpsMx/restapp/blob/master/prometheus/jmx_prometheus_javaagent-0.1.0.jar
wget -qO /opt/tomcat.yml https://raw.githubusercontent.com/OpsMx/restapp/master/prometheus/tomcat.yml
nohup java -javaagent:/opt/jmx_prometheus_javaagent-0.1.0.jar=9090:/opt/tomcat.yml -jar /opt/restapp/restapp-0.1.0.jar &
