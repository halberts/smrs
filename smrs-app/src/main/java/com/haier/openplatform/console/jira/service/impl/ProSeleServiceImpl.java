package com.haier.openplatform.console.jira.service.impl;

import java.util.List;
import java.util.Map;

import com.haier.openplatform.console.jira.dao.JiraBaseDAO;
import com.haier.openplatform.console.jira.module.ProSeleModel;
import com.haier.openplatform.console.jira.service.ProSeleService;

public class ProSeleServiceImpl implements ProSeleService {
	private JiraBaseDAO jiraBaseDAO ;
	
	
	public JiraBaseDAO getJiraBaseDAO() {
		return jiraBaseDAO;
	}

	
	public void setJiraBaseDAO(JiraBaseDAO jiraBaseDAO) {
		this.jiraBaseDAO = jiraBaseDAO;
	}


	@Override
	public List<ProSeleModel> getProSele(Map map) {
		// TODO Auto-generated method stub
		List<ProSeleModel> proList = jiraBaseDAO.getProSele(map);
		return proList;
	}

}
