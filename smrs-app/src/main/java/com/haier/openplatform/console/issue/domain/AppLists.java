package com.haier.openplatform.console.issue.domain;

import com.haier.openplatform.domain.BaseDomain;

/**
 * @author shanjing 持久化类：应用列表
 */
public class AppLists extends BaseDomain<Long> {

	private static final long serialVersionUID = -2924450221574978605L;

	/**
	 * 应用名称
	 */
	private String appName;

	/**
	 * 负责人ID
	 */
	private Long sptId;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Long getSptId() {
		return sptId;
	}

	public void setSptId(Long sptId) {
		this.sptId = sptId;
	}

	@Override
	public String toString() {
		return "AppLists [appName=" + appName + ", sptId=" + sptId + "]";
	}

}
