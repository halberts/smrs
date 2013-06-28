package com.jof.framework.util;

import java.io.Serializable;

/**
 * 查询参数封装对象
 * @author WangXuzheng
 *
 */
public class SearchModel<T> implements Serializable {
	private static final long serialVersionUID = 8161689763787936787L;
	private Pager<T> pager = new Pager<T>();
	public Pager<T> getPager() {
		return pager;
	}
	public void setPager(Pager<T> pager) {
		this.pager = pager;
	}
}
