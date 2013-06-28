package com.haier.openplatform.console.issue.module;

import java.sql.Clob;

public class IssueDetailModule {
	/**
	 * 应用系统名称
	 */
	private String appName;

	/**
	 * 节点名称
	 */
	private String nodeAlias;

	/**
	 * 节点IP
	 */
	private String nodeIp;

	/**
	 * Action名称
	 */
	private String actionName;

	/**
	 * 输入参数（数据库字段）
	 */
	private Clob inValueClob;

	/**
	 * 异常详细信息（数据库字段）
	 */
	private Clob serviceExClob;

	/**
	 * SQL栈信息（数据库字段）
	 */
	private Clob insightClob;

	/**
	 * 实际执行时间
	 */
	private long excuteTime;

	/**
	 * 当前线程数
	 */
	private long currentThrNum;

	/**
	 * 发生时间
	 */
	private String createTime;

	/**
	 * 负责人
	 */
	private String ownerName;

	/**
	 * 邮件
	 */
	private String ownerEmail;

	/**
	 * 电话
	 */
	private String ownerPhone;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getNodeAlias() {
		return nodeAlias;
	}

	public void setNodeAlias(String nodeAlias) {
		this.nodeAlias = nodeAlias;
	}

	public String getNodeIp() {
		return nodeIp;
	}

	public void setNodeIp(String nodeIp) {
		this.nodeIp = nodeIp;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public Clob getInValueClob() {
		return inValueClob;
	}

	public void setInValueClob(Clob inValueClob) {
		this.inValueClob = inValueClob;
	}

	public Clob getServiceExClob() {
		return serviceExClob;
	}

	public void setServiceExClob(Clob serviceExClob) {
		this.serviceExClob = serviceExClob;
	}

	public Clob getInsightClob() {
		return insightClob;
	}

	public void setInsightClob(Clob insightClob) {
		this.insightClob = insightClob;
	}

	public long getExcuteTime() {
		return excuteTime;
	}

	public void setExcuteTime(long excuteTime) {
		this.excuteTime = excuteTime;
	}

	public long getCurrentThrNum() {
		return currentThrNum;
	}

	public void setCurrentThrNum(long currentThrNum) {
		this.currentThrNum = currentThrNum;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public String getOwnerPhone() {
		return ownerPhone;
	}

	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
	}

}
