package com.haier.openplatform.console.issue.webapp.action;

import java.util.ArrayList;
import java.util.List;

import com.haier.openplatform.console.issue.module.AppHealthDetail;

/**
 * 服务器将抗状况统计图表
 * @author WangJian
 * 
 */
public class HealthyPlotAction extends BaseIssueAction {
	private static final long serialVersionUID = 6676940144096205302L;
	private String startTime;
	private String endTime;
	private Long urlId;
	private List<AppHealthDetail> healthDetailList = new ArrayList<AppHealthDetail>();
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Long getUrlId() {
		return urlId;
	}
	public void setUrlId(Long urlId) {
		this.urlId = urlId;
	}
	public List<AppHealthDetail> getHealthDetailList() {
		return healthDetailList;
	}
	public void setHealthDetailList(List<AppHealthDetail> healthDetailList) {
		this.healthDetailList = healthDetailList;
	}
	
	@Override
	public String execute() throws Exception {
		healthDetailList = getAppMonitorService().getAppHealthDetailByidAndDate(urlId, startTime, endTime);
		return SUCCESS;
	}
	
}
