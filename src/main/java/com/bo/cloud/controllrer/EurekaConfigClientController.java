package com.bo.cloud.controllrer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 
 * @author 王博
 * @version 2018年6月28日　下午11:54:59
 * @码云 https://gitee.com/LeisureBo
 */
@RestController
public class EurekaConfigClientController {
	
	@Value("${foo}")
	private String foo;
	
	@RequestMapping("/getfoo")
	public String getConfigFromRemote() {
		return foo;
	}
}
