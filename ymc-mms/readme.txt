Merchant Management System
商家后台管理系统

开发环境搭建
1.构建Maven项目
2.开发环境通过jetty插件运行  需设置 vm参数： 
-server -Xms256m -Xmx512m -XX:PermSize=192M -XX:MaxPermSize=192M 
3.配置jrebel热部署支持
在vm参数中加入jrebel支持：-noverify -javaagent:d:/jrebel.jar
后面的填入jrebel.jar包路径，注意路径的写法
最后在eclipse中设置编译输出路径：${basedir}/src/main/webapp/WEB-INF/classes
4.通过jetty:run启动项目

测试环境打包发布
mvn打包命令：mvn clean package -U
