package com.haier.openplatform.console.issue.module;
/**
 * bussiness对象 对应表BUSSINESS_SERVICE
 * @author WangJian
 *
 */
public class AppBussiness {
	private Long serviceApiId;
	private String serviceName;
	private Long appId;
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public Long getServiceApiId() {
		return serviceApiId;
	}
	public void setServiceApiId(Long serviceApiId) {
		this.serviceApiId = serviceApiId;
	}
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	} 
}
