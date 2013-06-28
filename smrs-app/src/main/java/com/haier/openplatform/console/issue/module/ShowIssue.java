package com.haier.openplatform.console.issue.module;

/**
 * @author shanjing
 * 结果集对象
 */
public class ShowIssue {

	private Long appId;

	private Long num;

	public ShowIssue(Long appId, Long num) {
		super();
		this.appId = appId;
		this.num = num;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "ShowIssue [appId=" + appId + ", Num=" + num + "]";
	}

}
