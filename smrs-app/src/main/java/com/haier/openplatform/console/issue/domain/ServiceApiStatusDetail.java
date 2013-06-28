package com.haier.openplatform.console.issue.domain;

import com.haier.openplatform.domain.BaseDomain;

/**
 * @author shanjing
 * 持久化类：API的运行状态
 */
public class ServiceApiStatusDetail extends BaseDomain<Long> {

	private static final long serialVersionUID = 4526278066125181886L;

	/**
	 * Service信息ID
	 */
	private long serviceApiId;

	/**
	 * 节点ID
	 */
	private long nodeId;

	/**
	 * 数据仓库ID
	 */
	private long timeId;

	/**
	 * 调用次数
	 */
	private long callNum;

	/**
	 * 执行时间加和
	 */
	private long respinseTimeSum;

	public long getServiceApiId() {
		return serviceApiId;
	}

	public void setServiceApiId(long serviceApiId) {
		this.serviceApiId = serviceApiId;
	}

	public long getNodeId() {
		return nodeId;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	public long getTimeId() {
		return timeId;
	}

	public void setTimeId(long timeId) {
		this.timeId = timeId;
	}

	public long getCallNum() {
		return callNum;
	}

	public void setCallNum(long callNum) {
		this.callNum = callNum;
	}

	public long getRespinseTimeSum() {
		return respinseTimeSum;
	}

	public void setRespinseTimeSum(long respinseTimeSum) {
		this.respinseTimeSum = respinseTimeSum;
	}

	@Override
	public String toString() {
		return "ServiceApiStatusDetail [serviceApiId=" + serviceApiId + ", nodeId=" + nodeId + ", timeId=" + timeId
				+ ", callNum=" + callNum + ", respinseTimeSum=" + respinseTimeSum + "]";
	}

}
