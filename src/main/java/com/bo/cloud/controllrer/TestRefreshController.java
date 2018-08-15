package com.bo.cloud.controllrer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bo.cloud.domain.TestRefresh;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Description 
 * @Author Bo
 * @Version 2018年8月15日　上午9:41:00
 * @码云 https://gitee.com/LeisureBo
 */
@RestController
public class TestRefreshController {
	
	@Autowired
	private TestRefresh testRefresh;
	
	@RequestMapping("/getTest")
	public TestRefresh test01() {
		ObjectMapper mapper = new ObjectMapper();
		// 使用@RefreshScope修饰的类会产生代理实例不能直接用于Json序列化
		try {
			System.out.println("before json serialize: " + testRefresh.toString());
			String result = mapper.writeValueAsString(testRefresh);
			System.out.println("after json serialize: " + result);
		} catch (JsonProcessingException e) {
			System.out.println("c.f.j.d.e.InvalidDefinitionException: " + e.getMessage());
		}
		// 但可Json序列化使用代理实例clone方法返回的副本
		TestRefresh copy = testRefresh.clone();
		System.out.println("copy: " + copy.toString());
		return copy;
	}
}
