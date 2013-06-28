package com.haier.openplatform.console.sonar.service;

import java.util.Map;

import com.haier.openplatform.console.sonar.domain.SonarProjectMetric;

public interface SonarService {

	/**
	 * 查询sonar上所有项目的违规问题
	 */
	public void checkProjectIssue();
	
	/**
	 * 查询Project和Metric的关联表
	 * @return
	 */
	public Map<String,Map<String,SonarProjectMetric>> queryProjectMetric();
	/**
	 * 新增关联表
	 * @param sonarProjectMetric
	 */
	public void saveSonarProjectMetric(SonarProjectMetric sonarProjectMetric);
	/**
	 * 根据id删除关联表数据
	 * @param id
	 * @return
	 */
	public int delSonarProjectMetric(Long id);
	
	/**
	 * 新增sonar的project到数据库
	 */
	public void addProject();
	/**
	 * 新增sonar的指标到数据库
	 */
	public void addMetric();
}
