package com.jof.framework.dao.hibernate.support;

/**
 * @author WangXuzheng
 *
 */
public class DynamicSqlQuery extends DynamicQuery {
	@Override
	public QueryType type() {
		return QueryType.SQL;
	}
}
