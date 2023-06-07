## zerotier


https://my.zerotier.com/network/12ac4a1e71b2f5d9


1、在线安装zerotier

```shell
curl -s https://install.zerotier.com/ | sudo bash
```

2、添加开机自启

```shell
sudo systemctl enable zerotier-one.service
```

3、启动zerotier-one.service

```shell
sudo systemctl start zerotier-one.service
```

4、加入网络

```shell
sudo zerotier-cli join 12ac4a1e71b2f5d9
```