package com.haier.openplatform.console.issue.domain;

import com.haier.openplatform.domain.BaseDomain;

/**
 * @author shanjing 持久化类:
 */
public class BusinessServiceGrp extends BaseDomain<Long> {

	private static final long serialVersionUID = 553012844000386359L;

	/**
	 * 应用系统ID
	 */
	private long appId;

	/**
	 * 分组名称
	 */
	private String serviceGrpName;

	/**
	 * 分组详细信息：ServiceID用逗号分隔
	 */
	private String serviceGrpDetail;

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public String getServiceGrpName() {
		return serviceGrpName;
	}

	public void setServiceGrpName(String serviceGrpName) {
		this.serviceGrpName = serviceGrpName;
	}

	public String getServiceGrpDetail() {
		return serviceGrpDetail;
	}

	public void setServiceGrpDetail(String serviceGrpDetail) {
		this.serviceGrpDetail = serviceGrpDetail;
	}

	@Override
	public String toString() {
		return "BusinessServiceGrp [appId=" + appId + ", serviceGrpName="
				+ serviceGrpName + ", serviceGrpDetail=" + serviceGrpDetail
				+ "]";
	}

}
