--------------------centos-------------------
进入 home 目录，创建 minio 文件夹。将 minio运行文件放入创建好的 minio 文件夹下，并对 minio 运行文件赋权限
cd /home/
mkdir minio
cd minio/

minio运行文件位置chmod +x minio
wget [https://dl.min.io/server/minio/release/linux-amd64/minio](https://dl.min.io/server/minio/release/linux-amd64/minio)


在 minio 文件夹下创建data 文件夹，作为所有桶存放的位置。

在 minio文件夹下，新建脚本文件 run.sh
touch run.sh

编辑 runmkdir data
.sh

vi run.sh

将下面的启动脚本放入 run.sh 中，其中配置了 minio 的用户名和密码。最后指定了 minio 的数据存放路径。（使用 vim，不要用可视化工具拖拽到本地修改，可能有编码问题）

export MINIO_ACCESS_KEY=minio
export MINIO_SECRET_KEY=123nohup ./minio server ./data &

运行 run.sh 文件

sh run.sh

查看 minio 进程，确认是否启动成功
ps -ef|grep minio

在浏览器输入 ip + port，默认端口 9000
注意：minio 支持高版本的google 浏览器，如果发现访问 minio页面有问题，可能是浏览器兼容性的问题

mkdir soft
cd soft
mkdir minio
上传minio或下载
wget https://dl.min.io/server/minio/release/linux-amd64/minio

chmod +x minio

启动minio：

MINIO_ACCESS_KEY=minioadmin MINIO_SECRET_KEY=minioadmin nohup /root/soft/minio/minio server --console-address ":9000" --address "0.0.0.0:9001" /root/soft/minio/data/minio > /root/soft/minio/minio.log 2>&1&

MINIO_ACCESS_KEY=minioadmin MINIO_SECRET_KEY=minioadmin nohup /root/minio/minio server --console-address ":9000" --address ":9001" /root/minio/data > /root/minio/minio.log 2>&1&

MINIO_ACCESS_KEY=minioadmin MINIO_SECRET_KEY=minioadmin nohup /root/soft/minio/minio server --console-address ":9000" --address “:9001" /root/soft/minio/data/minio > /root/soft/minio/minio.log 2>&1&

MINIO_ACCESS_KEY=minioadmin MINIO_SECRET_KEY=minioadmin nohup /root/soft/minio/minio server --console-address ":9000" --address “rizhaogang.zhangertong.com:9001" /root/soft/minio/data/minio > /root/soft/minio/minio.log 2>&1&

  

  

MINIO_ACCESS_KEY=minioadmin MINIO_SECRET_KEY=minioadmin nohup /root/minio/minio server --console-address ":9000" —address 
“rizhaogang.zhangertong.com:9001" /root/minio/data > /root/minio/minio.log 2>&1&

mac启动
MINIO_ACCESS_KEY=minioadmin MINIO_SECRET_KEY=minioadmin nohup /Users/knight/soft/minio/minio server --console-address ":9000" --address ":9001" /Users/knight/soft/minio > /Users/knight/soft/minio/minio.log 2>&1&

  

[Unit]
Description=MinIO
Documentation=https://docs.min.io
Wants=network-online.target
After=network-online.target

#minio文件具体位置
AssertFileIsExecutable=/root/soft/minio/data/minio //这个就是下载minio的位置，如果跟着我下载的话，就是在root下

[Service]

User and group 用户 组

User=root
Group=root

#创建的配置文件 minio.conf
EnvironmentFile=/root/soft/minio/conf/minio.conf //刚才我们3中配置的conf地址
ExecStart=/root/soft/minio/minio server $MINIO_OPTS $MINIO_VOLUMES  //这其实就是minio服务启动命令 /root/minio是服务位置 后面是端口号和数据存放目录
Let systemd restart this service always
Restart=always
Specifies the maximum file descriptor number that can be opened by this process
LimitNOFILE=65536
Disable timeout logic and wait until process is stopped
TimeoutStopSec=infinity
SendSIGKILL=no
[Install]
WantedBy=multi-user.target

在浏览器输入 ip + port，默认端口 9000
注意：minio 支持高版本的google 浏览器，如果发现访问 minio页面有问题，可能是浏览器兼容性的问题

[http://150.158.130.169:9000](http://150.158.130.169:9000/buckets)

创建桶：
1、先安装mc

--------------------macOS-----------------
[https://www.jianshu.com/p/32c81fa0fed3](https://www.jianshu.com/p/32c81fa0fed3)