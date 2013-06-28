package com.haier.openplatform.console.jira.dao;

import java.util.List;
import java.util.Map;

import com.haier.openplatform.console.jira.module.CakeRateModel;
import com.haier.openplatform.console.jira.module.CakeRateSearchModel;
import com.haier.openplatform.console.jira.module.JiraUserGroupModel;
import com.haier.openplatform.console.jira.module.LineRateModel;
import com.haier.openplatform.console.jira.module.LineRateSearchModel;
import com.haier.openplatform.console.jira.module.ManagermentSearchModel;
import com.haier.openplatform.console.jira.module.ProSeleModel;
import com.haier.openplatform.console.jira.module.QualitySearchModel;
import com.haier.openplatform.console.jira.module.TimeWorkedModel;
import com.haier.openplatform.console.jira.module.TimeWorkedSearchModel;
import com.haier.openplatform.console.jira.module.TimelyRateModel;
import com.haier.openplatform.console.jira.module.TimelyRateSearchModel;
import com.haier.openplatform.console.jira.module.UserSatisfactionModel;
import com.haier.openplatform.console.jira.module.UserSatisfactionSearchModel;
import com.haier.openplatform.util.Pager;

/**
 * @author BEN Mysql 查询DAO
 */
public interface JiraBaseDAO {
	public Pager<TimelyRateModel>   getTimelyRate(Map<String, Object> pram,TimelyRateSearchModel searchModel);
	public List<Map<String,Object>>  getManagerment(Map<String, Object> pram,ManagermentSearchModel searchModel);
	public List<ProSeleModel>  getProSele(Map map);
	public Pager<JiraUserGroupModel> getDisplayName(Map<String, Object> pram);
	public Pager<UserSatisfactionModel> userSatisfaction(Map<String, Object> pram,UserSatisfactionSearchModel userSatisfactionSearchModel);
	public List<Map<String,Object>> getColSum(String sql,Map<String,Object> param,String type,String sumcol);
	public List<Map<String,Object>> getQuality(Map<String, Object> pram,QualitySearchModel searchModel);
	public Pager<TimeWorkedModel> getTimeWorked(Map<String, Object> pram,TimeWorkedSearchModel searchModel);
	public int saveSuppliers(String sql);
	public JiraUserGroupModel getproadmin(Map<String, Object> pram);
	public double getgroupPrice(Map<String, Object> pram);
	public List<Map<String,Object>> getUserNumber(Map<String,String> param);
	public Pager<CakeRateModel> getCakeRate(Map<String, Object> pram,CakeRateSearchModel searchModel);
	public Pager<LineRateModel> getLineRate(Map<String, Object> pram,LineRateSearchModel searchModel);
}
