package com.haier.openplatform.console.jira.service.impl;

import java.util.Map;

import com.haier.openplatform.console.jira.dao.JiraBaseDAO;
import com.haier.openplatform.console.jira.module.JiraUserGroupModel;
import com.haier.openplatform.console.jira.service.UserGroupService;
import com.haier.openplatform.util.Pager;

public class UserGroupServiceImpl implements UserGroupService {
	
	private JiraBaseDAO jiraBaseDAO ;
	
	
	public JiraBaseDAO getJiraBaseDAO() {
		return jiraBaseDAO;
	}

	
	public void setJiraBaseDAO(JiraBaseDAO jiraBaseDAO) {
		this.jiraBaseDAO = jiraBaseDAO;
	}
	@Override
	public Pager<JiraUserGroupModel> findByKW(Map<String,Object> map) {
		Pager<JiraUserGroupModel> list1 = jiraBaseDAO.getDisplayName(map);
		return list1;
	}


	public int saveSuppliers(String sql) {
		return jiraBaseDAO.saveSuppliers(sql);
	}

}
