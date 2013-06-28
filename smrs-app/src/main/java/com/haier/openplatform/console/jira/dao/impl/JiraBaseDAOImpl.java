package com.haier.openplatform.console.jira.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.haier.openplatform.console.jira.dao.JiraBaseDAO;
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
import com.haier.openplatform.dao.hibernate.BaseDAOHibernateImpl;
import com.haier.openplatform.dao.hibernate.Java2HibernateTypeMapping;
import com.haier.openplatform.dao.hibernate.JavaHibernateType;
import com.haier.openplatform.dao.hibernate.support.ColumnToPropertyResultTransformer;
import com.haier.openplatform.dao.hibernate.support.DynamicQuery;
import com.haier.openplatform.dao.hibernate.support.QueryType;
import com.haier.openplatform.dao.hibernate.support.ReturnProperty;
import com.haier.openplatform.dao.hibernate.support.StatementTemplate;
import com.haier.openplatform.util.Pager;

/**
 * @author ben
 */
public class JiraBaseDAOImpl extends BaseDAOHibernateImpl<Object, Long> implements JiraBaseDAO {

	@Override
	public Pager<TimelyRateModel> getTimelyRate(Map<String, Object> pram,TimelyRateSearchModel searchModel) {

//		return this.findPageByNamedQuery("jira.gettimelyrate", searchModel.getPager(), pram);
		List<TimelyRateModel> list = this.findByNamedQuery("jira.gettimelyrate",pram);
		TimelyRateSearchModel searchModeltmp= new TimelyRateSearchModel();
		searchModeltmp.getPager().setRecords(list);
		return searchModeltmp.getPager();
	}
	protected long countSQLResult(final String sql, final Map<String, ?> values) {
		String countSql = prepareCountHQLOrSQL(sql);
		try {
			BigDecimal count = new BigDecimal(findUniqueBySQL(countSql, values).toString());
			return count.longValue();
		} catch (Exception e) {
			throw new RuntimeException("sql can't be auto count, hql is:" + countSql, e);
		}
	}

	private String prepareCountHQLOrSQL(String orgHql) {
		/*String countHql = "select count (*) " + removeSelect(removeOrders(orgHql));*/
		String countHql = "select count(*) from ( " + orgHql+" ) tttt";
		return countHql;
	}
	public <X> Pager<X> findPageByNamedQuery(String queryName,final Pager<?> pageRequest, final Map<String, ?> parameters) {
		StatementTemplate statementTemplate = dynamicStatementBuilder.getTemplateCache().get(queryName);
		String sql = processTemplate(statementTemplate,parameters);
		if(statementTemplate.getQueryType() == QueryType.HQL){
			throw new RuntimeException("璇ユ柟娉曚笉鏀寔hql鏌ヨ锛屽彧鏀寔sql鏌ヨ");
		}else{
			Pager<X> result = Pager.cloneFromPager(pageRequest);
			if (pageRequest.isCountTotal()) {
				long totalCount = countSQLResult(sql, parameters);
				result.setTotalRecords(totalCount);
			}
			if (pageRequest.isOrderBySetted()) {
				sql = setOrderParameterToHQLOrSQL(sql, pageRequest);
			}
			SQLQuery q = createSQLQuery(sql, parameters);
			setPageParameterToQuery(q, pageRequest);
			DynamicQuery dynamicQuery = statementTemplate.getDynamicQuery();
			q.setCacheable(dynamicQuery.isCacheable());
			if(dynamicQuery.isCacheable()){
				q.setCacheRegion(dynamicQuery.getCacheRegion());
			}
			q.setResultTransformer(new ColumnToPropertyResultTransformer(dynamicQuery.getReturnMapping()));
			Map<String, JavaHibernateType> typeMap = Java2HibernateTypeMapping.getMapping();
			for(ReturnProperty returnProperty : dynamicQuery.getReturnMapping().getReturnProperties()){
				q.addScalar(returnProperty.getColumn().toLowerCase(),typeMap.get(returnProperty.getType()).getHibernateType());
			}
			result.setRecords(q.list());
			return result;
		}
	}

	@Override
	public List<ProSeleModel>  getProSele(Map map) {
		List<ProSeleModel> list = this.findByNamedQuery("jira.getProject",map);
		return list;
	}
	@Override
	public List<Map<String,Object>> getColSum(String sql,Map<String,Object> param,String type,String sumcol) {
		// TODO Auto-generated method stub
		String query="";
		if(type.equals("XML")){
			StatementTemplate statementTemplate = dynamicStatementBuilder.getTemplateCache().get(sql);
			query = processTemplate(statementTemplate,param);
			String querysql = "select "+sumcol+" from ("+query+") tttt";
			Query q = createSQLQuery(querysql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			return q.list();
		}else{
			Query q = createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			return q.list();
		} 
	}

	@Override
	public Pager<JiraUserGroupModel> getDisplayName(Map<String, Object> pram) {
		List<JiraUserGroupModel> list = this.findByNamedQuery("jira.getDisplayName",pram);
		Pager<JiraUserGroupModel> pager = new Pager<JiraUserGroupModel>();
		pager.setRecords(list);
		return pager;
	}

	@Override
	public int saveSuppliers(String sql) {
		Query q = this.getSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.executeUpdate();
		
	}

	@Override
	public Pager<UserSatisfactionModel> userSatisfaction(Map<String, Object> pram,UserSatisfactionSearchModel userSatisfactionSearchModel) {
//		return this.findPageByNamedQuery("jira.getUserSatisfaction",userSatisfactionSearchModel.getPager(),pram);
		List<UserSatisfactionModel> list = this.findByNamedQuery("jira.getUserSatisfaction",pram);
		userSatisfactionSearchModel.getPager().setRecords(list);
		return userSatisfactionSearchModel.getPager();
	}

	@Override
	public List<Map<String,Object>>getManagerment(Map<String, Object> pram,ManagermentSearchModel searchModel) {
		// TODO Auto-generated method stub
		StatementTemplate statementTemplate = dynamicStatementBuilder.getTemplateCache().get("jira.managerment");
		String query = processTemplate(statementTemplate,pram);
		Query q = createSQLQuery(query).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	@Override
	public List<Map<String,Object>> getQuality(Map<String, Object> pram,
			QualitySearchModel searchModel) {
		// TODO Auto-generated method stub
		StatementTemplate statementTemplate = dynamicStatementBuilder.getTemplateCache().get("jira.quality");
		String query = processTemplate(statementTemplate,pram);
		Query q = createSQLQuery(query).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	@Override
	public Pager<TimeWorkedModel> getTimeWorked(Map<String, Object> pram,
			TimeWorkedSearchModel searchModel) {
		// TODO Auto-generated method stub
		List<TimeWorkedModel> list = this.findByNamedQuery("jira.timeworked",pram);
		TimeWorkedSearchModel searchModeltmp= new TimeWorkedSearchModel();
		searchModeltmp.getPager().setRecords(list);
		return searchModeltmp.getPager();
	}
	@Override
	public JiraUserGroupModel getproadmin(Map<String, Object> pram) {
		// TODO Auto-generated method stub
		StatementTemplate statementTemplate = dynamicStatementBuilder.getTemplateCache().get("jira.getproadmin");
		String query = processTemplate(statementTemplate,pram);
		Query q = createSQLQuery(query).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		//Map<String, Object> mapreturn = new HashMap<String, Object>();
		if(q.list().size()>0){
			Map<String, Object> mapreturn=(Map<String, Object>) q.list().get(0);
			JiraUserGroupModel jiraUserGroupModel = new JiraUserGroupModel();
			jiraUserGroupModel.setAdminprice(mapreturn.get("price").toString());
			jiraUserGroupModel.setProjectkey((String)mapreturn.get("project_key"));
			jiraUserGroupModel.setProjectname((String)mapreturn.get("project_name"));
			jiraUserGroupModel.setDisplayname(mapreturn.get("display_name").toString());
			return jiraUserGroupModel;
		}else{
			return null;
		}
	}
	@Override
	public double getgroupPrice(Map<String, Object> pram) {
		// TODO Auto-generated method stub
		StatementTemplate statementTemplate = dynamicStatementBuilder.getTemplateCache().get("jira.getGroupPrice");
		String query = processTemplate(statementTemplate,pram);
		Query q = createSQLQuery(query).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		//Map<String, Object> mapreturn = new HashMap<String, Object>();
		if(q.list().size()>0){
			Map<String, Object> mapreturn=(Map<String, Object>) q.list().get(0);
			return Double.parseDouble(mapreturn.get("GROUPPRICE").toString());
		}else{
			return 0;
		}
	}
	@Override
	public List<Map<String, Object>> getUserNumber(Map<String, String> param) {
		StatementTemplate statementTemplate = dynamicStatementBuilder.getTemplateCache().get("jira.usernumber");
		String query = processTemplate(statementTemplate,param);
		Query q = createSQLQuery(query).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	public Pager<CakeRateModel> getCakeRate(Map<String, Object> pram,CakeRateSearchModel searchModel) {
//		return this.findPageByNamedQuery("jira.gettimelyrate", searchModel.getPager(), pram);
		List<CakeRateModel> list = this.findByNamedQuery("jira.getCakeRate",pram);
		CakeRateSearchModel searchModeltmp= new CakeRateSearchModel();
		searchModeltmp.getPager().setRecords(list);
		return searchModeltmp.getPager();
	}
	@Override
	public Pager<LineRateModel> getLineRate(Map<String, Object> pram,LineRateSearchModel searchModel) {
		// TODO Auto-generated method stub
		List<LineRateModel> list = this.findByNamedQuery("jira.getlinerate",pram);
		LineRateSearchModel searchModeltmp= new LineRateSearchModel();
		searchModeltmp.getPager().setRecords(list);
		return searchModeltmp.getPager();
	}


}
