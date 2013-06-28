package com.haier.openplatform.console.issue.module;

/**
 * @author shanjing 结果集：应用系统超时
 */
public class AppOvertime {

	/**
	 * 应用系统名称
	 */
	private String serviceName;

	/**
	 * 响应级别
	 */
	private String level;

	/**
	 * 超时数量
	 */
	private Long num;

	/**
	 * 总执行时间
	 */
	private Long totExecTime;

	/**
	 * 平均消耗时间
	 */
	private Double averageTime;

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

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public Long getTotExecTime() {
		return totExecTime;
	}

	public void setTotExecTime(Long totExecTime) {
		this.totExecTime = totExecTime;
	}

	@Override
	public String toString() {
		return "AppOvertime [serviceName=" + serviceName + ", level=" + level + ", num=" + num + ", totExecTime="
				+ totExecTime + "]";
	}

}
