#!/bin/bash

nohup java -jar /opt/restapp/restapp-0.1.0.jar > /var/log/restappinstall.log 2>&1 &
