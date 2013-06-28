package com.haier.openplatform.console.issue.domain;

import com.haier.openplatform.domain.BaseDomain;

/**
 * @author Mike Yang 持久化类：Service调用Queue信息
 */
public class ServiceApiStatusQueue extends BaseDomain<Long> {

	private static final long serialVersionUID = 4963279952209633421L;

	/**
	 * Service名称
	 */
	private String serviceApiName;

	/**
	 * 应用系统名称
	 */
	private String appName;

	/**
	 * 节点名称
	 */
	private String nodeName;

	/**
	 * 节点IP地址
	 */
	private String nodeIp;

	/**
	 * 调用次数
	 */
	private long callNum;

	/**
	 * 响应时间加和
	 */
	private long responseTimeSum;

	public String getServiceApiName() {
		return serviceApiName;
	}

	public void setServiceApiName(String serviceApiName) {
		this.serviceApiName = serviceApiName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeIp() {
		return nodeIp;
	}

	public void setNodeIp(String nodeIp) {
		this.nodeIp = nodeIp;
	}

	public long getCallNum() {
		return callNum;
	}

	public void setCallNum(long callNum) {
		this.callNum = callNum;
	}

	public long getResponseTimeSum() {
		return responseTimeSum;
	}

	public void setResponseTimeSum(long responseTimeSum) {
		this.responseTimeSum = responseTimeSum;
	}

	@Override
	public String toString() {
		return "ServiceApiStatusQueue [serviceApiName=" + serviceApiName
				+ ", appName=" + appName + ", nodeName=" + nodeName
				+ ", nodeIp=" + nodeIp + ", callNum=" + callNum
				+ ", responseTimeSum=" + responseTimeSum + "]";
	}

}
