package com.haier.openplatform.console.issue.webapp.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.haier.openplatform.console.issue.domain.AppLists;
import com.haier.openplatform.console.issue.domain.ServiceTimeoutLv;
import com.haier.openplatform.console.issue.module.ServiceApi;

/**
 * level自动统计
 * @author WangJian
 *
 */
public class LevelStatisticsAction extends BaseIssueAction{
	private static final long serialVersionUID = -8922411791130887514L;
	private List<Object> levels = new ArrayList<Object>();
	private List<AppLists> appLists = new ArrayList<AppLists>();
	private List<Long> levelNum = new ArrayList<Long>();
	private List<ServiceTimeoutLv> returnLv= new ArrayList<ServiceTimeoutLv>();
	private String start;
	private String end;
	private String date;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public List<Object> getLevels() {
		return levels;
	}
	public void setLevels(List<Object> levels) {
		this.levels = levels;
	}
	public List<AppLists> getAppLists() {
		return appLists;
	}
	public void setAppLists(List<AppLists> appLists) {
		this.appLists = appLists;
	}
	public List<Long> getLevelNum() {
		return levelNum;
	}
	public void setLevelNum(List<Long> levelNum) {
		this.levelNum = levelNum;
	}
	public List<ServiceTimeoutLv> getReturnLv() {
		return returnLv;
	}
	public void setReturnLv(List<ServiceTimeoutLv> returnLv) {
		this.returnLv = returnLv;
	}
	@Override
	public String execute() throws Exception {
		//获取应用列表
		appLists = super.getBaseInfoService().getAppLists();
		//获取级别列表
		List<ServiceTimeoutLv> rs = getBaseInfoService().getServiceTimeoutLv();
		List<ServiceTimeoutLv> result = comparator(rs);
		returnLv = result;
		//循环计算app下service级别  
		for(int j=0;j<returnLv.size();j++){
			List<Object> levelOne = new ArrayList<Object>();
			for(int i=0;i<appLists.size();i++){
				List<Object> level = new ArrayList<Object>();
				List<ServiceApi> listLevel = super.getAppMonitorService().getServiceApiByAppid(appLists.get(i).getId(),date);
				for(int l=0;l<listLevel.size();l++){
	//				ServiceTimeoutLv serviceTimeoutLv = new ServiceTimeoutLv();
	//				serviceTimeoutLv.setOvertimeMsec(new Double(listLevel.get(l).getAverageTime()).longValue());
					this.getOnLv(listLevel.get(l).getAverageTime().longValue());
				}
				level.add(returnLv.get(j).getNum() == null ? 0 : returnLv.get(j).getNum()); 
				level.add(i);
				this.returnToZero();
				levelOne.add(level);
				}
			levels.add(levelOne);
		}
		return SUCCESS;
	}
	
	public void getOnLv(Long time){ 
		for(int i=0;i<returnLv.size();i++){
			 boolean greaterThan = returnLv.get(i).getOvertimeMsec()<time;
			 boolean lessThan;
			 if(i!=returnLv.size()-1){
				 lessThan = returnLv.get(i+1).getOvertimeMsec()>time;
			 }else{
				 lessThan = true;
			 }
			 if(greaterThan&&lessThan){
				 Long returNum = returnLv.get(i).getNum()==null ? 0 : returnLv.get(i).getNum();
				 returnLv.get(i).setNum(returNum+1);
			 }
		}
	}
	
	public void returnToZero(){
		for(int i=0;i<returnLv.size();i++){
			returnLv.get(i).setNum(0L);
		}
	}

	
	public List<ServiceTimeoutLv> comparator(List<ServiceTimeoutLv> list){
		List<ServiceTimeoutLv> rs = getBaseInfoService().getServiceTimeoutLv();
		Comparator<ServiceTimeoutLv> comparator = new Comparator<ServiceTimeoutLv>() {
			@Override
			public int compare(ServiceTimeoutLv o1, ServiceTimeoutLv o2) {
				return o1.getOvertimeMsec().compareTo(o2.getOvertimeMsec());
			}
		};
		//进行level级别的排序
		Collections.sort(rs,comparator);
		return rs;
	}
}
