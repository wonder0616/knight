##### 生成Tomcat证书

1、按照如下命令输入

keytool -genkey -alias tomcat -keyalg RSA

密码要记住，后继配置server.xml需要用到，例如这里我们输入Huawei123

First and last name 输入：collaborative

组织organization 输入：dcfc

City or Locality：nanjing

Province :jiangsu

简写：js

确认信息后输入：yes

如下图：

下一步可以输入刚才设置一样的密码Huawei123，会在家目录下生成一个.keystore文件

2、把生成的.keystore文件挪到webapps下

mv /root/.keystore /root/apache-tomcat-8.5.84/webapps

3、修改tomcat协议为https

cd /root/apache-tomcat-8.5.84/conf

vi server.xml

找到这个部分，默认是注释掉的

![](file:///C:/Users/ZHANGE~1/AppData/Local/Temp/ksohtml13296/wps5.jpg) 

增加如下部分：
```
<Connector port="8443" protocol="HTTP/1.1"

           maxThreads="150" SSLEnabled="true" scheme="https" secure="true"

           clientAuth="false" sslProtocol="TLS"

           keystoreFile="/root/apache-tomcat-8.5.84/webapps/.keystore"

           keystorePass="Huawei123" >

</Connector>
```
<Connector port="8443" protocol="HTTP/1.1"

           maxThreads="150" SSLEnabled="true" scheme="https" secure="true"

           clientAuth="false" sslProtocol="TLS"

           keystoreFile="/root/apache-tomcat-8.5.84/webapps/.keystore"

           keystorePass="Huawei123" >

</Connector>

其中 keystoreFile 是上面生成证书的位置
keystorePass 是生成证书时设置的密码

导入服务端提供的证书：
使用jdk的自带工具
进入jdk安全管理目录
cd ${JRE_HOME}/lib/security/

导入证书：keytool -import -v -trustcacerts -alias 你的证书别名  -file 证书路径 -storepass changeit -keystore cacerts
删除证书：keytool -delete -alias 你的证书别名 -keystore “%JAVA_HOME%/jre/lib/security/cacerts” -storepass changeit

导入证书
keytool -import -v -trustcacerts -alias b-isdpcloud-huawei-com  -file /root/apache-tomcat-8.5.84/webapps/b-isdpcloud-huawei-com-chain.pem -storepass changeit -keystore cacerts

keytool -delete -alias b-isdpcloud-huawei-com -keystore “%JAVA_HOME%/jre/lib/security/cacerts” -storepass changeit