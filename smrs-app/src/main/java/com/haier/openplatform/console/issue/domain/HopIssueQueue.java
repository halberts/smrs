package com.haier.openplatform.console.issue.domain;

import com.haier.openplatform.domain.BaseDomain;

/**
 * @author Mike Yang 持久化类：异常汇总的Queue信息
 */
public class HopIssueQueue extends BaseDomain<Long> {

	private static final long serialVersionUID = -8049458202231306342L;

	/**
	 * 异常类型名称
	 */
	private String issueTypeName;

	/**
	 * Service名称
	 */
	private String serviceApiName;

	/**
	 * 应用名称
	 */
	private String appName;

	/**
	 * Action名称
	 */
	private String actionName;

	/**
	 * 节点名称
	 */
	private String nodeName;

	/**
	 * 节点IP地址
	 */
	private String nodeIp;

	/**
	 * 异常错误码
	 */
	private String errorCode;

	/**
	 * 异常名称
	 */
	private String exceptionName;

	/**
	 * 执行时间
	 */
	private long executeTime;

	/**
	 * 当前线程数
	 */
	private long currentThreadNum;

	public String getIssueTypeName() {
		return issueTypeName;
	}

	public void setIssueTypeName(String issueTypeName) {
		this.issueTypeName = issueTypeName;
	}

	public String getServiceApiName() {
		return serviceApiName;
	}

	public void setServiceApiName(String serviceApiName) {
		this.serviceApiName = serviceApiName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getExceptionName() {
		return exceptionName;
	}

	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}

	public long getCurrentThreadNum() {
		return currentThreadNum;
	}

	public void setCurrentThreadNum(long currentThreadNum) {
		this.currentThreadNum = currentThreadNum;
	}

	public String getNodeIp() {
		return nodeIp;
	}

	public void setNodeIp(String nodeIp) {
		this.nodeIp = nodeIp;
	}

	public long getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(long executeTime) {
		this.executeTime = executeTime;
	}

	@Override
	public String toString() {
		return "HopIssueQueue [issueTypeName=" + issueTypeName
				+ ", serviceApiName=" + serviceApiName + ", appName=" + appName
				+ ", actionName=" + actionName + ", nodeName=" + nodeName
				+ ", nodeIp=" + nodeIp + ", errorCode=" + errorCode
				+ ", exceptionName=" + exceptionName + ", executeTime="
				+ executeTime + ", currentThreadNum=" + currentThreadNum + "]";
	}

}
