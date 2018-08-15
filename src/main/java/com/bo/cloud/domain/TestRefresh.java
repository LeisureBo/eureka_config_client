package com.bo.cloud.domain;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @Description 
 * @Author Bo
 * @Version 2018年8月15日　上午9:32:23
 * @码云 https://gitee.com/LeisureBo
 */
@Component
@ConfigurationProperties
@RefreshScope
public class TestRefresh implements Cloneable, Serializable {
	
	private static final long serialVersionUID = 7031758947064337967L;

	@Value("${test.refresh.name}")
	private String name;
	
	@Value("${test.refresh.profession}")
	private String profession;
	
	@Value("${test.refresh.salary}")
	private double salary;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the profession
	 */
	public String getProfession() {
		return profession;
	}

	/**
	 * @param profession the profession to set
	 */
	public void setProfession(String profession) {
		this.profession = profession;
	}

	/**
	 * @return the salary
	 */
	public double getSalary() {
		return salary;
	}

	/**
	 * @param salary the salary to set
	 */
	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Override
	public TestRefresh clone() {
		TestRefresh testRefresh = null;
		try {
			testRefresh = (TestRefresh) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return testRefresh;
	}

	@Override
	public String toString() {
		return "TestRefresh [name=" + name + ", profession=" + profession + ", salary=" + salary + "]";
	}
	
}
