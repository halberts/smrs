package com.haier.openplatform.console.issue.module;

/**
 * @author shanjing 结果集：异常详细信息
 */
public class IssueDetail {
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
	 * 输入参数
	 */
	private String inValue;

	/**
	 * 异常详细信息
	 */
	private String serviceEx;

	/**
	 * SQL栈信息
	 */
	private String insight;

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

	public String getInValue() {
		return inValue;
	}

	public void setInValue(String inValue) {
		this.inValue = inValue;
	}

	public String getServiceEx() {
		return serviceEx;
	}

	public void setServiceEx(String serviceEx) {
		this.serviceEx = serviceEx;
	}

	public String getInsight() {
		return insight;
	}

	public void setInsight(String insight) {
		this.insight = insight;
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

	@Override
	public String toString() {
		return "IssueDetail [appName=" + appName + ", nodeName=" + nodeAlias + ", nodeIp=" + nodeIp + ", actionName="
				+ actionName + ", inValue=" + inValue + ", serviceEx=" + serviceEx + ", insight=" + insight
				+ ", excuteTime=" + excuteTime + ", currentThrNum=" + currentThrNum + ", createTime=" + createTime
				+ ", ownerName=" + ownerName + ", ownerEmail=" + ownerEmail + ", ownerPhone=" + ownerPhone + "]";
	}

}