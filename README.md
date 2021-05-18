# tong-framework-startup
>tong-framework-startup, 基础代码  
>Java：1.8，Spring Boot：2.2.5RELEASE

数据库:　初始化脚本位于SQL下,导入数据库,并修改配置文件中的连接信息

项目启动:　执行TfwStartupApplication　　

接口管理:   localhost:18083/tfw/doc.html　　　

开发手册参考:　http://10.10.0.102/doc/ 　　 

代码包结构(com.tongtech)

config
配置类

##环境搭建

创建数据库，导入数据库脚本  
使用IDEA等开发工具，导入项目，使用maven下载依赖  
修改main/resource下，yml配置中的数据库连接地址  
通过TfwStartupApplication，启动程序  
通过localhost:18083/tfw/doc.html，可通过swagger查看后端接口    
**lombok插件为必须插件**  

##关键配置

config/SpringMvcConfiguration  
用于配置需要进行token拦截和签名认证的资源URI  
此URI需要通过系统的“菜单管理”功能维护到数据库中，否则将提示资源不存在  
开发阶段，可暂时将两个拦截器注解掉  
  
##打包部署  
使用maven打包, 通过参数使用不同的配置文件  
执行打包命令后,会于target下生成压缩的tar.gz文件  
将压缩文件解压至服务器环境,并执行bin目录下的startup.sh脚本启动    
打包命令:  mvn package -P local  
启动命令:  bin/startup.sh  
停止命令:  bin/shutdown.sh  
日志位置:  logs/logback

