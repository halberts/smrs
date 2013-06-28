package com.haier.openplatform.console.issue.domain;

import com.haier.openplatform.domain.BaseDomain;

/**
 * @author shanjing 持久化类：异常列表
 */
public class ExLists extends BaseDomain<Long> {

	private static final long serialVersionUID = 7268878093444513353L;

	/**
	 * 异常名称
	 */
	private String exceptionName;

	/**
	 * 阀值下限
	 */
	private long alertMin;

	/**
	 * 阀值上限
	 */
	private long alertMax;

	/**
	 * 平均执行时间(暂时未使用)
	 */
	private long exceptionDailyAve;

	public long getExceptionDailyAve() {
		return exceptionDailyAve;
	}

	public void setExceptionDailyAve(long exceptionDailyAve) {
		this.exceptionDailyAve = exceptionDailyAve;
	}

	public String getExceptionName() {
		return exceptionName;
	}

	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
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

	@Override
	public String toString() {
		return "ExLists [exceptionName=" + exceptionName + ", alertMin="
				+ alertMin + ", alertMax=" + alertMax + ", exceptionDailyAve="
				+ exceptionDailyAve + "]";
	}

}
