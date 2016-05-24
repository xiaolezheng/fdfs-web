1. git clone https://github.com/happyfish100/fastdfs.git
2. git clone https://github.com/happyfish100/libfastcommon.git
3. 首先按照INSTALL说明安装 libfastcommon, 然后按照INSTALL说明安装fastdfs
4. 修改配置文件,包括tracker, storage
   1. 修改path路径, 其中tracker配置文件与storage配置文件路径区分开;
   2. 配置tracker server ip地址;
   3. 其他规则根据自己需求进行调整;
5. 按照fastdfs的INSTALL说明文档启动fastdfs服务,包括 tracker, storage
6. spring boot配置文件 application.yml 配置fdfs服务参数
7. application.properties配置spring boot参数
8. 启动方式可以通过直接执行Startup.main 方法; 或者通过tomcat容器启动,不同启动方式需要调整pom.xml;


该工程为spring boot工程
spring boot官方文档