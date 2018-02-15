FROM java:8

#RUN apt-get update && apt-get upgrade
ADD target/restapp.jar /restapp.jar
COPY dockerrun.sh /usr/local/bin/dockerrun.sh
RUN chmod +x /usr/local/bin/dockerrun.sh
COPY newrelic /opt/newrelic
CMD ["dockerrun.sh"]
