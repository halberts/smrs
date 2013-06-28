package com.jof.framework.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author WangXuzheng
 *
 */
public interface CommonDAO<T, PK extends Serializable> {
	/**
	 * 保存对象
	 * @param object
	 */
	public void save(T object);
	/**
	 * 更新对象
	 * @param object
	 */
	public int update(T object);
	
	/**
	 * 获取对象
	 * @param id
	 * @return
	 */
	public T get(PK id);
	/**
	 * 获取所有的对象列表
	 * @return
	 */
	public List<T> getAll();
	
	/**
	 * 删除对象
	 * @param id
	 */
	public int delete(PK id);
	
	/**
	 * 按属性查找唯一对象, 匹配方式为相等.
	 * @param parameter
	 * @return
	 */
	public T findUniqueBy(Map<String,Object> parameter);
}
