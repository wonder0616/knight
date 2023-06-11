因为[kafka](https://so.csdn.net/so/search?q=kafka&spm=1001.2101.3001.7020)依赖于zookeeper，所以我们先要启动zookeeper再启动kafka
[https://blog.csdn.net/csdnerM/article/details/121848173](https://blog.csdn.net/csdnerM/article/details/121848173)

### 启动zookeeper
- cd /root/soft/apache-zookeeper-3.8.0-bin/bin
- ./zkServer.sh start

/Users/knight/Library/Mobile Documents/iCloud~md~obsidian/Documents/knight/knight/附件和图片/zoo.cfg

[https://blog.csdn.net/lijiaxuan_/article/details/124049304](https://blog.csdn.net/lijiaxuan_/article/details/124049304)


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
