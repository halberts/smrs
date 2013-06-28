package com.haier.openplatform.console.issue.domain;

import java.util.Date;

import com.haier.openplatform.domain.BaseDomain;

/**
 * 一次监控信息
 * @author WangXuzheng
 *
 */
public class ProfileInfo extends BaseDomain<Long> {
	private static final long serialVersionUID = 5488362347652201904L;
	private long appId;
	private String appName;
	/**
	 * hop框架的版本号
	 */
	private String hopVersion;
	/**
	 * 调用的开始时间
	 */
	private Date startTime;
	/**
	 * 调用的结束时间
	 */
	private Date endTime;
	/**
	 * 执行时间
	 */
	private long executionTime;
	/**
	 * 监控的类型，1为action监控；2为后台Job监控
	 */
	private int type;
	/**
	 * 保留字段，用来扩展添加自己的特性息
	 */
	private String infomationMap; 
	/**
	 * action执行情况id
	 */
	private ProfileBeanInfo profileBeanInfo;
	public long getAppId() {
		return appId;
	}
	public void setAppId(long appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getHopVersion() {
		return hopVersion;
	}
	public void setHopVersion(String hopVersion) {
		this.hopVersion = hopVersion;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public long getExecutionTime() {
		return executionTime;
	}
	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getInfomationMap() {
		return infomationMap;
	}
	public void setInfomationMap(String infomationMap) {
		this.infomationMap = infomationMap;
	}
	public ProfileBeanInfo getProfileBeanInfo() {
		return profileBeanInfo;
	}
	public void setProfileBeanInfo(ProfileBeanInfo profileBeanInfo) {
		this.profileBeanInfo = profileBeanInfo;
	}
}
