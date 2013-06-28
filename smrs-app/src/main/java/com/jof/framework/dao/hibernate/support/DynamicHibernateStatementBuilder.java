package com.jof.framework.dao.hibernate.support;

import java.util.Map;

/**
 * 动态sql/hql语句组装器
 * @author WangXuzheng
 *
 */
public interface DynamicHibernateStatementBuilder {
	/**
	 * hql语句map
	 * @return
	 */
	public Map<String,DynamicQuery> getNamedDynamicQueries();
	/**
	 * sql语句map
	 * @return
	 */
	public Map<String, StatementTemplate> getTemplateCache();
}
