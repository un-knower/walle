#!/bin/sh
## 批量配置filebeat，并重新启动
path=$(pwd)
for hostname in `cat ${path}/conf/hosts`
do
    sed -i "" "s/xyc/${hostname}/g" ${path}"/template/filebeat.yml"
    scp ${path}"/template/filebeat.yml" root@${hostname}:/etc/filebeat/
    sed -i "" "s/host: ${hostname}/host: xyc/g" ${path}"/template/filebeat.yml"
    ssh root@${hostname} "killall -9 filebeat"
    ssh root@${hostname} "nohup filebeat -e -c /etc/filebeat/filebeat.yml -d publish >/data0/log/filebeat/filebeat.log 2>&1 &"
    echo ${hostname}" is finish!"
done


