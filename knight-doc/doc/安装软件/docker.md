安装docker

[https://mp.weixin.qq.com/s?__biz=MjM5NTY1MjY0MQ==&mid=2650860524&idx=3&sn=02dfc31d637f70b066a6ef9842beeac5&chksm=bd017ea28a76f7b466773e68f7dab26e65ffae2918c28aa1d87c84acfc54460a7b82aa57279f&scene=27](https://mp.weixin.qq.com/s?__biz=MjM5NTY1MjY0MQ==&mid=2650860524&idx=3&sn=02dfc31d637f70b066a6ef9842beeac5&chksm=bd017ea28a76f7b466773e68f7dab26e65ffae2918c28aa1d87c84acfc54460a7b82aa57279f&scene=27)

curl -fsSL [https://get.docker.com](https://get.docker.com/) | bash -s docker --mirror Aliyun

curl -sSL [https://get.daocloud.io/docker](https://get.daocloud.io/docker) | sh

查找镜像：

docker scan EulerOS

当前已安装的镜像
docker images

拉取最新的euleros镜像
docker pull euleros:latest
docker pull centos:7.9.2009

增加本地镜像库
cat 镜像包 |docker import docker名（需要小写）：TAG
cat EulerOS_V200R005C00SPC326B001-docker.x86_64.tar.xz | docker import - euleros:latest

启动docker
// 常驻启动
docker run -d -i euleros /bin/bash
docker run -d -i centos:7.9.2009 /bin/bash
docker run -d -i nginx:latest /bin/bash
// 常驻启动 + 内部端口映射
docker run -d -i -p 8012:8012 centos:apache /bin/bash
// 不常驻启动（启动后进入docker），退出docker后 docker就停止运行
docker run -it euleros /bin/bash

连接docker
docker exec -it  4a30e2032c7a bash
docker exec -it -u root  189fa622059d bash

docker cp 容器名:要拷贝的文件在容器里面的路径       要拷贝到宿主机的相应路径
docker cp k8s_tpm-server_tpm-server-6749dff7f9-gw9tj_phm_3bc9ef0a-0141-419a-8ba3-a1b0d5851591_4:/home/cme/log/zhangertong.pdf /home/paas/zhangertong

docker cp 本地文件路径 容器ID/容器NAME:容器内路径

docker cp  xxxx.zip 10568991378c:/root
docker cp    5733c488fc69:/home/cme/log
docker cp  xxxx.zip  c5e08ea32c64:/root

基于某个docker制作镜像

docker commit CONTAINERID  制作后的镜像名称
docker commit 5536df0fa9a1 centos:zhangertong

保存镜像到本地
docker save -o docker-centos-apache-image.tar centos:zhangertong

使用本地镜像
docker load -i [本地tar包文件]
docker load -i
![[Pasted image 20230325193050.png]]
![[Pasted image 20230325193111.png]]

1.停用全部运行中的容器:
docker stop $(docker ps -q)

2.删除全部容器：
docker rm $(docker ps -aq)

3、删除所有镜像
docker rmi -f $(docker images -qa)
docker rmi -f 801dada78aad
