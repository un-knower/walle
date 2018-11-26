#!/usr/bin/env bash
for index in {1..16}
do
echo $index
curl -X DELETE -u elastic:elastic "es1:9200/kafka-state-change-kafka${index}"
done