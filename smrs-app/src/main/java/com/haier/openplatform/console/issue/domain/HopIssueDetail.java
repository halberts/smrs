package com.haier.openplatform.console.issue.domain;

import com.haier.openplatform.domain.BaseDomain;

/**
 * @author shanjing 持久化类：错误详细信息
 */
public class HopIssueDetail extends BaseDomain<Long> {

	private static final long serialVersionUID = -3251985800941701588L;

	/**
	 * 错误类型ID
	 */
	private long issueTypeId;

	/**
	 * Service信息ID
	 */
	private long serviceApiId;

	/**
	 * Actionx信息ID
	 */
	private long actionId;

	/**
	 * 节点ID
	 */
	private long nodeId;

	/**
	 * 错误码
	 */
	private String errorCode;

	/**
	 * 具体异常ID
	 */
	private long exceptionId;

	/**
	 * 数据仓库ID
	 */
	private long timeId;

	/**
	 * 发送邮件标记
	 */
	private String emailStatus;

	/**
	 * 发送短信标记
	 */
	private String smsStatus;

	/**
	 * 实际执行时间
	 */
	private long excuteTime;

	/**
	 * 当前线程数
	 */
	private long currentThreadNum;

	public long getCurrentThreadNum() {
		return currentThreadNum;
	}

	public void setCurrentThreadNum(long currentThreadNum) {
		this.currentThreadNum = currentThreadNum;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public long getExcuteTime() {
		return excuteTime;
	}

	public void setExcuteTime(long excuteTime) {
		this.excuteTime = excuteTime;
	}

	public long getIssueTypeId() {
		return issueTypeId;
	}

	public void setIssueTypeId(long issueTypeId) {
		this.issueTypeId = issueTypeId;
	}

	public long getServiceApiId() {
		return serviceApiId;
	}

	public void setServiceApiId(long serviceApiId) {
		this.serviceApiId = serviceApiId;
	}

	public long getActionId() {
		return actionId;
	}

	public void setActionId(long actionId) {
		this.actionId = actionId;
	}

	public long getNodeId() {
		return nodeId;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	public long getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(long exceptionId) {
		this.exceptionId = exceptionId;
	}

	public long getTimeId() {
		return timeId;
	}

	public void setTimeId(long timeId) {
		this.timeId = timeId;
	}

	public String getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(String emailStatus) {
		this.emailStatus = emailStatus;
	}

	public String getSmsStatus() {
		return smsStatus;
	}

	public void setSmsStatus(String smsStatus) {
		this.smsStatus = smsStatus;
	}

	@Override
	public String toString() {
		return "HopIssueDetail [issueTypeId=" + issueTypeId + ", serviceApiId="
				+ serviceApiId + ", actionId=" + actionId + ", nodeId="
				+ nodeId + ", errorCode=" + errorCode + ", exceptionId="
				+ exceptionId + ", timeId=" + timeId + ", emailStatus="
				+ emailStatus + ", smsStatus=" + smsStatus + ", excuteTime="
				+ excuteTime + ", currentThreadNum=" + currentThreadNum + "]";
	}

}
