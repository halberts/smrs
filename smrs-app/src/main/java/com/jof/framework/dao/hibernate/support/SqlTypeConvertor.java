package com.jof.framework.dao.hibernate.support;

/**
 * 将返回结果的sql类型转换为实际的java类型
 * @author WangXuzheng
 *
 */
public interface SqlTypeConvertor<IN,OUT> {
	/**
	 * 做实际的转换
	 * @param in
	 * @return
	 */
	public OUT resolve(IN in);
}
