#!/bin/bash

ID=`ps -ef| grep 'tpm-server'|grep -v 'grep'|awk '{print $2}'`
for pid in $ID
do
    kill -9 $pid
    echo " kill tpm-server pid:$pid"
done

nohup java -Djava.security.egd=file:/dev/./urandom -jar /root/tpm-server.war 2>&1 &
wait $!
