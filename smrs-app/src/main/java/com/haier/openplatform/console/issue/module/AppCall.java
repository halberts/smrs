package com.haier.openplatform.console.issue.module;

/**
 * @author shanjing 结果集：全API调用频次和消耗时间
 */
public class AppCall {

	/**
	 * 应用系统名称
	 */
	private String appName;

	/**
	 * 超时数量
	 */
	private Long num;

	/**
	 * 总执行时间
	 */
	private Long executeTime;

	/**
	 * 平均消耗时间
	 */
	private Double averageTime;

	/**
	 * 应用系统ID
	 */
	private Long appId;

	/**
	 * 异常类型ID
	 */
	private Long issueTypeId;

	public Double getAverageTime() {
		return averageTime;
	}

	public void setAverageTime(Double averageTime) {
		this.averageTime = averageTime;
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

	public Long getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(Long executeTime) {
		this.executeTime = executeTime;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public Long getIssueTypeId() {
		return issueTypeId;
	}

	public void setIssueTypeId(Long issueTypeId) {
		this.issueTypeId = issueTypeId;
	}

	@Override
	public String toString() {
		return "AppCall [appName=" + appName + ", num=" + num + ", executeTime=" + executeTime + ", appId=" + appId
				+ ", issueTypeId=" + issueTypeId + "]";
	}

}
