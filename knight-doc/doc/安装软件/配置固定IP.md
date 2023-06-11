## CentOS 7虚拟机下配置固定IP

[https://blog.csdn.net/wa_ka_ka/article/details/121455511](https://blog.csdn.net/wa_ka_ka/article/details/121455511)

### 达到如下效果：

- 笔记本主机IP为设置自动获取，不管什么情况下，不受虚拟机影响，只要连接外网就可以正常上网；

- 只要笔记本主机可以正常访问外网，启动虚拟机中的CentOS 7系统就可以正常访问外网，无需再进行任何设置；

- 虚拟机设置为固定IP，不管主机在什么网络环境下，是断网环境，还是连接任何网段访问外网的环境下，虚拟机的IP都固定不变，而且使用终端连接，始终不变，正常连接；

- 虚拟机的固定IP可以按照自己想设置的IP地址网段随意设置，比如我就想设置固定IP为192.168.2.2。

一、 设置虚拟机的网络连接方式

按照如下图设置，英文版的对照设置即可
![](https://img-blog.csdnimg.cn/img_convert/025f6a388b77b8ee419439a76736fda1.png)

二、 配置虚拟机的NAT模式具体地址参数：

1. 编辑–虚拟网络编辑器–更改设置
![](https://img-blog.csdnimg.cn/img_convert/6f1cba3775bac23b1cc484c746299e8e.png)
2. 选择VMnet8–取消勾选使用本地DHCP–设置子网IP–网关IP设置（记住此处设置，后面要用到），如下图

>说明：修改子网IP设置，实现自由设置固定IP，若你想设置固定IP为192.168.2.2-255，比如192.168.2.2，则子网IP为192.168.2.0；若你想设置固定IP为192.168.1.2-255，比如192.168.1.2，则子网IP为192.168.1.0；
![](https://img-blog.csdnimg.cn/img_convert/d10ff7018d89469644e4207e5d23a19b.png)

3. 网关IP可以参照如下格式修改：192.168.2.1
![](https://img-blog.csdnimg.cn/img_convert/416782e0340e104cccaac53a9b8e0812.png)

三、配置笔记本主机具体VMnet8本地地址参数：
![](https://img-blog.csdnimg.cn/img_convert/f55b7dc82a5fc5b90ce4e772609e4278.png)

>说明：第6步中的IP地址随意设置，但是要保证不能跟你要设置虚拟机的固定IP一样。
![](https://img-blog.csdnimg.cn/img_convert/bdc34fe57508ef08a72c163427831757.png)

四、修改虚拟机中的CentOS 7系统为固定IP的配置文件：
1. 进入centos7命令行界面，修改如下内容：
```
#cd /etc/sysconfig/network-scripts/
#vi ifcfg-eno16777736
```
![](https://img-blog.csdnimg.cn/img_convert/edf4cf47fd86ae6ec91b02df1739aa7e.png)
![](https://img-blog.csdnimg.cn/img_convert/34c7087ad42a0be98528f0fcf1bbda05.png)
>说明
```
#将IPV6…..协议都注释；
BOOTPROTO=static        #开机协议，有dhcp及static；
ONBOOT=yes              #设置为开机启动；
DNS1=114.114.114.114    #这个是国内的DNS地址，是固定的；
IPADDR=192.168.2.2      #你想要设置的固定IP，理论上192.168.2.2-255之间都可以，请自行验证；
NETMASK=255.255.255.0   #子网掩码，不需要修改；
GATEWAY=192.168.2.1     #网关，这里是你在“2.配置虚拟机的NAT模式具体地址参数”中的（2）选择VMnet8--取消勾选使用本地DHCP--设置子网IP--网关IP设置。
```
2. 重启网络服务
- service network restart

五、检验配置是否成功
1. 查看修改后的固定IP为192.168.2.2，配置正确；
- ifconfig
![](https://img-blog.csdnimg.cn/img_convert/99a63f5a2506f5fa53d551b58240030a.png)
2. 测试虚拟机中的CentOS 7系统是否能连外网，有数据返回，说明可以连接外网；
- ping www.baidu.com
![](https://img-blog.csdnimg.cn/img_convert/9ed28f8df0c42682efc292a567a0bc30.png)
3. 测试本机是否能ping通虚拟机的固定IP，有数据返回，说明可以使用终端工具正常连接；
- 鼠标放到开始菜单右键，选择命令提示符（管理员），打开命令操作界面：
- ping 192.168.2.2
![](https://img-blog.csdnimg.cn/img_convert/9c4810e991dca9436698703ea01006be.png)

六、远程终端连接

1. 遇到问题，若连接失败是因为CentOS 7的防火墙端口没有打开，比如开启80，3306端口，最后一定要重启防火墙；
```
#查看防火墙状态
systemctl status firewalld  
#开启80端口
firewall-cmd --zone=public --add-port=80/tcp --permanent  
#开启3306端口
firewall-cmd --zone=public --add-port=3306/tcp --permanent  
#重启防火墙：
firewall-cmd --reload  
```
2. 连接成功
   
![](https://img-blog.csdnimg.cn/img_convert/7bd08abb4842dabdad71302e1c2e2ea9.png)

![](https://img-blog.csdnimg.cn/img_convert/bbcd07b641de62d704f23c048832ee86.png)

>notice: 在网卡重启时报如下错误
```
[root@localhost network-scripts]# service network restart
Restarting network (via systemctl): Job for network.service failed because the control process exited with error code. See “systemctl status network.service” and “journalctl -xe” for details.
[失败]
```
>进一步查看日志详情
```
[root@localhost network-scripts]# journalctl -xe
11月 21 16:02:42 localhost.localdomain network[10391]: 正在打开环回接口： ./ifup： 未发现 ifcfg-lo 配置。
11月 21 16:02:42 localhost.localdomain network[10391]: 用法：ifup <设备名>
11月 21 16:02:42 localhost.localdomain network[10391]: [失败]
```
>报错原因：未发现 ifcfg-lo 配置
![](https://img-blog.csdnimg.cn/bacf6816589c44fdb2f84e8ec6f01ebf.png)
删除这个文件，再次重启网卡，问题解决，配置文件生效！

