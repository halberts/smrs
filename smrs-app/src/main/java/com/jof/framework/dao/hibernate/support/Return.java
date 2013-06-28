package com.jof.framework.dao.hibernate.support;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangXuzheng
 *
 */
public class Return {
	private Class<?> clazz;
	private List<ReturnProperty> returnProperties = new ArrayList<ReturnProperty>();
	public Class<?> getClazz() {
		return clazz;
	}
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
	public List<ReturnProperty> getReturnProperties() {
		return returnProperties;
	}
	public void setReturnProperties(List<ReturnProperty> returnProperties) {
		this.returnProperties = returnProperties;
	}
}
