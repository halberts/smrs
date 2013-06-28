package com.haier.openplatform.console.issue.module;

import java.sql.Clob;

/**
 * 
 * @author Aaron_Guan
 * 
 */
public class IssueModel {

	/**
	 * 应用名
	 */
	private String appName;

	/**
	 * Service名
	 */
	private String serviceName;

	/**
	 * 异常数量
	 */
	private long issueId;

	/**
	 * identify what exact type is this issue
	 */
	private long issueType;

	/**
	 * ServiceID
	 */
	private long serviceApiId;

	/**
	 * status flag of email
	 */
	private long emailStatus;

	/**
	 * status flag of sms
	 */
	private long smsStatus;

	/**
	 * 收件人邮箱
	 */
	private String ownerEmail;

	/**
	 * 收件人姓名
	 */
	private String ownerName;

	/**
	 * 
	 */
	private String ownerPhone;
	
	/**
	 * SPT owner name
	 */
	private String sptOwnerName;
	/**
	 * SPT owner email
	 */
	private String sptOwnerEmail;
	/**
	 * SPT owner phone
	 */
	private String sptOwnerPhone;
	
	/**
	 * exception detail
	 */
	private Clob serviceException;
	
	private String exceptionName;

	private long alertMax;

	private long alertMin;

	private long count;

	private double duration;

	public String getSptOwnerEmail() {
		return sptOwnerEmail;
	}

	public void setSptOwnerEmail(String sptOwnerEmail) {
		this.sptOwnerEmail = sptOwnerEmail;
	}

	public String getSptOwnerName() {
		return sptOwnerName;
	}

	public void setSptOwnerName(String sptOwnerName) {
		this.sptOwnerName = sptOwnerName;
	}

	public String getSptOwnerPhone() {
		return sptOwnerPhone;
	}

	public void setSptOwnerPhone(String sptOwnerPhone) {
		this.sptOwnerPhone = sptOwnerPhone;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public long getAlertMax() {
		return alertMax;
	}

	public void setAlertMax(long alertMax) {
		this.alertMax = alertMax;
	}

	public long getAlertMin() {
		return alertMin;
	}

	public void setAlertMin(long alertMin) {
		this.alertMin = alertMin;
	}

	public String getOwnerPhone() {
		return ownerPhone;
	}

	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
	}

	public long getIssueType() {
		return issueType;
	}

	public void setIssueType(long issueType) {
		this.issueType = issueType;
	}

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

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Clob getServiceException() {
		return serviceException;
	}

	public void setServiceException(Clob serviceException) {
		this.serviceException = serviceException;
	}

	public long getIssueId() {
		return issueId;
	}

	public void setIssueId(long issueId) {
		this.issueId = issueId;
	}

	public long getServiceApiId() {
		return serviceApiId;
	}

	public void setServiceApiId(long serviceApiId) {
		this.serviceApiId = serviceApiId;
	}

	public long getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(long emailStatus) {
		this.emailStatus = emailStatus;
	}

	public long getSmsStatus() {
		return smsStatus;
	}

	public void setSmsStatus(long smsStatus) {
		this.smsStatus = smsStatus;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	@Override
	public String toString() {
		return "IssueModel [appName=" + appName + ", serviceName="
				+ serviceName + ", issueId=" + issueId + ", issueType="
				+ issueType + ", serviceApiId=" + serviceApiId
				+ ", emailStatus=" + emailStatus + ", smsStatus=" + smsStatus
				+ ", ownerEmail=" + ownerEmail + ", ownerName=" + ownerName
				+ ", ownerPhone=" + ownerPhone + ", serviceException="
				+ serviceException + ", alertMax=" + alertMax + ", alertMin="
				+ alertMin + "]";
	}

	public String getExceptionName() {
		return exceptionName;
	}

	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}

}
