package com.jof.framework.dao.hibernate.support;

/**
 * 动态查询语句的return属性
 * @author WangXuzheng
 *
 */
public class ReturnProperty {
	/**
	 * 类属性名
	 */
	private String name;
	/**
	 * 字段类型
	 */
	private String type;
	/**
	 * 数据库列名
	 */
	private String column;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
}
