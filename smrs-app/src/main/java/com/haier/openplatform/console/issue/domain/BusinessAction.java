package com.haier.openplatform.console.issue.domain;

import com.haier.openplatform.domain.BaseDomain;

/**
 * @author shanjing 持久化类：业务名称表
 */
public class BusinessAction extends BaseDomain<Long> {

	private static final long serialVersionUID = 4975213113180916123L;

	/**
	 * Action名称
	 */
	private String actionName;

	/**
	 * 应用系统ID
	 */
	private long appId;

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	@Override
	public String toString() {
		return "BusinessAction [actionName=" + actionName + ", appId=" + appId
				+ "]";
	}

}
