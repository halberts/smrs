package com.haier.openplatform.console.jira.service;

import java.util.List;
import java.util.Map;

import com.haier.openplatform.console.jira.module.AvetimeModel;
import com.haier.openplatform.console.jira.module.AvetimeSearchModel;
import com.haier.openplatform.console.jira.module.CakeRateModel;
import com.haier.openplatform.console.jira.module.CakeRateSearchModel;
import com.haier.openplatform.console.jira.module.HealthCountModel;
import com.haier.openplatform.console.jira.module.JiraUserGroupModel;
import com.haier.openplatform.console.jira.module.LineRateModel;
import com.haier.openplatform.console.jira.module.LineRateSearchModel;
import com.haier.openplatform.console.jira.module.QualitySearchModel;
import com.haier.openplatform.console.jira.module.SuggestModel;
import com.haier.openplatform.console.jira.module.SysExctModel;
import com.haier.openplatform.console.jira.module.SysExctSearchModel;
import com.haier.openplatform.console.jira.module.TimeWorkedModel;
import com.haier.openplatform.console.jira.module.TimeWorkedSearchModel;
import com.haier.openplatform.console.jira.module.TimelyRateModel;
import com.haier.openplatform.console.jira.module.TimelyRateSearchModel;
import com.haier.openplatform.console.jira.module.UserSatisfactionModel;
import com.haier.openplatform.console.jira.module.UserSatisfactionSearchModel;
import com.haier.openplatform.util.Pager;

public interface LfSqlService{
	/**
	 * 根据SQL返回分页结果集
	 * @param sql 查询sql
	 * @param orderBy 排序字段
	 * @param page	第几页
	 * @param pagecount	多少个
	 * @return
	 */
	public Map<String,Object> querySQL(String sql,String orderBy,long page,long pagecount);
	public Map<String,Object> querySQL(String sql,String orderBy,long page,long pagecount,String summap,String sumCol);
	public List querySQL(String sql);
	public int updateSQL(String sql);
	public Pager<TimelyRateModel> getTimelyRate(Map<String, Object> pram,TimelyRateSearchModel searchModel);
	public Pager<UserSatisfactionModel> userSatisfaction(Map<String, Object> pram,UserSatisfactionSearchModel userSatisfactionSearchModel);
	public List<Map<String,Object>> getColSum(Map<String,Object> param);
	public List<Map<String,Object>>  getManagerment(Map<String, Object> pram);
	public Map<String,Object> getQuality(Map<String, Object> pram,QualitySearchModel searchModel);
	public Pager<TimeWorkedModel> getTimeWorked(Map<String, Object> pram,TimeWorkedSearchModel searchModel);
	public JiraUserGroupModel getproadmin(Map<String, Object> pram);
	public double getgroupPrice(Map<String, Object> pram);
	public Pager<SuggestModel> getSuggest(Map<String, Object> pram);
	public List<Map<String,Object>> getUserNumber(Map<String,String> param);
	public int getKqDay(Map<String,String> pram);
	public int getQjDay(Map<String,String> pram);
	public Pager<CakeRateModel> getCakeRate(Map<String, Object> pram,CakeRateSearchModel searchModel);
	public Pager<LineRateModel> getLineRate(Map<String, Object> pram,LineRateSearchModel searchModel);
	public List<Map<String, Object>> getSysExct(Map<String, Object> pram);
	public List<Map<String, Object>> getExctLine(Map<String, Object> pram);
	public List<Map<String, Object>> getHealthExct(Map<String, Object> pram);
	public List<Map<String, Object>> getHealthLine(Map<String, Object> pram);
	public List<Map<String, Object>> getAveTimeCake(Map<String, Object> pram);
	public List<Map<String, Object>> getAveTimeLine(Map<String, Object> pram);
	public List<Map<String, Object>> getSysAllCount(Map<String, Object> pram);
	public List<AvetimeModel> getAveTimeTable(Map<String, Object> pram);
	public List<SysExctModel> getExctTable(Map<String, Object> pram);
	public List<HealthCountModel> getHealthTable(Map<String, Object> pram);
	
}
