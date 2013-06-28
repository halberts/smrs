package com.haier.openplatform.console.jira.module;

import com.haier.openplatform.util.SearchModel;

public class SysExctSearchModel extends SearchModel<SysExctModel>{
	private static final long serialVersionUID = -3157928768978363923L;
	private SysExctModel sysExctModel =new SysExctModel();
	public SysExctModel getSysExctModel() {
		return sysExctModel;
	}
	public void setSysExctModel(SysExctModel sysExctModel) {
		this.sysExctModel = sysExctModel;
	}
	
}
