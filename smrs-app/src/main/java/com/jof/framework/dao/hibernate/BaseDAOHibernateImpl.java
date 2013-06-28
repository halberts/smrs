package com.jof.framework.dao.hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.Validate;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;

import com.jof.framework.dao.hibernate.support.ColumnToPropertyResultTransformer;
import com.jof.framework.dao.hibernate.support.DynamicQuery;
import com.jof.framework.dao.hibernate.support.QueryType;
import com.jof.framework.dao.hibernate.support.ReturnProperty;
import com.jof.framework.dao.hibernate.support.StatementTemplate;
import com.jof.framework.util.Pager;
import com.jof.framework.util.Pager.Sort;
import com.jof.framework.util.Reflections;



/**
 * 基于Hibernate session操作实现的DAO层
 * @author jonathan
 *
 */
public class BaseDAOHibernateImpl<T,ID extends Serializable> extends SimpleHibernate3SupportDao<T, ID>{
	public static final String DEFAULT_ALIAS = "x";
	/**
	 * 通过子类的泛型定义取得对象类型Class.
	 * eg.
	 * public class UserDao extends HibernateDao<User, Long>{
	 * }
	 */
	public BaseDAOHibernateImpl() {
		super();
	}

	//-- 分页查询函数 --//

	/**
	 * 分页获取全部对象.
	 */
	public <X> Pager<X> getAll(final Pager<?> pageRequest) {
		return findPage(pageRequest);
	}

	/**
	 * 按HQL分页查询.
	 * 
	 * @param pageRequest 分页参数.
	 * @param hql hql语句.
	 * @param values 数量可变的查询参数,按顺序绑定.
	 * @deprecated 不推荐使用，请使用sql语句插叙
	 * @return 分页查询结果, 附带结果列表及所有查询输入参数.
	 */
	@SuppressWarnings({ "rawtypes" })
	public <X> Pager<X> findPageByHQL(final Pager<?> pageRequest,RowMapper<X> rowMapper, String hql, final Object... values) {
		Validate.notNull(pageRequest, "pageRequest不能为空");
		Pager<X> result = Pager.cloneFromPager(pageRequest);
		if (pageRequest.isCountTotal()) {
			long totalCount = countHQLResult(hql, values);
			result.setTotalRecords(totalCount);
		}

		if (pageRequest.isOrderBySetted()) {
			hql = setOrderParameterToHQLOrSQL(hql, pageRequest);
		}
		Query query = createHQLQuery(hql, values);
		setPageParameterToQuery(query, pageRequest);
		List list = query.list();
		
		return buildPagerResultFromRowMapper(rowMapper, result, list);
	}
	
	/**
	 * 按HQL/sql分页查询.
	 * 
	 * @param pageRequest 分页参数.
	 * @param queryName 名称
	 * @param parameters sql/sql中的变量值.
	 * @deprecated 使用 {@link #findPageByNamedQuery(Pager, String, Map)}
	 * @return 分页查询结果, 附带结果列表及所有查询输入参数.
	 */
	public <X> Pager<X> findPageByNamedQuery(final Pager<?> pageRequest,RowMapper<X> rowMapper, String queryName, final Map<String, ?> parameters) {
		StatementTemplate statementTemplate = dynamicStatementBuilder.getTemplateCache().get(queryName);
		String statement = processTemplate(statementTemplate,parameters);
		if(statementTemplate.getQueryType() == QueryType.HQL){
			return this.findPageByHQL(pageRequest, rowMapper, statement);
		}else{
			return this.findPageBySQL(pageRequest, rowMapper, statement);
		}
	}
	
	/**
	 * 按sql分页查询-不支持hql查询.
	 * 
	 * @param pageRequest 分页参数.
	 * @param queryName xml中sql查询语句的名称
	 * @param parameters sql/sql中的变量值.
	 * @return 分页查询结果, 附带结果列表及所有查询输入参数.
	 */
	@SuppressWarnings("unchecked")
	public <X> Pager<X> findPageByNamedQuery(String queryName,final Pager<?> pageRequest, final Map<String, ?> parameters) {
		StatementTemplate statementTemplate = dynamicStatementBuilder.getTemplateCache().get(queryName);
		String sql = processTemplate(statementTemplate,parameters);
		if(statementTemplate.getQueryType() == QueryType.HQL){
			throw new RuntimeException("该方法不支持hql查询，只支持sql查询");
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
	
	/**
	 * 按SQL分页查询.
	 * 
	 * @param pageRequest 分页参数.
	 * @param rowMapper 查询映射器
	 * @param sql sql语句.
	 * @param values 数量可变的查询参数,按顺序绑定.
	 * @deprecated 使用 {@link #findPageByNamedQuery(Pager, String, Map)}
	 * @return 分页查询结果, 附带结果列表及所有查询输入参数.
	 */
	@SuppressWarnings({ "rawtypes" })
	public <X> Pager<X> findPageBySQL(final Pager<?> pageRequest,RowMapper<X> rowMapper, String sql, final Object... values) {
		Validate.notNull(pageRequest, "pageRequest不能为空");
		Pager<X> result = Pager.cloneFromPager(pageRequest);
		if (pageRequest.isCountTotal()) {
			long totalCount = countSQLResult(sql, values);
			result.setTotalRecords(totalCount);
		}

		if (pageRequest.isOrderBySetted()) {
			sql = setOrderParameterToHQLOrSQL(sql, pageRequest);
		}
		Query q = createSQLQuery(sql, values);
		setPageParameterToQuery(q, pageRequest);
		List list = q.list();
		return buildPagerResultFromRowMapper(rowMapper, result, list);
	}
	
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Deprecated
	private <X> Pager<X> buildPagerResultFromRowMapper(RowMapper<X> rowMapper, Pager<X> result, List list) {
		if(rowMapper == null){
			result.setRecords(list);
		}else{
			List<Object[]> rows = (List<Object[]>)list;
			result.setRecords(buildListResultFromRowMapper(rowMapper,rows));
		}
		return result;
	}
	
	/**
	 * 按HQL分页查询.
	 * 
	 * @param page 分页参数.
	 * @param hql hql语句.
	 * @param values 命名参数,按名称绑定.
	 * @deprecated 不推荐使用
	 * @return 分页查询结果, 附带结果列表及所有查询输入参数.
	 */
	@SuppressWarnings({ "rawtypes" })
	public <X> Pager<X> findPage(final Pager<?> pageRequest,RowMapper<X> rowMapper, String hql, final Map<String, ?> values) {
		Validate.notNull(pageRequest, "page不能为空");
		Pager<X> result = Pager.cloneFromPager(pageRequest);
		if (pageRequest.isCountTotal()) {
			long totalCount = countHQLResult(hql, values);
			result.setTotalRecords(totalCount);
		}

		if (pageRequest.isOrderBySetted()) {
			hql = setOrderParameterToHQLOrSQL(hql, pageRequest);
		}

		Query q = createHQLQuery(hql, values);
		setPageParameterToQuery(q, pageRequest);
		List list = q.list();
		
		return buildPagerResultFromRowMapper(rowMapper, result, list);
	}
	
	/**
	 * 按SQL分页查询.
	 * 
	 * @param page 分页参数.
	 * @param sql hql语句.
	 * @param values 命名参数,按名称绑定.
	 * @deprecated 使用 {@link #findPageByNamedQuery(String, Pager, Map)}
	 * @return 分页查询结果, 附带结果列表及所有查询输入参数.
	 */
	@SuppressWarnings({ "rawtypes"})
	public <X> Pager<X> findPageBySQL(final Pager<?> pageRequest,RowMapper<X> rowMapper, String sql, final Map<String, ?> values) {
		Validate.notNull(pageRequest, "page不能为空");
		Pager<X> result = Pager.cloneFromPager(pageRequest);
		if (pageRequest.isCountTotal()) {
			long totalCount = countSQLResult(sql, values);
			result.setTotalRecords(totalCount);
		}

		if (pageRequest.isOrderBySetted()) {
			sql = setOrderParameterToHQLOrSQL(sql, pageRequest);
		}

		Query q = createSQLQuery(sql, values);
		setPageParameterToQuery(q, pageRequest);
		List list = q.list();
		
		return buildPagerResultFromRowMapper(rowMapper, result, list);
	}

	/**
	 * 按Criteria分页查询.
	 * 
	 * @param page 分页参数.
	 * @param criterions 数量可变的Criterion.
	 * 
	 * @return 分页查询结果.附带结果列表及所有查询输入参数.
	 * @see #findPage(Pager, CriteriaCallBack, Criterion...)
	 */
	public <X> Pager<X> findPage(final Pager<?> pageRequest, final Criterion... criterions) {
		return this.findPage(pageRequest, null, criterions);
	}
	
	/**
	 * 按Criteria分页查询.
	 * 
	 * @param page 分页参数.
	 * @param callBack 对Criteria进行设置的回调接口
	 * @param criterions 数量可变的Criterion.
	 * 
	 * @return 分页查询结果.附带结果列表及所有查询输入参数.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <X> Pager<X> findPage(final Pager<?> pageRequest,CriteriaCallBack callBack, final Criterion... criterions) {
		Validate.notNull(pageRequest, "page不能为空");
		Pager<X> result = Pager.cloneFromPager(pageRequest);
		Criteria criteria = createCriteria(criterions);
		if(callBack != null){
			callBack.execute(criteria);
		}
		if (pageRequest.isCountTotal()) {
			long totalCount = countCriteriaResult(criteria);
			result.setTotalRecords(totalCount);
		}

		setPageRequestToCriteria(criteria, pageRequest);

		List list = criteria.list();
		result.setRecords(list);
		return result;
	}

	/**
	 * 在HQL或SQL的后面添加分页参数定义的orderBy, 辅助函数.
	 */
	protected String setOrderParameterToHQLOrSQL(final String hql, final Pager<?> pageRequest) {
		StringBuilder builder = new StringBuilder(hql);
		builder.append(" order by");

		for (Sort orderBy : pageRequest.getSort()) {
			builder.append(String.format(" %s.%s %s,", DEFAULT_ALIAS, orderBy.getProperty(), orderBy.getDir()));
		}
		builder.deleteCharAt(builder.length() - 1);

		return builder.toString();
	}



	/**
	 * 设置分页参数到Criteria对象,辅助函数.
	 */
	protected Criteria setPageRequestToCriteria(final Criteria criteria, final Pager<?> pageRequest) {
		Validate.isTrue(pageRequest.getPageSize() > 0, "Page Size must larger than zero");
		criteria.setFirstResult(pageRequest.getFirstResult().intValue());
		criteria.setMaxResults(pageRequest.getPageSize().intValue());

		if (pageRequest.isOrderBySetted()) {
			for (Sort sort : pageRequest.getSort()) {
				if (Sort.ASC.equals(sort.getDir())) {
					criteria.addOrder(Order.asc(sort.getProperty()));
				} else {
					criteria.addOrder(Order.desc(sort.getProperty()));
				}
			}
		}
		return criteria;
	}



	

	
	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHQLResult(final String hql, final Object... values) {
		/*String countHql = prepareCountHQLOrSQL(hql);*/
		String countHql = "select count(*) " + removeSelect(removeOrders(hql));
		try {
			Long count = findUniqueByHQL(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
	}
	
	/**
	 * 执行count查询获得本次SQL查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的sql语句,复杂的sql查询请另行编写count语句查询.
	 */
	protected long countSQLResult(final String sql, final Object... values) {
		String countSql = prepareCountHQLOrSQL(sql);
		try {
			BigDecimal count = findUniqueBySQL(countSql, values);
			return count.longValue();
		} catch (Exception e) {
			throw new RuntimeException("sql can't be auto count, hql is:" + countSql, e);
		}
	}

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHQLResult(final String hql, final Map<String, ?> values) {
		/*String countHql = prepareCountHQLOrSQL(hql);*/
		String countHql = "select count(*) " + removeSelect(removeOrders(hql));
		try {
			Long count = findUniqueByHQL(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
	}
	
	/**
	 * 执行count查询获得本次sql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的sql语句,复杂的sql查询请另行编写count语句查询.
	 */
	protected long countSQLResult(final String sql, final Map<String, ?> values) {
		String countSql = prepareCountHQLOrSQL(sql);
		try {
			BigInteger count = findUniqueBySQL(countSql, values);
			return count.longValue();
		} catch (Exception e) {
			throw new RuntimeException("sql can't be auto count, hql is:" + countSql, e);
		}
	}

	private String prepareCountHQLOrSQL(String orgHql) {
		/*String countHql = "select count (*) " + removeSelect(removeOrders(orgHql));*/
		String countHql = "select count(*) from ( " + orgHql+" ) d";
		return countHql;
	}




	/**
	 * 执行count查询获得本次Criteria查询所能获得的对象总数.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected long countCriteriaResult(final Criteria criteria) {
		CriteriaImpl criteriaImpl = (CriteriaImpl) criteria;

		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = criteriaImpl.getProjection();
		ResultTransformer transformer = criteriaImpl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = (List) Reflections.getFieldValue(criteriaImpl, "orderEntries");
		Reflections.setFieldValue(criteriaImpl, "orderEntries", new ArrayList());

		// 执行Count查询
		Long totalCountObject = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		long totalCount = (totalCountObject != null) ? totalCountObject : 0;

		// 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
		criteria.setProjection(projection);

		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			criteria.setResultTransformer(transformer);
		}
		Reflections.setFieldValue(criteriaImpl, "orderEntries", orderEntries);

		return totalCount;
	}
	

	
	
	public Pager<T> getByNameLikePager(String name,final Pager<?> pageRequest,final Map<String,?> mapNameValues){
		//String name = model.getRegion().getName();
		String hsql="";
		
		if(StringUtils.isNotEmpty(name)){
		   hsql = " from "+this.entityClass.getSimpleName()+ " a where name like '%"+name+"%'"; 
		}else{
			hsql = " from "+this.entityClass.getSimpleName() + " a ";
		}
		if(MapUtils.isNotEmpty(mapNameValues)){
			//hsql = hsql + " and a."

			Set<String> key = mapNameValues.keySet();
			String tempStr = "";
			for(String keyName:key){
				tempStr=tempStr+" and " + keyName+ " = :" +keyName;
			}
			if(StringUtils.contains(hsql, "where")){
				hsql=hsql+tempStr;	
			}else{
				hsql = hsql + " where "+ tempStr.substring(4);
			}
		} 
		Pager<T> result = Pager.cloneFromPager(pageRequest);
		Long totalCount=this.countHQLUniqueResule(hsql, mapNameValues);
		result.setTotalRecords(totalCount);		
		Query query = this.getSession().createQuery(hsql);
		query.setProperties(mapNameValues);
		this.setPageParameterToQuery(query, pageRequest);
		result.setRecords(query.list());
		return result;		
	}

	
}
