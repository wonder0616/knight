Centos
[https://blog.csdn.net/weixin_45401600/article/details/125172685](https://blog.csdn.net/weixin_45401600/article/details/125172685)

解压压缩包 nexus-3.41.0-01-unix.tar.gz
/root/soft/nexus-3.41.0-01/bin

移到 /data/nexus/nexus-3.31.1-01/bin
移到 /data/nexus/nexus-3.31.1-01/etc/nexus-default.properties  可修改端口
执行 ./nexus start
执行 ./nexus status 查看启动状态

![[Pasted image 20230325193516.png]]
![[Pasted image 20230325193537.png]]
![[Pasted image 20230325193555.png]]
![[Pasted image 20230325193607.png]]
![[Pasted image 20230325193620.png]]

--------

MAC

https://blog.csdn.net/liaowenxiong/article/details/122598964

本地路径
/Users/knight/soft/nexus-3.51.0-01-mac/nexus-3.51.0-01/bin

启动
./nexus start
查看状态
./nexus status
停止
./nexus stop

http://localhost:28899/#browse/welcome