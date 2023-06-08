## ssh连接虚拟机慢解决方法

>注意：修改之后记得重启sshd服务
- service sshd restart

1. 关闭DNS反向解析
>在linux中，默认就是开启了SSH的反向DNS解析,这个会消耗大量时间，因此需要关闭。
- vi /etc/ssh/sshd_config
- UseDNS=no
>在配置文件中，虽然UseDNS yes是被注释的，但默认开关就是yes

2. 关闭SERVER上的GSS认证
>在authentication gssapi-with-mic有很大的可能出现问题，因此关闭GSS认证可以提高ssh连接速度。
- vi /etc/ssh/sshd_config
- GSSAPIAuthentication no
3. 修改server上nsswitch.conf文件
- vi /etc/nsswitch.conf
>找到
hosts： files dns
改为
hosts：files