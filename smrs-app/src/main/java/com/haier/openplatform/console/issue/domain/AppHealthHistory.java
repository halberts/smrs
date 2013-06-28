package com.haier.openplatform.console.issue.domain;

import java.util.Date;

import com.haier.openplatform.domain.BaseDomain;

public class AppHealthHistory extends BaseDomain<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1427393931764141839L;
	private Long id;
	private Long urlId;
	private Integer urlStatus;
	private String urlStatusMsg;
	private String lastModifiedBy;
	private Date lastModifiedDate;
	private String createBy;
	private Date createDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUrlId() {
		return urlId;
	}
	public void setUrlId(Long urlId) {
		this.urlId = urlId;
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
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
