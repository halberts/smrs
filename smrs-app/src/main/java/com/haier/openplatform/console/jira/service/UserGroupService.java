package com.haier.openplatform.console.jira.service;

import java.util.Map;

import com.haier.openplatform.console.jira.module.JiraUserGroupModel;
import com.haier.openplatform.util.Pager;

public interface UserGroupService {
	 Pager<JiraUserGroupModel>  findByKW(Map<String,Object> map);
	int saveSuppliers(String sql);
}
