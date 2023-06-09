https://www.cnblogs.com/lmh2072005/p/15827985.html

默认是最新版
brew install postgresql 

初始化数据库（mac下面默认之前没有var，创建以及用root给chown给当前用户 knight）
initdb /usr/local/var/postgres

启动服务
pg_ctl -D /usr/local/var/postgres start


关闭服务
pg_ctl -D /usr/local/var/postgres stop

createdb # 创建数据库
psql # 进入控制台此时不需要 用户名密码
CREATE USER postgres WITH PASSWORD 'password'; # 创建postgres用户 
DROP DATABASE postgres; # 删除默认生成的postgres数据库
GRANT ALL PRIVILEGES ON DATABASE postgres to postgres; # 数据库所有权限赋予postgres用户
ALTER ROLE postgres CREATEDB; # 给postgres用户添加创建数据库的属性

psql -U [user] -d [database] -h [host] -p [post]


#修改配置文件 
vi /var/lib/pgsql/12/data/postgresql.conf 
#将监听地址修改为* #默认listen_addresses配置是注释掉的，所以可以直接在配置文件开头加入该行 
```
isten_addresses='*'
```


### kafka

启动kafka

cd /root/soft/kafka_2.13-3.1.0/bin
./kafka-server-start.sh ../config/server.properties

//挂起运行
 
bin/kafka-server-start.sh config/server.properties
 
//后台运行
 
nohup ./kafka-server-start.sh ../config/server.properties >/root/soft/kafka_2.13-3.1.0/logs/qidong.log 2>&1 &

修改server中ip以及端口
/Users/knight/Library/Mobile Documents/iCloud~md~obsidian/Documents/knight/knight/附件和图片/server.properties

./kafka-server-start.sh -daemon  ../config/server.properties
./kafka-server-stop.sh -daemon  ../config/server.properties