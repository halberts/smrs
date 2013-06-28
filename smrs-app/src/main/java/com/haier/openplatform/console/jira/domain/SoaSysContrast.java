package com.haier.openplatform.console.jira.domain;

import com.haier.openplatform.domain.BaseDomain;

public class SoaSysContrast extends BaseDomain<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7589338093769786396L;

	/**
	 * 应用系统id
	 */
	private String appId;
	/**
	 * 应用系统在jira上的id
	 */
	private String jiraId;
	/**
	 * 应用系统在jira上的key
	 */
	private String jiraKey;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getJiraId() {
		return jiraId;
	}
	public void setJiraId(String jiraId) {
		this.jiraId = jiraId;
	}
	public String getJiraKey() {
		return jiraKey;
	}
	public void setJiraKey(String jiraKey) {
		this.jiraKey = jiraKey;
	}
}
