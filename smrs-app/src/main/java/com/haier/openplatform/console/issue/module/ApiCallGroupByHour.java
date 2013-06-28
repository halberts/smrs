package com.haier.openplatform.console.issue.module;

/**
 * @author shanjing
 * 结果集：每小时API调用次数
 */
public class ApiCallGroupByHour {

	/**
	 * 小时
	 */
	private String hour;

	/**
	 * 调用次数
	 */
	private Long callNum;

	/**
	 * 执行时间
	 */
	private Long executeTime;

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public Long getCallNum() {
		return callNum;
	}

	public void setCallNum(Long callNum) {
		this.callNum = callNum;
	}

	public Long getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(Long executeTime) {
		this.executeTime = executeTime;
	}

	@Override
	public String toString() {
		return "ApiCallGroupByHour [hour=" + hour + ", callNum=" + callNum + ", executeTime=" + executeTime + "]";
	}

}
