package com.haier.openplatform.console.issue.module;

/**
 * @author shanjing
 * 结果集：所有系统所有API中异常数量最多的前10个API
 */
public class AppApiIssue {

	/**
	 * 应用系统名称
	 */
	private String appName;

	/**
	 * Service名称
	 */
	private String serviceName;

	/**
	 * 发生异常次数
	 */
	private Long exceptionNum;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Long getExceptionNum() {
		return exceptionNum;
	}

	public void setExceptionNum(Long exceptionNum) {
		this.exceptionNum = exceptionNum;
	}

	@Override
	public String toString() {
		return "AppApiIssue [appName=" + appName + ", serviceName=" + serviceName + ", exceptionNum=" + exceptionNum
				+ "]";
	}

}
