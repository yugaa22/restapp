FROM opsmx11/java:14.04-openjdk-8-jdk
##port for restapp ##***
ENV server_port=8080
RUN apt-get update && apt-get install stress-ng -y
COPY /target/restapp.jar /opt/restapp.jar
COPY dockerrun.sh /usr/local/bin/dockerrun.sh
RUN chmod +x /usr/local/bin/dockerrun.sh
#### for newrelic ###
COPY newrelic/* /opt/newrelic/
#COPY prometheus/jmx_prometheus_javaagent-0.1.0.jar /opt/jmx_prometheus_javaagent-0.1.0.jar
#COPY prometheus/tomcat.yml /opt/tomcat.yml
#COPY tomcat.yaml /etc/dd-agent/conf.d/tomcat.yaml
#COPY install-dd.sh install-dd.sh
#RUN DD_API_KEY=<KEY> bash install-dd.sh
CMD ["dockerrun.sh"]

