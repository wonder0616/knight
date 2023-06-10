### kafka

1. 启动kafka

cd /root/soft/kafka_2.13-3.1.0/bin
./kafka-server-start.sh ../config/server.properties

2. 挂起运行

bin/kafka-server-start.sh config/server.properties

3. 后台运行

nohup ./kafka-server-start.sh ../config/server.properties >/root/soft/kafka_2.13-3.1.0/logs/qidong.log 2>&1 &

4. 修改server中ip以及端口

> 配置文件
> /Users/knight/Library/Mobile Documents/iCloud~md~obsidian/Documents/knight/knight/附件和图片/server.properties

5. 命令

> ./kafka-server-start.sh -daemon  ../config/server.properties
> ./kafka-server-stop.sh -daemon  ../config/server.properties
