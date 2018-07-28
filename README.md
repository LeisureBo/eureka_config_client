# Eureka_Config_Client

## 配置动态刷新
* 在配置客户端依赖的基础上添加如下依赖：
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
* 开放actuator所有页面节点包括refresh等：
```yml
management:
  endpoints:
    web:
      exposure:
        #暴露bus-refresh节点，通过此节点刷新配置
        include: '*'
```
* 读取远程配置controller添加@RefreshScope注解：
```java
@RestController
@RefreshScope
public class TestController {
	
	@Value("${foo}")
	public String foo;
	
	@RequestMapping("/getfoo")
	public String getfoo() {
		return foo;
	}
}
```
* 读取远程配置属性foo：<br>
		例如：333
* 修改远程配置foo为111，发现访问controller读取foo没有改变，此时`POST`访问http://127.0.0.1:{port}/actuator/refresh，重新访问controller:<br>
		foo属性已变为：111
## 使用bus消息总线动态更新微服务集群配置
* 在上文基础上添加bus消息总线依赖，使用rabbitmq作为消息代理：
```xml
<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
```
* 添加rabbitmq配置，可配置在config-server：
```yml
spring:
  rabbitmq:
    host: 127.0.0.1
    port: 5672
    username: guest
    password: guest
    virtual-host: /
```
* 部署两个config-client微服务，二者读取同一远程配置
修改远程配置属性foo，`POST`访问其中的一个微服务的刷新端点：http://127.0.0.1:{port}/actuator/bus-refresh, 访问另一个微服务读取配置，发现已正确读取刚刚修改的属性foo
