package com.haier.openplatform.console.issue.domain;

import com.haier.openplatform.domain.BaseDomain;

public class StatisticsAction extends BaseDomain<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7573670676521669580L;
	
	/**
	 * 应用id
	 */
	private Long appId;
	/**
	 * 应用名称
	 */
	private String appName;
	/**
	 * 调用api总次数
	 */
	private Long callNum;
	/**
	 * 调用api平均时间
	 */
	private Double averageTime;
	/**
	 * 统计类型 1:周 2:月 3:年
	 */
	private Integer statisticsType;
	/**
	 * 统计的是哪一周或者哪一月或者哪一年
	 */
	private Integer statisticsNum;
	
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public Long getCallNum() {
		return callNum;
	}
	public void setCallNum(Long callNum) {
		this.callNum = callNum;
	}
	public Double getAverageTime() {
		return averageTime;
	}
	public void setAverageTime(Double averageTime) {
		this.averageTime = averageTime;
	}
	public Integer getStatisticsType() {
		return statisticsType;
	}
	public void setStatisticsType(Integer statisticsType) {
		this.statisticsType = statisticsType;
	}
	public Integer getStatisticsNum() {
		return statisticsNum;
	}
	public void setStatisticsNum(Integer statisticsNum) {
		this.statisticsNum = statisticsNum;
	}
}
