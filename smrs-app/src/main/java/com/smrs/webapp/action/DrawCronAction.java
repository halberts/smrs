package com.smrs.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.haier.openplatform.webapp.action.BaseAppAction;
import com.smrs.util.CronExpressionEx;
import com.smrs.util.DateCommonUtil;
/**
 * 计算cron开始时间以及未来执行时间
 * @author 01167496
 *
 */
public class DrawCronAction extends BaseAppAction{
 
	private static final long serialVersionUID = 7082884880971443186L;
	
	private String cronStr;
	private List<String> exeTime = new ArrayList<String>();
	 
	
	public List<String> getExeTime() {
		return exeTime;
	}
	public void setExeTime(List<String> exeTime) {
		this.exeTime = exeTime;
	}
	public String getCronStr() {
		return cronStr;
	} 
	public void setCronStr(String cronStr) {
		this.cronStr = cronStr;
	}
	
	@Override 
	public String execute() throws Exception {  
	     CronExpressionEx exp = new CronExpressionEx(cronStr); 
	     Date nowDate = new Date();
	     exeTime.clear();
	     for (int i = 1; i <= 8; i++) {
	     nowDate = exp.getNextValidTimeAfter(nowDate);
	     exeTime.add(i + ": " + DateCommonUtil.format("yyyy-MM-dd HH:mm:ss", nowDate) + "\n");
	       nowDate = new java.util.Date(nowDate.getTime() + 1000); 
		} 
	    return SUCCESS;
	}
	 
}
