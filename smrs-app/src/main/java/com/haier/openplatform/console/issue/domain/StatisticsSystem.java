package com.haier.openplatform.console.issue.domain;

import com.haier.openplatform.domain.BaseDomain;

public class StatisticsSystem extends BaseDomain<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -637737943376493119L;

	private Long appId;
	
	private String statisticsContent;
	
	private String statisticsValue;
	
	private Integer statisticsType;
	
	private Integer statisticsTime;
	
	private Integer statisticsTimeType;
	
	private String remark;

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getStatisticsContent() {
		return statisticsContent;
	}

	public void setStatisticsContent(String statisticsContent) {
		this.statisticsContent = statisticsContent;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatisticsValue() {
		return statisticsValue;
	}

	public void setStatisticsValue(String statisticsValue) {
		this.statisticsValue = statisticsValue;
	}

	public Integer getStatisticsType() {
		return statisticsType;
	}

	public void setStatisticsType(Integer statisticsType) {
		this.statisticsType = statisticsType;
	}

	public Integer getStatisticsTime() {
		return statisticsTime;
	}

	public void setStatisticsTime(Integer statisticsTime) {
		this.statisticsTime = statisticsTime;
	}

	public Integer getStatisticsTimeType() {
		return statisticsTimeType;
	}

	public void setStatisticsTimeType(Integer statisticsTimeType) {
		this.statisticsTimeType = statisticsTimeType;
	}
}
