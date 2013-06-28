package com.smrs.logtrace.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jof.framework.util.Pager;
import com.smrs.logtrace.enums.LogTraceTypeEnum;
import com.smrs.logtrace.model.LogTraceModel;
import com.smrs.logtrace.model.LogTraceSearchModel;



public class LogTraceAction extends BaseLogTraceAction{
	private static final long serialVersionUID = 1L;
	protected LogTraceModel logTrace = new LogTraceModel();
	protected Character  logTraceType ;	
	protected Pager<LogTraceModel> pager = new Pager<LogTraceModel>(); 
	protected String titleName="日志";
	protected Date startDate;
	protected Date endDate;
	
	public String searchLogTrace(){

		
		LogTraceSearchModel searchModel = new LogTraceSearchModel();
		if(logTraceType==null){
			searchModel.setType(null);
		}else{
			if(LogTraceTypeEnum.ALL.getId().equals(logTraceType)){
				searchModel.setType(null);
			}else{
				searchModel.setType(logTraceType);
			}
		}
		searchModel.setStartDate(startDate);
		searchModel.setEndDate(endDate);
		pager=logTraceService.getByNameTypeLikePager(searchModel, pager);
		return SUCCESS;
	}
	
	String deleteLogTrace(){
		logTraceService.deleteModel(logTrace);
		return this.SEARCH;		
	}
	
	public Date getStartDate() {
		return startDate;
	}

	

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}
    
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Pager<LogTraceModel> getPager() {
		return pager;
	}


	public void setPager(Pager<LogTraceModel> pager) {
		this.pager = pager;
	}

	public String getTitleName() {
		return titleName;
	}


	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public LogTraceModel getCustomer() {
		return logTrace;
	}


	public void setCustomer(LogTraceModel logTrace) {
		this.logTrace = logTrace;
	}
	
	public Character getLogTraceType() {
		return logTraceType;
	}


	public void setLogTraceType(Character logTraceType) {
		this.logTraceType = logTraceType;
	}
}
