下载安装包
[https://dlcdn.apache.org/tomcat/tomcat-10/v10.0.22/bin/apache-tomcat-10.0.22.tar.gz](https://dlcdn.apache.org/tomcat/tomcat-10/v10.0.22/bin/apache-tomcat-10.0.22.tar.gz)

其他版本请去官网上搜索
[https://tomcat.apache.org/](https://tomcat.apache.org/)

把包上传到指定目录，然后解压
tar -zxvf apache-tomcat-10.0.22.tar.gz

vi /etc/profile

配置Tomcat环境变量
```
export CATALINA_HOME=/root/apache-tomcat-8.5.84
export PATH=$PATH:$CATALINA_HOME/bin
```

source /etc/profile

在tomcat安装目录下的conf目录下，找到startup.sh脚本并启动

我们看到了“Tomcat started”字样，大致代表着Tomcat已经启动了。至于是否成功，我们下面来检测。

检测Tomcat启动结果

1.检查java进程是否存在
netstat -anp | grep 8080

#停止firewall
systemctl stop firewalld.service

其他防火墙命令
#开启firewall
systemctl start firewalld.service

#禁止firewall开机启动
systemctl disable firewalld.service

#查看默认防火墙状态（关闭后显示not running，开启后显示running）
firewall-cmd --state
ps -ef | grep tomcat

tomcat热部署相关：
1、在server.xml中添加这部分
![[Pasted image 20230325191020.png]]
```
<Context debug="0" docBase="D:\code\huaweicloud\jeecg-boot\jeecg-boot-module-system\target\tpm-server" path="/tpm" reloadable="true" />
```


docBase 指定的是要启动加载的文件，精确到文件名
reloadable 如果是true，就是自动加载class文件，无需重启
path 设置访问路径

[http://localhost:8080/tpm](http://localhost:8080/tpm2)

2、apache-tomcat-8.5.82\conf\Catalina\localhost 中增加一个xml文件

姑且命名为tpm2.xml
```
<?xml version="1.0" encoding="UTF-8" ?>
    <Context docBase="D:\code\huaweicloud\jeecg-boot\jeecg-boot-module-system\target\tpm-server" reloadable="true" />
```

![[Pasted image 20230325191246.png]]

[http://localhost:8080/tpm2](http://localhost:8080/tpm2)

PS：经过测试如果上述配置同时存在时先加载server.xml中的工程，再加载 localhost中xml里面配置的部分

