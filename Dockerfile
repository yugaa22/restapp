FROM java:8

#RUN apt-get update && apt-get upgrade
ADD target/restapp.jar /restapp.jar
COPY prometheus/jmx_prometheus_javaagent-0.1.0.jar /opt/prometheus/jmx_prometheus_javaagent-0.1.0.jar
COPY prometheus/tomcat.yml /opt/prometheus/tomcat.yml
COPY dockerrun.sh /usr/local/bin/dockerrun.sh
RUN chmod +x /usr/local/bin/dockerrun.sh
COPY newrelic /opt/newrelic
CMD ["dockerrun.sh"]
