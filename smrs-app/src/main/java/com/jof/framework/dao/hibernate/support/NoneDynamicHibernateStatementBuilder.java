package com.jof.framework.dao.hibernate.support;

import java.util.HashMap;
import java.util.Map;


/**
 * @author WangXuzheng
 *
 */
public class NoneDynamicHibernateStatementBuilder implements DynamicHibernateStatementBuilder {

	@Override
	public Map<String, DynamicQuery> getNamedDynamicQueries() {
		return new HashMap<String, DynamicQuery>();
	}

	@Override
	public Map<String, StatementTemplate> getTemplateCache() {
		return new HashMap<String, StatementTemplate>();
	}
}
