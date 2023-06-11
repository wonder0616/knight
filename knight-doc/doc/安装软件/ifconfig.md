# Linux中找不到ifconfig命令的解决方法

1. fconfig命令是设置或显示网络接口的程序，可以显示出我们机器的网卡信息，可是有些时候最小化安装CentOS等Linux发行版的时候会默认不安装ifconfig等命令，这时候你进入终端，运行ifconfig命令就会出错

2. 首先想到是不是[环境变量](https://so.csdn.net/so/search?q=%E7%8E%AF%E5%A2%83%E5%8F%98%E9%87%8F&spm=1001.2101.3001.7020)里没有ifconfig命令的路径，因为ifconfig是在/sbin路径下的，以root用户登录才可以运行，看看root用户的环境变量
- echo $PATH

3. 环境变量里有/sbin这个路径，也就是说如果ifconfig命令存在并且就是位于/sbin目录下的话肯定就是可以运行的，那么就看看/sbin目录下有没有ifconfig命令
- ls /sbin | grep ifconfig
>结果表明/sbin目录下并没有ifconfig命令，所以：CentOS里边是没有安装ifconfig

4. 解决办法：使用yum安装ifconfig
- yum search ifconfig

> 通过yum search 这个命令我们发现ifconfig这个命令是在net-tools.x86_64这个包里，接下来只要安装这个包就行了
- yum install net-tools.x86_64

5. 检查是否安装成功
- ifconfig

6. 成功运行了，这时候你是不是和我当初一样有疑惑，windows里的ipconfig命令到了linux为什么有事还不是必须安装的，原来是ifconfig命令来源于net-tools，这个包里有ifconfig，netstat，whois等命令，所以之前这些命令你都是运行不了的

> 注：ifconfig是net-tools中已被废弃使用的一个命令，许多年前就已经没有维护了。iproute2套件里提供了许多增强功能的命令，ip命令即是其中之一。
