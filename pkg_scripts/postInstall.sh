#!/bin/bash

ln -s /opt/restapp/restapp-0.1.0.jar /etc/init.d/restapp
##/etc/init.d/restapp start
update-rc.d restapp defaults
sleep 60
apt-get install -y wget curl
sleep 60
curl -O "https://repo.stackdriver.com/stack-install.sh"
sleep 60
bash stack-install.sh --write-gcm
cd /opt/stackdriver/collectd/etc/collectd.d/
sudo curl -O https://raw.githubusercontent.com/OpsMx/server_monitor/master/tomcat-7.conf
#sudo wget -O /opt/packetbeat_install.sh https://raw.githubusercontent.com/N42Inc/server_monitor/master/packetbeat_install.sh && sudo bash /opt/packetbeat_install.sh
