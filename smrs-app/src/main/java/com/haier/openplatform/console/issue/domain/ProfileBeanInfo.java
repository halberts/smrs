package com.haier.openplatform.console.issue.domain;

import java.util.HashSet;
import java.util.Set;

import com.haier.openplatform.domain.BaseDomain;

public class ProfileBeanInfo extends BaseDomain<Long> {
	private static final long serialVersionUID = 95101209601372497L;
	/**
	 * 类名称
	 */
	private String className;
	/**
	 * 调用的方法名
	 */
	private String methodName;
	/**
	 * 调用次数
	 */
	private int times;
	/**
	 * 执行时间
	 */
	private long executionTime;
	private ProfileBeanInfo parent;
	private Set<ProfileBeanInfo> children = new HashSet<ProfileBeanInfo>();
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int time) {
		this.times = time;
	}
	public long getExecutionTime() {
		return executionTime;
	}
	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}
	public ProfileBeanInfo getParent() {
		return parent;
	}
	public void setParent(ProfileBeanInfo parent) {
		this.parent = parent;
	}
	public Set<ProfileBeanInfo> getChildren() {
		return children;
	}
	public void setChildren(Set<ProfileBeanInfo> children) {
		this.children = children;
	}
}
