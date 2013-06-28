package com.haier.openplatform.console.jira.dao;

import com.haier.openplatform.console.jira.domain.SoaSysContrast;
import com.haier.openplatform.dao.CommonDAO;

public interface SoaSysContrastDAO extends CommonDAO<SoaSysContrast, Long>{

	/**
	 * 根据应用id查询jira
	 * @param appId
	 * @return
	 */
	public SoaSysContrast queryJiraByAppId(Long appId);
	
	/**
	 * 根据应用名称查询jira
	 * @param appName
	 * @return
	 */
	public SoaSysContrast queryJiraByAppName(String appName);
}
