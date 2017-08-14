#!/bin/bash

ln -s /opt/restapp/restapp-0.1.0.jar /etc/init.d/restapp
/etc/init.d/restapp start
update-rc.d restapp defaults
