#!/bin/bash

ln -s /opt/restapp/restapp-0.1.0.jar /etc/init.d/restapp
##/etc/init.d/restapp start
update-rc.d restapp defaults
sudo apt-get install -y wget curl
sudo apt-get install -y python-pip python-dev build-essential
curl -O "https://repo.stackdriver.com/stack-install.sh"
sudo bash stack-install.sh --write-gcm
cd /opt/stackdriver/collectd/etc/collectd.d/
sudo curl -O https://raw.githubusercontent.com/OpsMx/server_monitor/master/tomcat-7.conf
#sudo wget -O /opt/packetbeat_install.sh https://raw.githubusercontent.com/N42Inc/server_monitor/master/packetbeat_install.sh && sudo bash /opt/packetbeat_install.sh
