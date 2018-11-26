#!/usr/bin/env bash
# 批量修改filebeat multiline配置
HOST=$(pwd)"/conf/host"
echo $HOST
multiline=$(pwd)"/template/multiline.template"
for hostname in `cat $HOST`
do
    echo "transport to "$hostname
    scp $multiline $hostname:/etc/filebeat/
    ssh $hostname 'bash -s' < $(pwd)"/singleScript/"add_multiline_config.sh
    echo $hostname
done