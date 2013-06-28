package com.jof.framework.dao;

import java.io.Serializable;
import java.util.List;

import com.jof.framework.util.Pager;

/**
 * 所有dao接口的父接口，定义通用操作
 * @author WangXuzheng
 *
 */
public interface BaseDAO<T, PK extends Serializable> {
	/**
	 * 保存对象
	 * @param object
	 */
	public void save(T object);
	/**
	 * 更新对象
	 * @param object
	 */
	public void update(T object);
	
	/**
	 * 获取对象
	 * @param id
	 * @return
	 */
	public T get(PK id);
	
	/**
	 * 加载对象
	 * @param id
	 * @return
	 */
	public T load(PK id);
	
	/**
	 * 删除对象
	 * @param object
	 */
	public void delete(T object);
	
	/**
	 * 获取所有的对象列表
	 * @return
	 */
	public List<T> getAll();
	
	
	public List<T> getAllActive();
	
	/**
	 * 删除对象
	 * @param id
	 */
	public void delete(PK id);
	
	/**
	 * 按属性查找唯一对象, 匹配方式为相等.
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public T findUniqueBy(final String propertyName, final Object value);
	
	public Pager<T> getByNameLikePager(String name,final Pager<?> pageRequest);
}
