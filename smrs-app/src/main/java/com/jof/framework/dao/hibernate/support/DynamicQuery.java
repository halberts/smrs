package com.jof.framework.dao.hibernate.support;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 动态查询抽象类
 * @author WangXuzheng
 *
 */
public abstract class DynamicQuery {
	/**
	 * 查询名称
	 */
	private String queryName;
	/**
	 * 是否启用查询缓存
	 */
	private boolean cacheable;
	/**
	 * 缓存区域的名称-通常设置在ehcache.xml中
	 */
	private String cacheRegion;
	/**
	 * sql/hql字符串
	 */
	private String queryString;
	private Return returnMapping = new Return();
	public String getQueryName() {
		return queryName;
	}
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	public Return getReturnMapping() {
		return returnMapping;
	}
	public void setReturnMapping(Return returnMapping) {
		this.returnMapping = returnMapping;
	}
	public boolean isCacheable() {
		return cacheable;
	}
	public void setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
	}
	public String getCacheRegion() {
		return cacheRegion;
	}
	public void setCacheRegion(String cacheRegion) {
		this.cacheRegion = cacheRegion;
	}
	/**
	 * 查询语句的类型
	 * @return
	 */
	public abstract QueryType type();
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this,obj);
	}
}
