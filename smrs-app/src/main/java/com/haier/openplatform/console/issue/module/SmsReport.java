package com.haier.openplatform.console.issue.module;

/**
 * @author shanjing
 * 结果集：待发送短信
 */
public class SmsReport {

	/**
	 * 应用名
	 */
	private String appNm;

	/**
	 * Service名
	 */
	private String serviceNm;

	/**
	 * 异常数量
	 */
	private long issueNum;

	/**
	 * 阀值
	 */
	private long alertMax;

	/**
	 * ServiceID
	 */
	private long serviceApiId;

	/**
	 * 收信人电话
	 */
	private String phoneNum;

	/**
	 * 收信人姓名
	 */
	private String ownerNm;

	public String getAppNm() {
		return appNm;
	}

	public void setAppNm(String appNm) {
		this.appNm = appNm;
	}

	public String getServiceNm() {
		return serviceNm;
	}

	public void setServiceNm(String serviceNm) {
		this.serviceNm = serviceNm;
	}

	public long getIssueNum() {
		return issueNum;
	}

	public void setIssueNum(long issueNum) {
		this.issueNum = issueNum;
	}

	public long getAlertMax() {
		return alertMax;
	}

	public void setAlertMax(long alertMax) {
		this.alertMax = alertMax;
	}

	public long getServiceApiId() {
		return serviceApiId;
	}

	public void setServiceApiId(long serviceApiId) {
		this.serviceApiId = serviceApiId;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getOwnerNm() {
		return ownerNm;
	}

	public void setOwnerNm(String ownerNm) {
		this.ownerNm = ownerNm;
	}

	@Override
	public String toString() {
		return "SmsReport [appNm=" + appNm + ", serviceNm=" + serviceNm + ", issueNum=" + issueNum + ", alertMax="
				+ alertMax + ", serviceApiId=" + serviceApiId + ", phoneNum=" + phoneNum + ", ownerNm=" + ownerNm + "]";
	}

}
