#!/bin/bash

cd /opt/stackdriver/collectd/etc/collectd.d/ && curl -O https://raw.githubusercontent.com/OpsMx/server_monitor/master/tomcat-7.conf
ln -s /opt/restapp/restapp-0.1.0.jar /etc/init.d/restapp
##/etc/init.d/restapp start
update-rc.d restapp defaults
