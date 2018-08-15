package com.bo.cloud.controllrer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 
 * @author 王博
 * @version 2018年6月28日　下午11:54:59
 * @码云 https://gitee.com/LeisureBo
 */
@RestController
/**
 * @RefreshScope: 便捷注释将@Bean定义放在刷新范围中，以这种方式注释的Bean可以在运行时刷新，并且使用它们的任何组件将在下一个方法调用上获得一个新实例，完全初始化并注入所有依赖项。
 * 配合actuator开放的"/refresh"端点，post访问该端点后将刷新该controller的注入属性值，以便于从远程仓库拉取更新的配置。
 */
@RefreshScope
public class EurekaConfigClientController {
	
	@Value("${foo}")
	private String foo;
	
	@RequestMapping("/getFoo")
	public String getConfigFromRemote() {
		return foo;
	}
}
