package com.haier.openplatform.console.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haier.openplatform.hmc.domain.MessageModule;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * 要进行监控的实体bean，可能是Action,Service,DAO或其他任何需要被监控的对象
 * @author WangXuzheng
 *
 */
public class ProfileBean extends MessageModule implements Serializable {
	private static final long serialVersionUID = -7288932131703113987L;
	/**
	 * 类名称
	 */
	@XStreamAlias("className")
	private String className;
	/**
	 * 调用的方法名
	 */
	@XStreamAlias("methodName")
	private String methodName;
	/**
	 * 执行的开始时间
	 */
	@XStreamAlias("startTime")
	private long startTime;
	/**
	 * 调用次数
	 */
	@XStreamAlias("times")
	private int times;
	/**
	 * 执行时间
	 */
	@XStreamAlias("executionTime")
	private long executionTime;
	/**
	 * 调用的其他组件
	 */
	@XStreamAlias("children")
	private List<ProfileBean> children = new ArrayList<ProfileBean>();
	/**
	 * key:调用的方法名称，value：方法对应的调用统计信息
	 */
	@XStreamOmitField
	private volatile Map<String, ProfileBean> methodInvokeMap = new HashMap<String, ProfileBean>();
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
	public void setTimes(int times) {
		this.times = times;
	}
	public long getExecutionTime() {
		return executionTime;
	}
	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}
	public List<ProfileBean> getChildren() {
		return children;
	}
	public void setChildren(List<ProfileBean> children) {
		this.children = children;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	/**
	 * 加入一个子节点
	 * @param profileBean
	 */
	public void addChild(ProfileBean profileBean){
		this.children.add(profileBean);
	}
	public ProfileBean getInvokedProfileBean(String className,String methodName){
		return methodInvokeMap.get(generateKey(className,methodName));
	}
	public void putInvokedProfileBean(String className,String methodName,ProfileBean profileBean){
		methodInvokeMap.put(generateKey(className,methodName), profileBean);
	}
	private String generateKey(String className,String methodName){
		return new StringBuilder(className).append('.').append(methodName).toString();
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		buildToString(this,1,builder);
		return new StringBuilder("\n").append(builder).toString();
	}
	
	private void buildToString(ProfileBean profile,int depth,StringBuilder builder){
		builder.append(generateSpace(depth,new StringBuilder()));
		builder.append("|-");
		builder.append(this.generateKey(profile.className, profile.methodName));
		builder.append('[').append(profile.times).append(']');
		builder.append('[').append(profile.executionTime).append(']');
		builder.append("\n");
		for(ProfileBean bean : profile.getChildren()){
			buildToString(bean,depth+1,builder);
		}
	}
	
	/**
	 * 生成n个空格
	 * @param depth
	 * @return
	 */
	private StringBuilder generateSpace(int depth,StringBuilder result){
		int size = (depth-1) *2;
		StringBuilder rs = new StringBuilder(size);
		for(int i = 0; i < size; i++){
			rs.append(' ');
		}
		return rs;
	}
}
