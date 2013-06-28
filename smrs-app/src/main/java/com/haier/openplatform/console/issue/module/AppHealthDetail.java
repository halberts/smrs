package com.haier.openplatform.console.issue.module;

import java.util.Date;

public class AppHealthDetail {

	private Long id;
	private Long appId;
	private String url;
	private Integer status;
	private String nodeName;
	private Integer urlStatus;
	private String urlStatusMsg;
	private Date createDate;
	private String createDateStr;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public Integer getUrlStatus() {
		return urlStatus;
	}
	public void setUrlStatus(Integer urlStatus) {
		this.urlStatus = urlStatus;
	}
	public String getUrlStatusMsg() {
		return urlStatusMsg;
	}
	public void setUrlStatusMsg(String urlStatusMsg) {
		this.urlStatusMsg = urlStatusMsg;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateDateStr() {
		return createDateStr;
	}
	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

}
