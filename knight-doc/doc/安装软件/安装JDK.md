centos
[https://www.jb51.net/article/140257.htm](https://www.jb51.net/article/140257.htm)

aarch
[https://blog.csdn.net/anyiVIP/article/details/120379604](https://blog.csdn.net/anyiVIP/article/details/120379604) 

安装jdk：
执行命令
yum install -y java-1.8.0-openjdk-devel.x86_64

执行完后会看见控制台刷出很多输出。

耐心等待至自动安装完成
安装目录在
/usr/lib/jvm/java

vi /etc/profile
```
JAVA_HOME=/usr/lib/jvm/java
JRE_HOME=$JAVA_HOME/jre
PATH=$PATH:$JAVA_HOME/bin
CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export JAVA_HOME
export JRE_HOME
export PATH
export CLASSPATH
```
source /etc/profile

-------------windows------------
安装后默认环境变量
```
C:\Program Files (x86)\Common Files\Oracle\Java\javapath
```

修改环境变量信息
![[windows的jdk的环境变量.png]]
```
JAVA_HOME
D:\Program Files\Java\jdk1.8.0_341

%Java_Home%\bin;%Java_Home%\jre\bin;
```


ubuntu安装
[https://blog.csdn.net/qq_42557044/article/details/124935468](https://blog.csdn.net/qq_42557044/article/details/124935468)

/usr/local/jdk1.8.0_333

```
vi ~/.bashrc
export JAVA_HOME=/usr/local/jdk1.8.0_333
export JRE_HOME=${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
export PATH=${JAVA_HOME}/bin:$PATH
source ~/.bashrc
```

