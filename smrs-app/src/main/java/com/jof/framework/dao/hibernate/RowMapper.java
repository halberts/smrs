package com.jof.framework.dao.hibernate;

/**
 * 将sql语句查询出来的结果映射到对象上
 * @author WangXuzheng
 * @deprecated
 * @see BaseDAOHibernateImpl#findByNamedQuery(String, java.util.Map)方法代替
 */
public interface RowMapper<T> {
	/**
	 * 将sql查询结果集映射到对象上
	 * @param columns 查询的sql字段
	 * @return
	 */
	public T fromColumn(Object[] columns);
}
