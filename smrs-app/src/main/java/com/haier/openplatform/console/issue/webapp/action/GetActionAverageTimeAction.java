package com.haier.openplatform.console.issue.webapp.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.haier.openplatform.console.issue.domain.StatisticsAction;
import com.haier.openplatform.console.issue.domain.enu.StatisticsTimeTypeEnum;

/**
 * Action平均响应时间
 * @author 01167496
 *
 */
public class GetActionAverageTimeAction extends BaseIssueAction{
	private static final long serialVersionUID = -8679086622666007484L;
	private String time;
	private List<Object> applists = new ArrayList<Object>();
	private List<Object> averageTime = new ArrayList<Object>();
	private List<Object> averageTimeWithName =  new ArrayList<Object>();
	private List<StatisticsAction> list = new ArrayList<StatisticsAction>();
	public List<StatisticsAction> getList() {
		return list;
	}
	public void setList(List<StatisticsAction> list) {
		this.list = list;
	}
	public List<Object> getAverageTimeWithName() {
		return averageTimeWithName;
	}
	public void setAverageTimeWithName(List<Object> averageTimeWithName) {
		this.averageTimeWithName = averageTimeWithName;
	} 
	public List<Object> getAverageTime() {
		return averageTime;
	}
	public void setAverageTime(List<Object> averageTime) {
		this.averageTime = averageTime;
	} 
	public List<Object> getApplists() {
		return applists;
	}
	public void setApplists(List<Object> applists) {
		this.applists = applists;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String execute() throws Exception {
		
		if("week".equals(time)){
			list = getProfileService().queryActionAvgTime(null,StatisticsTimeTypeEnum.WEEK.getType(), Calendar.getInstance().get(Calendar.WEEK_OF_YEAR));
		}else {
			list = getProfileService().queryActionAvgTime(null,StatisticsTimeTypeEnum.MONTH.getType(), Calendar.getInstance().get(Calendar.MONTH) + 1);
		}
		
		for(StatisticsAction sa : list){
			List<Object> one = new ArrayList<Object>();
			applists.add(sa.getAppId());
			averageTime.add(sa.getAverageTime());
			one.add(sa.getAppName());
			one.add(sa.getAverageTime());
			averageTimeWithName.add(one);
		}
		return SUCCESS;
	} 
	
}
