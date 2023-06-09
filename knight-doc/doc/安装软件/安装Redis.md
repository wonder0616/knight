 [https://baijiahao.baidu.com/s?id=1722728002073366376&wfr=spider&for=pc](https://baijiahao.baidu.com/s?id=1722728002073366376&wfr=spider&for=pc)


### 一、下载并解压Redis
1. 执行下面的命令下载redis：
- wget [https://download.redis.io/releases/redis-6.2.6.tar.gz](https://download.redis.io/releases/redis-6.2.6.tar.gz)
2. 解压redis：
- tar xzf redis-6.2.6.tar.gz
3. 移动redis目录，一般都会将redis目录放置到 /usr/local/redis目录：
- mv redis-6.2.6 /usr/local/redis
### 二、编译并安装redis
1. 进入redis安装目录，执行make命令编译redis：
- cd /usr/local/redis
- make
- 等待make命令执行完成即可。
>如果执行make命令报错：cc 未找到命令，原因是虚拟机系统中缺少gcc，执行下面命令安装gcc：
yum -y install gcc automake autoconf libtool make
如果执行make命令报错：致命错误:jemalloc/jemalloc.h: 没有那个文件或目录，则需要在make指定分配器为libc。执行下面命令即可正常编译：
make MALLOC=libc
make命令执行完，redis就编译完成了。

2. 执行下面命令安装redis，并指定安装目录
- make install PREFIX=/usr/local/redis
![[Pasted image 20230325182713.png]]
- 至此，redis即安装成功。

### 三、启动redis
1. 进入redis安装目录，执行下面命令启动redis服务
- ./bin/redis-server redis.conf
- 此时，可以看到redis服务被成功启动：
![[Pasted image 20230325182759.png]]
>但这种启动方式不能退出控制台，如果退出，那么redis服务也会停止。如果想要redis以后台方式运行，
需要修改redis的配置文件：redis.conf。将该配置文件中的daemonize no改为daemonize yes即可：
- vi redis.conf
- daemonize
- ./bin/redis-server redis.conf

>修改完成后，重新执行启动命令启动redis，然后通过下面命令查看redis进程，可以发现redis服务已经被启动了：
- ps -ef | grep redis
![[Pasted image 20230325182856.png]]

2. 通过redis-cli测试redis是否可用，在redis安装目录执行下面命令：
- ./bin/redis-cli
![[Pasted image 20230325182927.png]]
- 获取当前密码：
- config get requirepass
![[Pasted image 20230325182946.png]]
- 设置密码：
- config set requirepass 123456
![[Pasted image 20230325183005.png]]

- 此处我们通过下面命令随便set一个字符串类型的值，key是test，value是hello：
- set test hello
![[Pasted image 20230325183032.png]]
- 然后通过下面命令get出test这个key的value值：
- get test
![[Pasted image 20230325183050.png]]

### 开放外网访问模式
- /www/server/redis
- vi redis.conf
- 找到下面这三个参数：

```
bind 127.0.0.1（绑定允许访问的ip）
protected-mode yes（保护模式开）
#requirepass yourpassword （请求访问的密码）
```

>一二项是组合项，尤为重要，第三项就是设置密码，在一定程度上对服务区的保护。

- 修改为如下：
```
#bind 127.0.0.1   改成当前服务器的ip或者直接注释
protected-mode no
requirepass 你的密码
```

- 修改后重启redis
- /www/server/redis/src/
- /www/server/redis/src
- ./redis-cli
> 每次启动关闭redis甚是麻烦，不如直接注册为服务。
- 执行如下命令创建redis服务:
- vi /etc/systemd/system/redis.service
- 添加如下命令：
```
[Unit]
Description=redis-server
After=network.target

[Service]
Type=forking
ExecStart=/www/server/redis/src/redis-server /www/server/redis/redis.conf
PrivateTmp=true

[Install]
WantedBy=multi-user.target
```
或者
```
[Unit]
Description=redis-server
After=network.target

[Service]
Type=forking
ExecStart=/usr/local/redis/bin/redis-server /usr/local/redis/redis.conf
PrivateTmp=true

[Install]
WantedBy=multi-user.target
```
>其中 ExecStart 参数对应的安装 Redis 目录下的 redis-server，配置文件同上。

- 设置开机启动：
- systemctl daemon-reload
- systemctl start redis.service
- systemctl enable redis.service

- 常用的服务命令
- 启动redis服务
- systemctl start redis.service  
- 停止redis服务
- systemctl stop redis.service
- 重新启动服务
- systemctl restart redis.service
- 查看服务当前状态
- systemctl status redis.service
- 设置开机自启动
- systemctl enable redis.service

- 停止开机自启动
- systemctl disable redis.service
-----------------
### MAC 
https://blog.csdn.net/realize_dream/article/details/106227622

- 在线安装命令
- brew install redis
>Or, if you don't want/need a background service you can just run:

- 启动redis（非后台）
- /opt/homebrew/opt/redis/bin/redis-server /opt/homebrew/etc/redis.conf

5. 查看redis服务进程
- 我们可以通过下面命令查看redis是否正在运行
- ps axu | grep redis
- 1
6. redis-cli连接redis服务
- redis默认端口号6379，默认auth为空，输入以下命令即可连接
- redis-cli -h 127.0.0.1 -p 6379
- 1
7. 启动 redis 客户端，打开终端并输入命令 redis-cli。该命令会连接本地的 redis 服务。
- $redis-cli
- redis 127.0.0.1:6379>
- redis 127.0.0.1:6379> PING
- PONG
>在以上实例中我们连接到本地的 redis 服务并执行 PING 命令，该命令用于检测 redis 服务是否启动。

8.关闭redis服务
- 正确停止Redis的方式应该是向Redis发送SHUTDOWN命令
- redis-cli shutdown
- 1
- 强行终止redis
- sudo pkill redis-server
- 1
9. redis.conf 配置文件详解
- redis默认是前台启动，如果我们想以守护进程的方式运行（后台运行），可以在redis.conf中将daemonize no,修改成yes即可。