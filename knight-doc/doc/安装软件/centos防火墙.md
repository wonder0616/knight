>查看防火墙状态
- systemctl status firewalld
>开启80端口
- firewall-cmd --zone=public --add-port=80/tcp --permanent
>开启3306端口
- firewall-cmd --zone=public --add-port=3306/tcp --permanent
>开启6379端口
- firewall-cmd --zone=public --add-port=6379/tcp --permanent
>重启防火墙：
- firewall-cmd --reload
- systemctl start firewalld

>关闭防火墙
- systemctl stop firewalld
>示例
- firewall-cmd --zone=public --add-port=38443/tcp --permanent
- firewall-cmd --zone=public --add-port=2181/tcp --permanent
- firewall-cmd --zone=public --add-port=6379/tcp --permanent
- firewall-cmd --zone=public --add-port=9092/tcp --permanent
- firewall-cmd --zone=public --add-port=5432/tcp --permanent
- firewall-cmd --zone=public --add-port=23560/tcp --permanent
