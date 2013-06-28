package com.smrs.basicdata.webapp.action;

public class SystemConfigAction  extends BaseBasicDataAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String titleName="门店";

	public String getTitleName() {
		return titleName;
	}
	
	public String searchSystemConfig(){
		return this.SUCCESS;
	}
}
