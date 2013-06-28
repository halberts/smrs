package com.jof.framework.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jof.framework.util.PageInfo;
import com.jof.framework.util.PageSort;
import com.jof.framework.util.QueryType;

public class Hibernate4BaseDao<T> {
	private  Logger logger = LoggerFactory.getLogger(getClass());

	
	@Autowired
	protected SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * @return current session
	 */
	public Session currentSession() {
		return sessionFactory.getCurrentSession();
	}
	

	
	public void save(Object object) {
		Validate.notNull(object, "entity can not be null");
		currentSession().save(object);
		logger.debug("#####save entity: {}", object);
	}

	public void update(Object object) {
		Validate.notNull(object, "entity can not be null");
		currentSession().update(object);
		logger.debug("#####update entity: {}", object);
	}

	public void delete(Object object) {
		Validate.notNull(object, "entity can not be null");
		currentSession().delete(object);
		logger.debug("#####delete entity: {}", object);
	}
	
	public List<T> getAll(Class<T> c){
		String sql = "   from " + c.getName();	
		Query query = currentSession().createQuery(sql);		
		List<T> list = query.list();
		return list; 
	}
	
	/**
	 * find objects by hql, and convert result to specified object list (Map
	 * param)
	 * 
	 * @param hql
	 * @param values
	 *            binded by param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> find(final String hql) {
		return createQuery(hql, null).list();
	}
	
	/**
	 * find objects by hql, and convert result to specified object list (Map
	 * param)
	 * 
	 * @param hql
	 * @param values
	 *            binded by param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> find(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).list();
	}

	/**
	 * find unique result by hql, convert result to specified object type (Map
	 * param)
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <X> X findUniqueByHQL(final String hql, final Map<String, ?> values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	
	/**
	 * Create Query object through hql/param (Map params)
	 * 
	 * @param hql	 
	 * @return
	 */
	public Query createQuery(final String hql) {
		Query query = currentSession().createQuery(hql);
		return query;
	}

	
	/**
	 * Create Query object through hql/param (Map params)
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	public Query createQuery(final String hql, final Map<String, ?> values) {
		Query query = currentSession().createQuery(hql);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	/**
	 * find objects by sql, and convert result to specified object list (Map
	 * param)
	 * 
	 * @param sql
	 * @param values
	 *            binded by param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> findBySQL(final String sql, final Map<String, ?> values) {
		return createSQLQuery(sql, values).list();
	}

	/**
	 * find unique result by sql, convert result to specified object type (Map
	 * param)
	 * 
	 * @param sql
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <X> X findUniqueBySQL(final String sql, final Map<String, ?> values) {
		return (X) createSQLQuery(sql, values).uniqueResult();
	}

	/**
	 * Create Query object through sql/param (Map params)
	 * 
	 * @param sql
	 * @param values
	 * @return
	 */
	public SQLQuery createSQLQuery(final String sql, final Map<String, ?> values) {
		SQLQuery query = currentSession().createSQLQuery(sql);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}
	
	
	
	
	/**
	 * set first record num and max results
	 * 
	 * @param query
	 * @param pager
	 * @return
	 */
	public Query setPageParameterToQuery(Query query, PageInfo pageInfo) {
		query.setFirstResult(pageInfo.getFirstResult().intValue());
		query.setMaxResults(pageInfo.getPageSize().intValue());
		return query;
	}

	/**
	 * add order by to query
	 * 
	 * @param qurtyString
	 * @param pager
	 * @return
	 */
	protected String addOrderByToQuery(final String qurtyString, final PageInfo pager) {
		StringBuilder builder = new StringBuilder(qurtyString);
		builder.append(" order by ");

		List<PageSort> sorts = pager.getSort();
		for (PageSort sort : sorts) {
			builder.append(String.format(" %s %s", sort.getProperty(), sort.getDir()));
		}

		return builder.toString();
	}
	
	/**
	 * pager query prepare: count all and add order by
	 * 
	 * @param queryString
	 *            - query statement
	 * @param queryType
	 *            - hql/sql
	 * @param params
	 *            - params
	 * @param pager
	 *            - pager (set total records here)
	 * @return queryString - add order by clauze
	 */
	public String preparePagerQuery(String queryString, QueryType queryType, final Map<String, ?> params, PageInfo pager) {
		// count total records
		if (pager.isCountTotal()) {
			if (QueryType.HQL.equals(queryType)) {
				pager.setTotalRecords((long) find(queryString, params).size());
			} else if (QueryType.SQL.equals(queryType)) {
				pager.setTotalRecords((long) findBySQL(queryString, params).size());
			}
		}

		// order by
		if (pager.isOrderBySetted()) {
			queryString = addOrderByToQuery(queryString, pager);
		}

		return queryString;
	}

	public Object get(Class<T> tClass,Serializable pk){
		Object obj =this.currentSession().get(tClass, pk);
		return obj;
	}
	
}
