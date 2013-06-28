package com.haier.openplatform.console.issue.module;

/**
 * @author WangJian 
 * 结果集：Service的API最近一个月平均消耗时间
 */
public class ServiceApiAverage {
	/**
	 * Service ID
	 */
	private Long serviceId;
	/**
	 * Service名称
	 */
	private String serviceName; 

	/**
	 * 平均消耗时间
	 */
	private Double averageTime;
 

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Double getAverageTime() {
		return averageTime;
	}

	public void setAverageTime(Double averageTime) {
		this.averageTime = averageTime;
	}
    
    
}
