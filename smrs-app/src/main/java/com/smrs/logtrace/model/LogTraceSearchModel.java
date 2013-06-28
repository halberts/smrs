package com.smrs.logtrace.model;

import java.util.Date;



public class LogTraceSearchModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	


	private String traceAction;
	private String description;
	private Character type;
	private Date startDate = new Date();
	private Date endDate= new Date();
	
	
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


	public String getTraceAction() {
		return traceAction;
	}


	public void setTraceAction(String traceAction) {
		this.traceAction = traceAction;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Character getType() {
		return type;
	}


	public void setType(Character type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	
}
