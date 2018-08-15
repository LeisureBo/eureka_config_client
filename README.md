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
```Java
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
* 读取远程配置属性foo：

		例如：foo=111
		
* 修改远程配置foo=333：

		发现访问controller读取foo没有改变，此时`POST`访问http://127.0.0.1:{port}/actuator/refresh，重新访问controller服务配置，发现foo属行已变为333。
## 配置类动态刷新
在配置类添加@RefreshScope注解，可在触发刷新时刷新从远程读取的配置属性：
```Java
@Component
@ConfigurationProperties
@RefreshScope
public class TestRefresh {
	
	@Value("${test.refresh.name}")
	private String name;
	
	@Value("${test.refresh.profession}")
	private String profession;
	
	@Value("${test.refresh.salary}")
	private double salary;
}
```
* 关于@RefreshScope注解：<br>
1.@RefreshScope这个注解会生成代理类，在其他类中注入的实际上是代理类实例，如果不识别代理请不要使用，直接使用Json序列化会抛出com.fasterxml.jackson.databind.exc.InvalidDefinitionException异常，可使用clone副本进行替代。<br>
2.@RefreshScope作用的类，不能是final类，否则启动时会报错。
## 微服务集群配置动态更新
* 在上文基础上添加bus消息总线依赖，使用rabbitmq作为消息代理：
```xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
```
* 添加rabbitmq配置(可配置在config-server)：
```yml
spring:
  rabbitmq:
    host: 127.0.0.1
    port: 5672
    username: guest
    password: guest
    virtual-host: /
```
* 部署两个config-client微服务(二者可以读取不同远程配置，rabbitmq代理必须相同)：<br>
修改两个微服务各自的远程配置属性，`POST`访问其中一个微服务的刷新端点：http://127.0.0.1:{port}/actuator/bus-refresh, 访问另一个微服务发现已变为刚修改的配置。另外，/actuator/bus-refresh接口可以指定服务，即使用"destination"参数，比如刷新服务名为customers的所有服务："/actuator/bus-refresh?destination=customers:**"
