#!/bin/bash

ln -s /opt/restapp/restapp-0.1.0.jar /etc/init.d/restapp
##/etc/init.d/restapp start
update-rc.d restapp defaults
## PROMETHEUS AGENT TEST
sudo /etc/init.d/restapp stop
#sudo wget -qO /opt/jmx_prometheus_javaagent-0.1.0.jar https://repo1.maven.org/maven2/io/prometheus/jmx/jmx_prometheus_javaagent/0.1.0/jmx_prometheus_javaagent-0.1.0.jar
#sudo wget -qO /opt/tomcat.yml https://raw.githubusercontent.com/OpsMx/restapp/master/prometheus/tomcat.yml
#sudo nohup java -javaagent:/opt/jmx_prometheus_javaagent-0.1.0.jar=9090:/opt/tomcat.yml -jar /opt/restapp/restapp-0.1.0.jar &
sudo nohup java -jar /opt/restapp/restapp-0.1.0.jar &
