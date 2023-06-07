win11菜单换成win10

1.启动Windows 11，单击屏幕底部第二个搜索图标，键入“注册表编辑器”，在搜索结果中打开注册表编辑器。
2.然后像在 注册表编辑器中逐步浏览文件夹，找到以下目录：
HKEY_CURRENT_USER\Software\Microsoft\Windows\CurrentVersion\Explorer\Advanced\

3.进入“Advanced”文件夹后，右键单击以创建新的DWORD（32 位）值，将其命名为Start_ShowClassicMode
![[Pasted image 20230325200418.png]]

4.右键单击该值，选择“修改”，将“数值数据”的值设置为 “1”，然后重新启动计算机。执行此操作后，“开始”菜单将恢复到Windows 10下的样子。单击屏幕底部的开始图标，将会出现Windows 10中的动态磁贴样式。

5.如果你想把它切换回来，你需要在注册表编辑器中重复以上操作，将“数值数据”的值设置为“0”，然后，重新启动电脑即可恢复原状。