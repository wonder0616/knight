
https://blog.csdn.net/m0_51706315/article/details/123882355

1. 首先启动系统进入开机界面，在界面中按“e”进入编辑界面。如图（速度要快）
![123](https://img-blog.csdnimg.cn/a5fb952acfeb4004a06639fda3e69824.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAV2Fsa2VyIEYuTQ==,size_20,color_FFFFFF,t_70,g_se,x_16)

2. 进入编辑界面，使用键盘上下键把光标移动到“Linux16”开头内容所在的行数，在行后输入：init=/bin/sh。

3. 输入完init = /bin/sh后点击ctrl+x运行进入单用户模式

4. 接着输入：mount -o remount,rw /
![](https://img-blog.csdnimg.cn/62f6c0ea29834d27b00916daf70fe500.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAV2Fsa2VyIEYuTQ==,size_20,color_FFFFFF,t_70,g_se,x_16)

5. 在新的一行最后输入：passwd,完成后按键盘回车键输入密码两次 
![](https://img-blog.csdnimg.cn/aecd3047311446769b1c41ca89237ab2.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAV2Fsa2VyIEYuTQ==,size_20,color_FFFFFF,t_70,g_se,x_16)

6. 成功输完密码之后输入touch /.autorelabel完成后点击回车
- touch /.autorelabel
![](https://img-blog.csdnimg.cn/953cf54537a847fc8da5d911d721f72d.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAV2Fsa2VyIEYuTQ==,size_20,color_FFFFFF,t_70,g_se,x_16)

7. 继续输入 exec /sbin/init 等待系统自动修改密码后就会自动重启，新的密码生效 
- exec /sbin/init
