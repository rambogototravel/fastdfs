# fastdfs 客户端
基于netty封装。

# 使用指南
---
下面步骤展示如何在Spring应用中接入fastdfs。   
**注：FastDFS JAR 包仅支持Java 8 或以上版本。**
#### 1.下载源码编译打包，在`pom.xml`文件中加入以下代码： 
```
<dependency>
    <groupId>com.github</groupId>
    <artifactId>fastdfs-spring</artifactId>
    <version>x.y.z</version>
</dependency>
```
#### 2.在配置文件中定义以下相关的bean：
```
<bean id="trackerNode" class="com.github.fastdfs.connection.TrackerNode">
    <constructor-arg name="host" value="192.168.188.219"/>
    <constructor-arg name="port" value="22122"/>
</bean>

<bean id="poolConfig" class="com.github.fastdfs.core.connection.FastDFSPoolConfig"></bean>

<bean id="fastDFSExecutor" class="com.github.fastdfs.core.FastDFSExecutor">
    <property name="poolConfig" ref="poolConfig"/>
</bean>

<bean id="fastDFSConnectionFactory" class="com.github.fastdfs.connection.FastDFSConnectionFactory">
    <property name="tracker" ref="trackerNode"/>
    <property name="executor" ref="fastDFSExecutor"/>
</bean>

<bean id="fastDFSTemplate" class="com.github.fastdfs.core.FastDFSTemplate">
    <property name="connectionFactory" ref="fastDFSConnectionFactory"/>
    <property name="executor" ref="fastDFSExecutor"/>
</bean>
```
#### 3.注入FastDFSTemplate，使用其接口进行文件操作
```
@Autowired
private FastDFSTemplate template;

@Test
public void testUpload() {
    File file = new File("F:" + File.separator + "timg.jpg");
    print(template.upload(file));
}
``` 

---
下面步骤展示如何在Spring Boot应用中接入fastdfs。
#### 1.下载源码编译打包，在`pom.xml`文件中加入以下代码： 
```
<dependency>
    <groupId>com.github</groupId>
    <artifactId>fastdfs-spring-boot-starter</artifactId>
    <version>x.y.z</version>
</dependency>
``` 
#### 2.配置相关属性：
```
fastdfs.host=localhost
fastdfs.port=22122
fastdfs.pool.connectTime=1000
...
```
#### 3.注入FastDFSTemplate，使用其接口进行文件操作
```
@Autowired
private FastDFSTemplate template;

@Test
public void testUpload() {
    File file = new File("F:" + File.separator + "timg.jpg");
    print(template.upload(file));
}
``` 
