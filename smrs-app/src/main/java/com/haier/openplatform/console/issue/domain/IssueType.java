package com.haier.openplatform.console.issue.domain;

import com.haier.openplatform.domain.BaseDomain;

/**
 * @author shanjing 持久化类：错误类型
 */
public class IssueType extends BaseDomain<Long> {

	private static final long serialVersionUID = 3172419971532002546L;

	/**
	 * 异常类型名称
	 */
	private String name;

	/**
	 * 异常描述
	 */
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "IssueType [name=" + name + ", description=" + description + "]";
	}

}
