package com.jof.web.action.demo;

import com.jof.web.action.BaseAction;

public class TableIndexActoin extends BaseAction{
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 109L;

	public String execute(){
		logger.info("index success");
		return SUCCESS;
	 }
}
