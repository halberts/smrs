package com.haier.openplatform.console.issue.module;

import java.util.Date;

public class ActionAverageTime {
	private String actionId;
	private String actionName;
	private double averageTime;
	private Date minTime;
	private Date maxTime;
	private long appId;
	private String appName;
	private long num;
	private String actionMethod;
	public String getActionMethod() {
		return actionMethod;
	}
	public void setActionMethod(String actionMethod) {
		this.actionMethod = actionMethod;
	}
	public long getNum() {
		return num;
	}
	public void setNum(long num) {
		this.num = num;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public long getAppId() {
		return appId;
	}
	public void setAppId(long appId) {
		this.appId = appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}  
	public double getAverageTime() {
		return averageTime;
	}
	public void setAverageTime(double averageTime) {
		this.averageTime = averageTime;
	}
	public Date getMinTime() {
		return minTime;
	}
	public void setMinTime(Date minTime) {
		this.minTime = minTime;
	}
	public Date getMaxTime() {
		return maxTime;
	}
	public void setMaxTime(Date maxTime) {
		this.maxTime = maxTime;
	}
}
