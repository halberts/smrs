package com.haier.openplatform.console.sonar.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.openplatform.console.sonar.domain.SonarMetric;
import com.haier.openplatform.console.sonar.domain.SonarProject;
import com.haier.openplatform.console.sonar.domain.SonarProjectMetric;
import com.haier.openplatform.dao.CommonDAO;

public interface SonarDAO extends CommonDAO<Object, Long>{

	/**
	 * 查询Project和Metric的关联表
	 * @return
	 */
	public List<SonarProjectMetric> queryProjectMetric();
	/**
	 * 新增project
	 * @param sonarProject
	 */
	public void saveSonarProject(SonarProject sonarProject);
	/**
	 * 根据项目key查询
	 * @param key
	 * @return
	 */
	public SonarProject queryProjectByKey(String key);
	/**
	 * 新增metric
	 * @param sonarMetric
	 */
	public void saveSonarMetric(SonarMetric sonarMetric);
	/**
	 * 根据指标key查询
	 * @param key
	 * @return
	 */
	public SonarMetric queryMetricByKey(String key);
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
	public int delSonarProjectMetric(@Param("id")Long id);
}
