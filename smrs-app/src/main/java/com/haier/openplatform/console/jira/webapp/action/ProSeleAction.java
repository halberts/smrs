package com.haier.openplatform.console.jira.webapp.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haier.openplatform.console.jira.service.ProSeleService;

public class ProSeleAction extends BaseJiraAction {

	private static final long serialVersionUID = 2986568372732421489L;
	private ProSeleService proSeleService;
	private List proList ;
	private String val1;
	
	public String getVal1() {
		return val1;
	}

	public void setVal1(String val1) {
		this.val1 = val1;
	}


	public List getProList() {
		return proList;
	}

	public void setProList(List proList) {
		this.proList = proList;
	}

	public ProSeleService getProSeleService() {
		return proSeleService;
	}
	
	public void setProSeleService(ProSeleService proSeleService) {
		this.proSeleService = proSeleService;
	}

	public String execute() throws Exception {
	
		//System.out.println(val1);
		Map map = new HashMap<String,String>();
		map.put("keyWord", val1);
		proList = getProSeleService().getProSele(map); 
		return SUCCESS;
	}
}
