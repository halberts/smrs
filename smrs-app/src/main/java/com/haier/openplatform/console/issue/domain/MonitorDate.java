package com.haier.openplatform.console.issue.domain;

import com.haier.openplatform.domain.BaseDomain;

/**
 * @author shanjing 持久化类：监控时间分组
 */
public class MonitorDate extends BaseDomain<Long> {

	private static final long serialVersionUID = -5968426394300562650L;

	/**
	 * 刻(15分钟)
	 */
	private String minuteQuarter;

	/**
	 * 小时
	 */
	private String hour;

	/**
	 * 天
	 */
	private String day;

	/**
	 * 周
	 */
	private String week;

	/**
	 * 月
	 */
	private String month;

	/**
	 * 季度
	 */
	private String monthQuarter;

	/**
	 * 年
	 */
	private String year;

	public String getMinuteQuarter() {
		return minuteQuarter;
	}

	public void setMinuteQuarter(String minuteQuarter) {
		this.minuteQuarter = minuteQuarter;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getMonthQuarter() {
		return monthQuarter;
	}

	public void setMonthQuarter(String monthQuarter) {
		this.monthQuarter = monthQuarter;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "MonitorDate [minuteQuarter=" + minuteQuarter + ", hour=" + hour
				+ ", day=" + day + ", week=" + week + ", month=" + month
				+ ", monthQuarter=" + monthQuarter + ", year=" + year + "]";
	}

}
