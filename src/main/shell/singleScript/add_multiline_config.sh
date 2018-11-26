#!/usr/bin/env bash
# 修改filebeat multiline
beat_config_file=$(ls /etc/filebeat/configs/ | grep zookeeper)
path="/etc/filebeat/configs/"
for config in $(echo $beat_config_file)
do
    sed -i /multiline/d ${path}${config}
    sed -i /pattern/d ${path}${config}
    sed -i /negate/d ${path}${config}
    sed -i /match/d ${path}${config}
    sed -i "/enabled/r /etc/filebeat/multiline.template" ${path}${config}
done