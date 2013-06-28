package com.haier.openplatform.console.issue.module;

/**
 * @author shanjing
 * 结果集：所有业务响应横比按Level
 */
public class ApiCallGroupByLevel {

	/**
	 * 响应级别
	 */
	private String level;

	/**
	 * 应用系统名称
	 */
	private String appName;

	/**
	 * 超时数量
	 */
	private Long num;

	/**
	 * 应用系统ID
	 */
	private Long appId;

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	@Override
	public String toString() {
		return "ApiCallGroupByLevel [level=" + level + ", appName=" + appName + ", num=" + num + ", appId=" + appId
				+ "]";
	}

}
