package com.haier.openplatform.console.jira.service.impl;

import com.haier.openplatform.console.jira.dao.JiraBaseDAO;
import com.haier.openplatform.console.jira.service.UserSatisfactionService;


public class UserSatisfactionServiceImpl implements UserSatisfactionService {
	private JiraBaseDAO jiraBaseDAO ;
	
	
	public JiraBaseDAO getJiraBaseDAO() {
		return jiraBaseDAO;
	}

	
	public void setJiraBaseDAO(JiraBaseDAO jiraBaseDAO) {
		this.jiraBaseDAO = jiraBaseDAO;
	}
	
}
