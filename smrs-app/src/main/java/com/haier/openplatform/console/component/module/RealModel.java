package com.haier.openplatform.console.component.module;
/**
 * 实际Model
 */

public class RealModel{
	private Long id;
	private String appName;
	private String modelId;
	private int status;  
	public Long getId() {
		return id;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public void setId(Long id) {
		this.id = id;
	} 
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
 
}
