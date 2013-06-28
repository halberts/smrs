package com.haier.openplatform.console.sonar.domain;

import com.haier.openplatform.domain.BaseDomain;

public class SonarMetric extends BaseDomain<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3512054759442663846L;

	/**
	 * key
	 */
	private String key;
	/**
	 * key对应的名称
	 */
	private String name;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
