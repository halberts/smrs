package com.smrs.logtrace.model;

import com.smrs.model.BaseModel;


public class LogTraceModel extends BaseModel<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String traceAction;
	private String description;
	private Character type;

	
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

	
	
	
	
	
	
}
