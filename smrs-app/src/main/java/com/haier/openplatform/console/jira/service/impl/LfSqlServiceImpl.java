package com.haier.openplatform.console.jira.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import com.haier.openplatform.console.jira.dao.JiraBaseDAO;
import com.haier.openplatform.console.jira.dao.JiraOracleBaseDAO;
import com.haier.openplatform.console.jira.dao.SoaOracleBaseDAO;
import com.haier.openplatform.console.jira.module.AvetimeModel;
import com.haier.openplatform.console.jira.module.CakeRateModel;
import com.haier.openplatform.console.jira.module.CakeRateSearchModel;
import com.haier.openplatform.console.jira.module.HealthCountModel;
import com.haier.openplatform.console.jira.module.JiraUserGroupModel;
import com.haier.openplatform.console.jira.module.LineRateModel;
import com.haier.openplatform.console.jira.module.LineRateSearchModel;
import com.haier.openplatform.console.jira.module.ManagermentSearchModel;
import com.haier.openplatform.console.jira.module.QualitySearchModel;
import com.haier.openplatform.console.jira.module.SuggestModel;
import com.haier.openplatform.console.jira.module.SysExctModel;
import com.haier.openplatform.console.jira.module.TimeWorkedModel;
import com.haier.openplatform.console.jira.module.TimeWorkedSearchModel;
import com.haier.openplatform.console.jira.module.TimelyRateModel;
import com.haier.openplatform.console.jira.module.TimelyRateSearchModel;
import com.haier.openplatform.console.jira.module.UserSatisfactionModel;
import com.haier.openplatform.console.jira.module.UserSatisfactionSearchModel;
import com.haier.openplatform.console.jira.service.LfSqlService;
import com.haier.openplatform.dao.hibernate.BaseDAOHibernateImpl;
import com.haier.openplatform.util.Pager;

public class LfSqlServiceImpl extends
		BaseDAOHibernateImpl<Object, Long> implements LfSqlService,
		java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9030889311524410978L;
	private JiraBaseDAO jiraBaseDAO;
	private JiraOracleBaseDAO jiraOracleBaseDAO;
	private SoaOracleBaseDAO soaOracleBaseDAO;
	
	
	public JiraOracleBaseDAO getJiraOracleBaseDAO() {
		return jiraOracleBaseDAO;
	}
	public void setJiraOracleBaseDAO(JiraOracleBaseDAO jiraOracleBaseDAO) {
		this.jiraOracleBaseDAO = jiraOracleBaseDAO;
	}
	public JiraBaseDAO getJiraBaseDAO() {
		return jiraBaseDAO;
	}
	public void setJiraBaseDAO(JiraBaseDAO jiraBaseDAO) {
		this.jiraBaseDAO = jiraBaseDAO;
	}
	
public SoaOracleBaseDAO getSoaOracleBaseDAO() {
		return soaOracleBaseDAO;
	}
	public void setSoaOracleBaseDAO(SoaOracleBaseDAO soaOracleBaseDAO) {
		this.soaOracleBaseDAO = soaOracleBaseDAO;
	}
/**
 * 按照SQL查询数据
 */
	public Map<String, Object> querySQL(String sql, String orderBy, long page,
			long pagecount) {
		String pagesql = "Select ttt.* from(Select tt.*,row_number() over(order by tt."
				+ orderBy
				+ ") as RowNo from("
				+ sql
				+ ") tt)ttt where RowNo between "
				+ ((page - 1) * pagecount + 1) + " and " + page * pagecount;
		Query q = this.getSession().createSQLQuery(pagesql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = q.list();
		String countsql = " select count(*) as count  from (" + sql + ")";
		Query q2 = this.getSession().createSQLQuery(countsql);
		int result = Integer.parseInt(q2.list().get(0).toString());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", list);
		map.put("count", result);
		return map;
	}
	/**
	 * 根据sql查询数据带有合计
	 */
	public Map<String, Object> querySQL(String sql, String orderBy, long page,
			long pagecount,String summap,String sumCol) {
		String pagesql = "Select ttt.* from(Select tt.*,row_number() over(order by tt."
				+ orderBy
				+ ") as RowNo from("
				+ sql
				+ ") tt)ttt where RowNo between "
				+ ((page - 1) * pagecount + 1) + " and " + page * pagecount;
		Query q = this.getSession().createSQLQuery(pagesql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = q.list();
		String countsql = " select count(*) as count  from (" + sql + ")";
		Query q2 = this.getSession().createSQLQuery(countsql);
		int result = Integer.parseInt(q2.list().get(0).toString());
		String[] sumTemp = summap.split(",");
		String[] colTemp = sumCol.split(",");
		String sumSql="";
		//String suminfo="";
		StringBuffer suminfobuffer=new StringBuffer("");
		for(int i =0;i<sumTemp.length;i++){
			sumSql = "select nvl("+sumTemp[i]+",0) from ("+sql+") group by "+orderBy;
			 q2 = this.getSession().createSQLQuery(sumSql);
			 if(q2.list().size()>0&&!("0").equals(q2.list().get(0).toString())){
				 if(!(new StringBuffer("")).equals(suminfobuffer)){
					 suminfobuffer.append(",");
					}
				 //suminfo+="\""+colTemp[i]+"\":\""+q2.list().get(0).toString()+"\"";
				 suminfobuffer.append("\"");
				 suminfobuffer.append(colTemp[i]);
				 suminfobuffer.append("\":\"");
				 suminfobuffer.append(q2.list().get(0).toString());
				 suminfobuffer.append("\"");
			 }
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", list);
		map.put("count", result);
		String suminfo=suminfobuffer.toString();
		if(!"".equals(suminfo)){
			map.put("suminfo","{"+suminfo+"}");
		}
		return map;
	}
	public List querySQL(String sql) {
		Query q = this.getSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = q.list();
		return list;
	}

	public int updateSQL(String sql) {
		Query q = this.getSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.executeUpdate();
	}
	@Override
	public Pager<TimelyRateModel> getTimelyRate(Map<String, Object> pram,TimelyRateSearchModel searchModel) {
		// TODO Auto-generated method stub
		return jiraBaseDAO.getTimelyRate(pram,searchModel);
	}
	@Override
	public List<Map<String,Object>> getColSum(Map<String,Object> param){
		// TODO Auto-generated method stub
		String sql = "jira.gettimelyrate";
		String type = "XML";
		String sumcol = "SUM(JJCOUNT)  SUMJJCOUNT , SUM(TIMEWORKED)  SUMTIMEWORKED ,SUM(YQCOUNT)  SUMYQCOUNT,SUM(YQTIMEWORKED) SUMYQTIMEWORKED ,SUM(TQDAY) SUMTQDAY ,SUM(TQL)  SUMTQL";
		return jiraBaseDAO.getColSum(sql, param, type, sumcol);
	}
	@Override
	public List<Map<String,Object>> getManagerment(Map<String, Object> pram ) {
		// TODO Auto-generated method stub
		ManagermentSearchModel searchModel = new ManagermentSearchModel();
		return jiraBaseDAO.getManagerment(pram,searchModel);
	}
	public Pager<UserSatisfactionModel> userSatisfaction(Map<String, Object> pram,UserSatisfactionSearchModel userSatisfactionSearchModel) {
		return jiraBaseDAO.userSatisfaction(pram, userSatisfactionSearchModel);
	}
	@Override
	public Map<String,Object> getQuality(Map<String, Object> pram,
			QualitySearchModel searchModel) {
		// TODO Auto-generated method stub
		//List<Map<String,Object>> result = jiraBaseDAO.getQuality(pram,searchModel);
		/**
		 * BUG计算逻辑：
		 * 相同系统异常连续出现3天，记入Bug 
		 * 服务器健康检查，服务器异常比例超过10%，每超过1个百分点记入1个Bug
		 */
		int bugCount = jiraOracleBaseDAO.getBugCount(pram);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("BUGCOUNT", bugCount);
		return result;
	}
	@Override
	public Pager<TimeWorkedModel> getTimeWorked(Map<String, Object> pram,
			TimeWorkedSearchModel searchModel) {
		// TODO Auto-generated method stub
		return jiraBaseDAO.getTimeWorked(pram,searchModel);
	}
	@Override
	public JiraUserGroupModel getproadmin(Map<String, Object> pram) {
		// TODO Auto-generated method stub
		 return jiraBaseDAO.getproadmin(pram);
	}
	@Override
	public double getgroupPrice(Map<String, Object> pram) {
		// TODO Auto-generated method stub
		 return jiraBaseDAO.getgroupPrice(pram);
	}
	@Override
	public Pager<SuggestModel> getSuggest(Map<String, Object> pram) {
		// TODO Auto-generated method stub
		List<SuggestModel> lisugg = new ArrayList<SuggestModel>();
		Pager<SuggestModel> pagerSuggest = new Pager<SuggestModel>();
		//List<Map<String, Object>> limap = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> limap = jiraOracleBaseDAO.getSuggest(pram);
		for(int i =0;i<limap.size();i++){
			Map<String, Object> tempMap = limap.get(i);
			SuggestModel tempyc = new SuggestModel();
			String suggestStr="'"+(String)tempMap.get("SERVICE_API_NM")+"'异常连续'"+tempMap.get("YDAY")+"'天出现无人处理,请及时推进问题解决关差";
			if(i>5){
				suggestStr = "系统严重异常，异常数量"+limap.size()+"个";
			}
			tempyc.setSuggestStr(suggestStr);
			lisugg.add(tempyc);
			if(i>5){
				break;
			}
		}
		limap = jiraOracleBaseDAO.getSUrlStatus(pram);
		for(int i =0;i<limap.size();i++){
			SuggestModel dj = new SuggestModel();
			Map<String, Object> tempMap = limap.get(i);
			dj.setSuggestStr(tempMap.get("URL_ID").toString()+"节点白天宕机超过一小时未处理，请及时推进问题解决关差");
			lisugg.add(dj);
		}
		int errorCount = jiraOracleBaseDAO.getErrorCount(pram);
		if(errorCount>0){
			SuggestModel djcount = new SuggestModel();
			djcount.setSuggestStr("本段时间宕机次数为"+errorCount+"次，请联系实施团队查明原因规避风险");
			lisugg.add(djcount);
		}
		double serviceBfb = jiraOracleBaseDAO.getServiceBfb(pram);
		if(serviceBfb>30){
			SuggestModel highleve = new SuggestModel();
			highleve.setSuggestStr("level3以上级别超时所占比例较高 量化总体系统响应时间较慢,请及时修复超时前十的service，提升用户体验");
			lisugg.add(highleve);
		}
		int averageTime = jiraOracleBaseDAO.getAverageTime(pram);
		if(averageTime == 1){
			SuggestModel actionTop = new SuggestModel();
			actionTop.setSuggestStr("Action平均响应时间大约2秒请及时排查优化响应速度较后十的Action，提升用户体验");
			lisugg.add(actionTop);
		}
		int errCount = jiraOracleBaseDAO.getErrCount(pram);
		if(errCount>0){
			SuggestModel errtemp = new SuggestModel();
			errtemp.setSuggestStr("本段时间异常数量为"+errCount+"个,系统可能存在一定的不稳定性，请联系实施团队查明原因规避风险");
			lisugg.add(errtemp);
		}
		int warCount = jiraOracleBaseDAO.getWarCount(pram);
		if(warCount>0){
			SuggestModel apitemp = new SuggestModel();
			apitemp.setSuggestStr("本段时间API超时数量为"+warCount+" ，请联系实施团队查明原因规避风险");
			lisugg.add(apitemp);
		}
		double fapi = jiraOracleBaseDAO.getFapi(pram)*100;
		if(fapi>0){
			SuggestModel apinum = new SuggestModel();
			apinum.setSuggestStr("本段时间API调用数量占数量的百分之"+fapi+"可能存在废弃的API，请确认API业务使用情况");
			lisugg.add(apinum);
		}
		pagerSuggest.setRecords(lisugg);
		return pagerSuggest;
	}
	@Override
	public List<Map<String, Object>> getUserNumber(Map<String, String> param) {
		// TODO Auto-generated method stub
		 return jiraBaseDAO.getUserNumber(param);
	}
	@Override
	public int getKqDay(Map<String, String> pram) {
		// TODO Auto-generated method stub
		return soaOracleBaseDAO.getKqDay(pram);
	}
	@Override
	public int getQjDay(Map<String, String> pram) {
		// TODO Auto-generated method stub
		return soaOracleBaseDAO.getQjDay(pram);
	}
	@Override
	public Pager<CakeRateModel> getCakeRate(Map<String, Object> pram,CakeRateSearchModel searchModel) {
		// TODO Auto-generated method stub
		return jiraBaseDAO.getCakeRate(pram, searchModel);
	}
	@Override
	public Pager<LineRateModel> getLineRate(Map<String, Object> pram,LineRateSearchModel searchModel) {
		// TODO Auto-generated method stub
		return jiraBaseDAO.getLineRate(pram,searchModel);
	}
	
	@Override
	public List<Map<String, Object>> getSysExct(Map<String, Object> pram) {
		return jiraOracleBaseDAO.getSysExct(pram);
	}
	
	@Override
	public List<Map<String, Object>> getExctLine(Map<String, Object> pram) {
		return jiraOracleBaseDAO.getExctLine(pram);
	}
	
	@Override
	public List<Map<String, Object>> getHealthExct(Map<String, Object> pram) {
		return jiraOracleBaseDAO.getHealthExct(pram);
	}
	
	@Override
	public List<Map<String, Object>> getHealthLine(Map<String, Object> pram) {
		return jiraOracleBaseDAO.getHealthLine(pram);
	}
	@Override
	public List<Map<String, Object>> getAveTimeCake(Map<String, Object> pram) {
		return jiraOracleBaseDAO.getAveTimeCake(pram);
	}
	@Override
	public List<Map<String, Object>> getAveTimeLine(Map<String, Object> pram) {
		return jiraOracleBaseDAO.getAveTimeLine(pram);
	}
	
	@Override
	public List<Map<String, Object>> getSysAllCount(Map<String, Object> pram) {
		return jiraOracleBaseDAO.getSysAllCount(pram);
	}
	@Override
	public List<AvetimeModel> getAveTimeTable(Map<String, Object> pram) {
		// TODO Auto-generated method stub
		return jiraOracleBaseDAO.getAveTimeTable(pram);
	}
	
	@Override
	public List<SysExctModel> getExctTable(Map<String, Object> pram) {
		// TODO Auto-generated method stub
		return jiraOracleBaseDAO.getExctTable(pram);
	}
	
	@Override
	public List<HealthCountModel> getHealthTable(Map<String, Object> pram) {
		// TODO Auto-generated method stub
		return jiraOracleBaseDAO.getHealthTable(pram);
	}
}
