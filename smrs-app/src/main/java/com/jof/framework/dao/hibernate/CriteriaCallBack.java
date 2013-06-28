package com.jof.framework.dao.hibernate;

import org.hibernate.Criteria;

/**
 * Criteria回调接口，用来设置criteria属性值
 * @author WangXuzheng
 * @see org.hibernate.Criteria
 */
public interface CriteriaCallBack {
	/**
	 * 设置criteria属性值
	 * @param criteria
	 */
	public void execute(Criteria criteria);
}
