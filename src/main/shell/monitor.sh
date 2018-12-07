#!/usr/bin/env bash

while :
do
        flag=$(ps -aux | grep  walle.jar | grep -v grep)
        if [[ ! "$flag" ]]
        then
                curl -XPOST http://10.1.3.124:9093/api/v1/alerts -d'[{"labels":{"altername": "walle is down"}}]'
                date
                echo walle is down, will be restart ...
                nohup java -jar walle.jar >/dev/null  &
                echo walle is started
        fi
        sleep 20s
done