package com.haier.openplatform.console.jira.dao;

import java.util.List;
import java.util.Map;

import com.haier.openplatform.console.jira.module.AvetimeModel;
import com.haier.openplatform.console.jira.module.AvetimeSearchModel;
import com.haier.openplatform.console.jira.module.HealthCountModel;
import com.haier.openplatform.console.jira.module.SuggestModel;
import com.haier.openplatform.console.jira.module.SysExctModel;
import com.haier.openplatform.util.Pager;

/**
 * @author BEN Oracle 查询DAO
 */
public interface JiraOracleBaseDAO {
	public List<Map<String,Object>> getSuggest(Map<String, Object> pram);
	public List<Map<String,Object>> getSUrlStatus(Map<String,Object> pram);
	public int getErrorCount(Map<String,Object> pram);
	public int getBugCount(Map<String,Object> pram);
	public double getServiceBfb(Map<String,Object> pram);
	public int getAverageTime(Map<String,Object> pram);
	public int getErrCount(Map<String,Object> pram);
	public int getWarCount(Map<String,Object> pram);
	public double getFapi(Map<String,Object> pram);
	public int getKqDay(Map<String,String> pram);
	public int getQjDay(Map<String,String> pram);
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
