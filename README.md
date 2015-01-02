## MailPlus 运行测试指南

**Warning：**

1. **本客户端暂时只支持 163, 126, yeah.net的邮箱**
2. **测试的话不建议用自己的常用邮箱，因为初次下载几千封邮件会很慢**

   推荐测试账号：

   USER: lhfcws_oop@126.com
   
   PASS: lhfcws123456

   里面已经有部分各种案例的测试邮件了，截图亦可在最终文档中看到。

### 运行

#### Windows 用户：

1. 将当前文件夹定位到`mail-plus/bin`下
2. 执行`mailplus.bat`

> 潜在问题：
> Windows的字体表现可能不佳

#### 带桌面的*nix 用户

1. cd mail-plus/bin
2. chmod +x mail-plus.sh   (optional)
3. sh mail-plus.sh


### 测试须知

#### 推荐方法
在Eclipse 或 Idea 等IDE中引入整个项目然后直接跑测试类。

单元测试案例位于 `mail-plus/src/test/java/unittest/` 下




