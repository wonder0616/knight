windows安装
[https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)

其他版本
[https://archive.apache.org/dist/maven/maven-3/3.8.1/binaries/](https://archive.apache.org/dist/maven/maven-3/3.8.1/binaries/)

增加变量
MAVEN_HOME
D:\Program Files\apache-maven-3.8.6

path中添加
%MAVEN_HOME%\bin

验证是否安装完成
Windows+R-输入cmd-输入mvn-v-显示maven版本则成功
![[Pasted image 20230325193233.png]]

Mac
```
export M="/usr/local/apache-maven-3.8.7"
export PATH="$M/bin:$PATH"
```

```
export M="/Users/knight/soft/apache-maven-3.8.7"
export PATH="$M/bin:$PATH"
```


批量上传jar包
[https://www.dandelioncloud.cn/article/details/1535188524261470209](https://www.dandelioncloud.cn/article/details/1535188524261470209)


工程里面deploy上传到maven私服上，需要在POM.XML文件中增加对应的服务地址
```xml
    <distributionManagement>  
      <repository>         
        <id>releases</id>  
<!--         <url>http://localhost:28899/repository/third-party/</url>-->  
        <url>http://localhost:28899/repository/maven-releases/</url>  
      </repository>      
      <snapshotRepository>         
        <id>snapshots</id>  
<!--         <url>http://localhost:28899/repository/third-party/</url>-->  
         <url>http://localhost:28899/repository/maven-snapshots/</url>  
       </snapshotRepository>  
     </distributionManagement>
```
