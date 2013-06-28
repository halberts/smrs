package com.haier.openplatform.console.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.haier.openplatform.hmc.domain.MessageModule;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * 表示一次Action的执行
 * @author WangXuzheng
 *
 */
public class Profile extends MessageModule implements Serializable {
	private static final long serialVersionUID = 1926296440373896339L;
	/**
	 * hop框架的版本号
	 */
	@XStreamAlias("hopVersion")
	private String hopVersion;
	/**
	 * bean的调用栈信息
	 */
	@XStreamAlias("profileBean")
	private ProfileBean profileBean;
	/**
	 * 调用的开始时间
	 */
	@XStreamAlias("startTime")
	private long startTime;
	/**
	 * 调用的结束时间
	 */
	@XStreamAlias("endTime")
	private long endTime;
	/**
	 * 监控的类型，1为action监控；2为后台Job监控
	 */
	@XStreamAlias("type")
	private int type;
	/**
	 * 调用的堆栈信息
	 */
	@XStreamOmitField
	private volatile Stack<ProfileBean> invokeStack = new Stack<ProfileBean>();
			
	/**
	 * 保留字段，用来扩展添加自己的特性息
	 */
	@XStreamAlias("infomationMap")
	private Map<String, String> infomationMap = new HashMap<String, String>(); 
	public String getHopVersion() {
		return hopVersion;
	}
	public void setHopVersion(String hopVersion) {
		this.hopVersion = hopVersion;
	}
	public ProfileBean getProfileBean() {
		return profileBean;
	}
	public void setProfileBean(ProfileBean profileBean) {
		this.profileBean = profileBean;
	}
	public Map<String, String> getInfomationMap() {
		return infomationMap;
	}
	public void setInfomationMap(Map<String, String> infomationMap) {
		this.infomationMap = infomationMap;
	}
	public Stack<ProfileBean> getInvokeStack() {
		return invokeStack;
	}
	public void setInvokeStack(Stack<ProfileBean> invokeStack) {
		this.invokeStack = invokeStack;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Profile {hopVersion=" + hopVersion + ", startTime=" + startTime
				+ ", endTime=" + endTime + "}";
	}
}
