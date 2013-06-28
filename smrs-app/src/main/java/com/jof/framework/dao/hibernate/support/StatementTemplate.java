package com.jof.framework.dao.hibernate.support;

import freemarker.template.Template;

/**
 * 中间类，用于定义查询语句的类型和内容
 * @author WangXuzheng
 *
 */
public class StatementTemplate {
	private Template template;
	private DynamicQuery dynamicQuery;
	public StatementTemplate(Template template,DynamicQuery dynamicQuery) {
		this.template = template;
		this.dynamicQuery = dynamicQuery;
	}
	public Template getTemplate() {
		return template;
	}
	public DynamicQuery getDynamicQuery() {
		return dynamicQuery;
	}
	public QueryType getQueryType(){
		return dynamicQuery.type();
	}
}
