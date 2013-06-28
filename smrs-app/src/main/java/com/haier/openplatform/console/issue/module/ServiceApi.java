package com.haier.openplatform.console.issue.module;

/**
 * @author shanjing 结果集：Service的API调用频率和消耗时间
 */
public class ServiceApi {

	/**
	 * Service名称
	 */
	private String serviceName;

	/**
	 * 是否监控这个Service的所有异常
	 */
	private String serviceVip;

	/**
	 * 阀值上限
	 */
	private long alertMax;

	/**
	 * 超时级别
	 */
	private String overtimeLv;

	/**
	 * 调用次数
	 */
	private Long callNum;

	/**
	 * 消耗时间总和
	 */
	private Long executeTime;

	/**
	 * 平均消耗时间
	 */
	private Double averageTime;

	/**
	 * 最后执行时间
	 */
	private String lastExecTime;

	public Double getAverageTime() {
		return averageTime;
	}

	public void setAverageTime(Double averageTime) {
		this.averageTime = averageTime;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Long getCallNum() {
		return callNum;
	}

	public void setCallNum(Long callNum) {
		this.callNum = callNum;
	}

	public Long getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(Long executeTime) {
		this.executeTime = executeTime;
	}

	public String getServiceVip() {
		return serviceVip;
	}

	public void setServiceVip(String serviceVip) {
		this.serviceVip = serviceVip;
	}

	public long getAlertMax() {
		return alertMax;
	}

	public void setAlertMax(long alertMax) {
		this.alertMax = alertMax;
	}

	public String getOvertimeLv() {
		return overtimeLv;
	}

	public void setOvertimeLv(String overtimeLv) {
		this.overtimeLv = overtimeLv;
	}

	public String getLastExecTime() {
		return lastExecTime;
	}

	public void setLastExecTime(String lastExecTime) {
		this.lastExecTime = lastExecTime;
	}

	@Override
	public String toString() {
		return "ServiceApi [serviceName=" + serviceName + ", serviceVip=" + serviceVip + ", alertMax=" + alertMax
				+ ", overtimeLv=" + overtimeLv + ", callNum=" + callNum + ", executeTime=" + executeTime
				+ ", averageTime=" + averageTime + ", lastExecTime=" + lastExecTime + "]";
	}

}
