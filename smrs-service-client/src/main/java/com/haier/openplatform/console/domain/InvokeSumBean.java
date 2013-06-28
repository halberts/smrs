package com.haier.openplatform.console.domain;

import java.io.Serializable;
import java.lang.reflect.Field;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("InvokeSumBean")
public class InvokeSumBean implements Serializable {

	private static final long serialVersionUID = -7875215571301203001L;
	/**
	 * 
	 */
	@XStreamAlias("callNum")
	private long callNum = 0;
	/**
	 * 
	 */
	@XStreamAlias("responseTime")
	private long responseTime = 0;
	/**
	 * 
	 */
	@XStreamAlias("apiNM")
	private String apiNM;
	/**
	 * 
	 */
	@XStreamAlias("appNM")
	private String appNM;
	/**
	 * 
	 */
	@XStreamAlias("nodeNM")
	private String nodeNM;
	/**
	 * 
	 */
	@XStreamAlias("nodeIP")
	private String nodeIP;
	/**
	 * 
	 */
	@XStreamAlias("gmtCreate")
	private String gmtCreate;

	private long startTime = 0;

	public InvokeSumBean(String nm, long resTime) {
		apiNM = nm;
		responseTime = resTime;
	}

	public InvokeSumBean() {
	}

	public long getCallNum() {
		return callNum;
	}

	public void setCallNum(long callNum) {
		this.callNum = this.callNum + callNum;
	}

	public void setCallNum() {
		this.callNum = this.callNum + 1;
	}

	public long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(long responseTime) {
		this.responseTime = this.responseTime + responseTime;
	}

	public String getApiNM() {
		return apiNM;
	}

	public void setApiNM(String apiNM) {
		this.apiNM = apiNM;
	}

	public String getAppNM() {
		return appNM;
	}

	public void setAppNM(String appNM) {
		this.appNM = appNM;
	}

	public String getNodeNM() {
		return nodeNM;
	}

	public void setNodeNM(String nodeNM) {
		this.nodeNM = nodeNM;
	}

	public String getNodeIP() {
		return nodeIP;
	}

	public void setNodeIP(String nodeIP) {
		this.nodeIP = nodeIP;
	}

	public String getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public String toString() {
		StringBuilder s = new StringBuilder("\r\n//==========InvokeSumBean=================\r\n");
		try {
			Class<? extends InvokeSumBean> class1 = this.getClass();
			Field afield[] = class1.getDeclaredFields();
			for (int i = 0; i < afield.length; i++){
				s.append(afield[i].getName() + ":" + afield[i].get(this) + "\r\n");
			}
		} catch (Exception exception) {
			exception.printStackTrace(System.out);
		}
		s.append("============InvokeSumBean============\r\n");
		return s.toString();
	}
}
