package com.haier.openplatform.console.issue.module;

/**
 * 响应级别（ServiceTimeoutLv的转换类，用于页面显示）
 * 
 * @author Vilight_Wu
 * 
 */
public class TimeoutLevel {
	private String lvName;
	private String otTime;

	public String getLvName() {
		return lvName;
	}

	public void setLvName(String lvName) {
		this.lvName = lvName;
	}

	public String getOtTime() {
		return otTime;
	}

	public void setOtTime(String otTime) {
		this.otTime = otTime;
	}

}
