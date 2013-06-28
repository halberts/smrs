package com.jof.framework.dao.hibernate;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.type.Type;

/**
 * java与hibernate映射类型
 * @author WangXuzheng
 *
 */
public class JavaHibernateType{
	private Type hibernateType;
	private Class<?> javaTypeClass;
	public JavaHibernateType(Type hibernateType, Class<?> javaTypeClass) {
		this.hibernateType = hibernateType;
		this.javaTypeClass = javaTypeClass;
	}
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	public Type getHibernateType() {
		return hibernateType;
	}
	public Class<?> getJavaTypeClass() {
		return javaTypeClass;
	}
}
