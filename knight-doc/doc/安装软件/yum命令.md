

**1.使用yum安装和卸载软件**，有个前提是yum安装的软件包都是rpm格式的。

安装的命令是，yum install ~，yum会查询[数据库](https://cloud.tencent.com/solution/database?from=20065&from_column=20065)，有无这一软件包，如果有，则检查其依赖冲突关系，如果没有依赖冲突，那么最好，下载安装;如果有，则会给出提示，询问是否要同时安装依赖，或删除冲突的包，你可以自己作出判断；

删除的命令是，yum remove ~，同安装一样，yum也会查询数据库，给出解决依赖关系的提示。

其中~代表软件名

1) 用YUM安装软件包命令：yum install ~

2) 用YUM删除软件包命令：yum remove ~

**2.用yum查询想安装的软件**

我们常会碰到这样的情况，想安装一个软件，只知道它和某方面有关，但又不能确切知道它的名字。这时yum的查询功能就起作用了。我们可以用yum search keyword这样的命令来进行搜索，比如我们要则安装一个InstantMessenger，但又不知到底有哪些，这时不妨用yum search messenger这样的指令进行搜索，yum会搜索所有可用rpm的描述，列出所有描述中和messeger有关的rpm包，于是我们可能得到 gaim，kopete等等，并从中选择。

有时我们还会碰到安装了一个包，但又不知道其用途，我们可以用yuminfo packagename这个指令来获取信息。

1) 使用YUM查找软件包

命令：yum search ~

2.列出所有可安装的软件包

命令：yum list

3.列出所有可更新的软件包

命令：yum list updates

4.列出所有已安装的软件包

命令：yum list installed

5.列出所有已安装但不在Yum Repository內的软件包

命令：yum list extras

6.列出所指定软件包

命令：yum list～

7.使用YUM获取软件包信息

命令：yum info～

8.列出所有软件包的信息

命令：yum info

9.列出所有可更新的软件包信息

命令：yum info updates

10.列出所有已安裝的软件包信息

命令：yum info installed

11.列出所有已安裝但不在Yum Repository內的软件包信息

命令：yum info extras

12.列出软件包提供哪些文件

命令：yum provides ~

**3.清除YUM缓存**

yum会把下载的软件包和header存储在cache中，而不会自动删除。如果我们觉得它们占用了磁盘空间，可以使用yum clean指令进行清除，更精确的用法是yumclean headers清除header，yum clean packages清除下载的rpm包，yum clean all 清除所有。

1.清除缓存目录(/var/cache/yum)下的软件包

命令：yum clean packages

2.清除缓存目录(/var/cache/yum)下的 headers

命令：yum clean headers

3.清除缓存目录(/var/cache/yum)下旧的 headers

命令：yum clean oldheaders

4.清除缓存目录(/var/cache/yum)下的软件包及旧的headers

命令：yum clean, yum clean all (= yum clean packages; yum clean oldheaders)

**4.yum命令工具使用举例**

yum update 升级系统

yum install ～ 安装指定软件包

yum update～ 升级指定软件包

yum remove～ 卸载指定软件

yum grouplist 查看系统中已经安装的和可用的软件组，可用的可以安装

yum groupinstall～ 安装上一个命令显示的可用的软件组中的一个

yum groupupdate～ 更新指定软件组的软件包

yum groupremove～ 卸载指定软件组中的软件包

yum deplist～ 查询指定软件包的依赖关系

yum list yum\* 列出所有以yum开头的软件包

yum localinstall ～ 从硬盘安装rpm包并使用yum解决依赖

**5.yum高级管理应用技巧**

技巧1: 加快你的yum的速度.使用yum的扩展插件yum-fastestmirror，个人认为这个插件非常有效，速度真的是明显提高，

#yum -y install yum-fastestmirror

注意，在CentOS 4上,名字叫yum-plugin-fastestmirror

技巧2: 扩展你的rpm包好多包官方没有,怎么搞定他.要我自己编译吗?好了，你安装这个包,这个是RedHat5的哦。你可以自己到php#B”>http://dag.wieers.com/rpm/FAQ.php#B这来找

# Red Hat Enterprise Linux 5 / i386:

rpm-Uhvhttp://apt.sw.be/redhat/el5/en/i386/rpmforge/RPMS/rpmforge-release-0.3.6-1.el5.rf.i386.rpm

# Red HatEnterprise Linux 5 / x86_64:

rpm-Uhvhttp://apt.sw.be/redhat/el5/en/x86_64/rpmforge/RPMS//rpmforge-release-0.3.6-1.el5.rf.x86_64.rpm

#ATrpms

[atrpms]

name=CentOS-$releasever – ATrpms

baseurl=http://dl.atrpms.net/elreleasever−basearch/atrpms/stable

gpgcheck=1

gpgkey=[http://ATrpms.net/RPM-GPG-KEY.atrpms](http://atrpms.net/RPM-GPG-KEY.atrpms)

技巧3: rpm查找.还是有rpm包找不到怎么办,到下面这个网站。基本上都收集全了，你可以用高级查找看看.[http://rpm.pbone.net/](http://rpm.pbone.net/)

技巧4: 通过yum工具下载RPM源码包。前提是有安装yum-utils这个软件包.如果有安装的话。

#yum downloader –source ; RPM源码包

#yum downloader –source vsftpd

当然,没有源包的话,还要加入一个源

[linux-src]

name=Centosreleasever−basearch-Source

baseurl=http://mirrors.163.com/centos/$releasever/os/SRPMS/

enabled=1

gpgcheck=1

gpgkey=file:///etc/pki/rpm-gpg/RPM-GPG-KEY-redhat-release

技巧5: 软件组安装有时我们安装完系统，管理有一类软件没有安装，比如用于开发的开发包,我们可以用软件包来安装。

#yum grouplist这样可以列出所有的软件包

比如我们要安装开发有关的包

#yum groupinstall “Development Libraries”

#yum groupinstall “Development Tools”

比如我们要安装中方支持

#yum groupinstall “Chinese Support”

#yum deplist package1 #查看程序package1依赖情况

以上所有命令参数的使用都可以用man来查看：

[root@F7常用文档]$ man yum

yum -y install 包名 （支持*）：自动选择y，全自动

yum install 包名（支持*）：手动选择y or n

yum remove 包名（不支持*）

rpm -ivh 包名（支持*）：安装rpm包

rpm -e 包名（不支持*）：卸载rpm包

升级内核：#yum install kernel-headers kernel-devel

===========================================

注:当第一次使用yum或yum资源库有更新时,yum会自动下载所有所需的headers放置于/var/cache/yum目录下,所需时间可能较长.

rpm包的更新

* 检查可更新的rpm包

#yum check-update

* 更新所有的rpm包

#yum update

* 更新指定的rpm包,如更新kernel和kernel source

#yum update kernel kernel-source

* 大规模的版本升级,与yum update不同的是,连旧的淘汰的包也升级

#yum upgrade

rpm包的安装和删除

* 安装rpm包,如xmms-mp3

#yum install xmms-mp3

* 删除rpm包,包括与该包有倚赖性的包

#yum remove licq

* 注:同时会提示删除licq-gnome,licq-qt,licq-text

yum暂存(/var/cache/yum/)的相关参数

* 清除暂存中rpm包文件

#yum clean packages

* 清除暂存中rpm头文件

#yum clearn headers

* 清除暂存中旧的rpm头文件

#yum clean oldheaders

* 清除暂存中旧的rpm头文件和包文件

#yum clearn 或#yum clearn all

* 注:相当于yum clean packages + yum clean oldheaders

包列表

* 列出资源库中所有可以安装或更新的rpm包

#yum list

* 列出资源库中特定的可以安装或更新以及已经安装的rpm包

#yum list mozilla

#yum list mozilla*

* 注:可以在rpm包名中使用匹配符,如列出所有以mozilla开头的rpm包

* 列出资源库中所有可以更新的rpm包

#yum list updates

* 列出已经安装的所有的rpm包

#yum list installed

* 列出已经安装的但是不包含在资源库中的rpm包

#yum list extras

* 注:通过其它网站下载安装的rpm包

rpm包信息显示(info参数同list)

* 列出资源库中所有可以安装或更新的rpm包的信息

#yum info

* 列出资源库中特定的可以安装或更新以及已经安装的rpm包的信息

#yum info mozilla

#yum info mozilla*

* 注:可以在rpm包名中使用匹配符,如列出所有以mozilla开头的rpm包的信息

* 列出资源库中所有可以更新的rpm包的信息

#yum info updates

* 列出已经安装的所有的rpm包的信息

#yum info installed

* 列出已经安装的但是不包含在资源库中的rpm包的信息

#yum info extras

* 注:通过其它网站下载安装的rpm包的信息

搜索rpm包

* 搜索匹配特定字符的rpm包

#yum search mozilla

* 注:在rpm包名,包描述等中搜索

* 搜索有包含特定文件名的rpm包

#yum provides realplay

​​​​​​​===========================================

增加资源库

* 例如:增加rpm.livna.org作为资源库

* 安装Livna.org rpms GPG key

#rpm –import http://rpm.livna.org/RPM-LIVNA-GPG-KEY

* 检查GPG Key

# rpm -qa gpg-pubkey*

* 显示Key信息

#rpm -qi gpg-pubkey-a109b1ec-3f6e28d5

* (注:如果要删除Key,使用#rpm -e gpg-pubkey-a109b1ec-3f6e28d5)

​​​​​​​===========================================

CentOS 使用 yum update 命令不升级内核和操作系统的方法

在 /etc/yum.conf 的 [main]后面添加

[main]

exclude=kernel*

exclude=centos-release*

======================

cat /etc/yum.conf

…………

exclude=mongodb-org,mongodb-org-server,mongodb-org-shell,mongodb-org-mongos,mongodb-org-tools

======================

[root@bb ~]# rpm -qa | grep mongodb

mongodb-org-server-3.6.4-1.el7.x86_64

mongodb-org-shell-3.6.4-1.el7.x86_64

mongodb-org-tools-3.6.4-1.el7.x86_64

mongodb-org-mongos-3.6.4-1.el7.x86_64

mongodb-org-3.6.4-1.el7.x86_64

​​​​​​​===========================================

**yum安装器**

命令 解析

yum -y install [package] 下载并安装一个rpm包

yum localinstall [package.rpm] 安装一个rpm包，使用你自己的软件仓库解决所有依赖关系

yum -y update 更新当前系统中安装的所有rpm包

yum update [package] 更新一个rpm包

yum remove [package] 删除一个rpm包

yum list 列出当前系统中安装的所有包

yum search [package] 在rpm仓库中搜寻软件包

yum clean [package] 清除缓存目录（/var/cache/yum）下的软件包

yum clean headers 删除所有头文件

yum clean all 删除所有缓存的包和头文件

===========================================

**yum常用命令** 1.列出所有可更新的软件清单命令：yum check-update 2.更新所有软件命令：yum update 3.仅安装指定的软件命令：yum install <package_name> 4.仅更新指定的软件命令：yum update <package_name> 5.列出所有可安裝的软件清单命令：yum list 6.删除软件包命令：yum remove <package_name> 7.查找软件包 命令：yum search <keyword> 8.清除缓存命令: yum clean packages: 清除缓存目录下的软件包 yum clean headers: 清除缓存目录下的 headers yum clean oldheaders: 清除缓存目录下旧的 headers yum clean, yum clean all (= yum clean packages; yum clean oldheaders) :清除缓存目录下的软件包及旧的headers