package com.haier.openplatform.console.issue.domain;

import com.haier.openplatform.domain.BaseDomain;

/**
 * @author shanjing
 * 持久化类：错误详细信息
 */
public class HopIssueDetailEx extends BaseDomain<Long> {

	private static final long serialVersionUID = 8119137866523379532L;

	/**
	 * 文件存储预留字段
	 */
	private String storeKey;

	/**
	 * 输入参数
	 */
	private String inValue;

	/**
	 * 异常详细信息
	 */
	private String serviceEx;

	/**
	 * SQL栈信息
	 */
	private String insight;

	public String getStoreKey() {
		return storeKey;
	}

	public void setStoreKey(String storeKey) {
		this.storeKey = storeKey;
	}

	public String getInValue() {
		return inValue;
	}

	public void setInValue(String inValue) {
		this.inValue = inValue;
	}

	public String getServiceEx() {
		return serviceEx;
	}

	public void setServiceEx(String serviceEx) {
		this.serviceEx = serviceEx;
	}

	public String getInsight() {
		return insight;
	}

	public void setInsight(String insight) {
		this.insight = insight;
	}

	@Override
	public String toString() {
		return "HopIssueDetailEx [storeKey=" + storeKey + ", inValue=" + inValue + ", serviceEx=" + serviceEx
				+ ", insight=" + insight + ", getId()=" + getId() + "]";
	}

}
