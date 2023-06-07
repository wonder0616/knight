
## mysql
localhost 3306
12345678

## redis
127.0.0.1 6379
123456
cd /usr/local/redis
./bin/redis-server redis.conf

## maven仓库
http://localhost:28899/repository/maven-public

/Users/knight/soft/nexus-3.51.0-01-mac/nexus-3.51.0-01/bin

./nexus start
./nexus status

```
<settings xmlns="http://maven.apache.org/SETTINGS/1.2.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.2.0 http://maven.apache.org/xsd/settings-1.2.0.xsd">

 <!--<localRepository>D:\Develop\repository</localRepository>-->
 <localRepository>/Users/knight/.m2/repository</localRepository>


  <servers>
    <server>
      <id>releases</id>
      <username>admin</username>
      <password>admin123</password>
    </server>
    <server>
      <id>snapshots</id>
      <username>admin</username>
      <password>admin123</password>
    </server>
  </servers>
  <mirrors>
    <mirror>
      <id>releases</id>
      <mirrorOf>*</mirrorOf>
      <name>local</name>
      <url>http://localhost:28899/repository/maven-public/</url>
    </mirror>
  </mirrors>
```



### PostgreSQL
123456

安装路径
/Library/PostgreSQL/15

```
Installation Directory: /Library/PostgreSQL/15
Server Installation Directory: /Library/PostgreSQL/15
Data Directory: /Library/PostgreSQL/15/data
Database Port: 5432
Database Superuser: postgres
Operating System Account: postgres
Database Service: postgresql-15
Command Line Tools Installation Directory: /Library/PostgreSQL/15
pgAdmin4 Installation Directory: /Library/PostgreSQL/15/pgAdmin 4
Stack Builder Installation Directory: /Library/PostgreSQL/15
Installation Log: /tmp/install-postgresql.log
```

### postgresql
localhost
postgres
123456