package com.haier.openplatform.console.issue.domain;

import com.haier.openplatform.domain.BaseDomain;

/**
 * @author shanjing 持久化类：业务对象
 */
public class BusinessService extends BaseDomain<Long> {

	private static final long serialVersionUID = 3931508706553354042L;

	/**
	 * Service名称
	 */
	private String serviceApiName;

	/**
	 * 应用系统ID
	 */
	private long appId;

	/**
	 * 是否监控这个Service的所有异常
	 */
	private String serviceVip;

	/**
	 * 业务分组ID
	 */
	private long serviceGrpId;

	/**
	 * 阀值下限
	 */
	private long alertMin;

	/**
	 * 阀值上限
	 */
	private long alertMax;

	/**
	 * 超时级别
	 */
	private String overtimeLv;

	/**
	 * API平均执行时间(暂时未使用)
	 */
	private long apiDailyAve;

	public long getServiceGrpId() {
		return serviceGrpId;
	}

	public void setServiceGrpId(long serviceGrpId) {
		this.serviceGrpId = serviceGrpId;
	}

	public String getServiceApiName() {
		return serviceApiName;
	}

	public void setServiceApiName(String serviceApiName) {
		this.serviceApiName = serviceApiName;
	}

	public String getServiceVip() {
		return serviceVip;
	}

	public void setServiceVip(String serviceVip) {
		this.serviceVip = serviceVip;
	}

	public long getAlertMin() {
		return alertMin;
	}

	public void setAlertMin(long alertMin) {
		this.alertMin = alertMin;
	}

	public long getAlertMax() {
		return alertMax;
	}

	public void setAlertMax(long alertMax) {
		this.alertMax = alertMax;
	}

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public String getOvertimeLv() {
		return overtimeLv;
	}

	public void setOvertimeLv(String overtimeLv) {
		this.overtimeLv = overtimeLv;
	}

	public long getApiDailyAve() {
		return apiDailyAve;
	}

	public void setApiDailyAve(long apiDailyAve) {
		this.apiDailyAve = apiDailyAve;
	}

	@Override
	public String toString() {
		return "BusinessService [serviceApiName=" + serviceApiName + ", appId="
				+ appId + ", serviceVip=" + serviceVip + ", serviceGrpId="
				+ serviceGrpId + ", alertMin=" + alertMin + ", alertMax="
				+ alertMax + ", overtimeLv=" + overtimeLv + ", apiDailyAve="
				+ apiDailyAve + "]";
	}

}
