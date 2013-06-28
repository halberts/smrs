package com.jof.framework.dao.hibernate.support;

/**
 * @author WangXuzheng
 *
 */
public class DynamicHqlQuery extends DynamicQuery {
	@Override
	public QueryType type() {
		return QueryType.HQL;
	}
}
