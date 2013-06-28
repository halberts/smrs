package com.haier.openplatform.console.issue.module;
/**
 * 查询APP负责人信息
 * @author WangJian
 */
public class AppIssueSupporter {
	/**
	 * 海尔负责人姓名
	 */
	private String ownerName;

	/**
	 * 海尔负责人Email
	 */
	private String ownerEmail;
 	
	/**
	 * 应用名称
	 */
	private String appName;

	/**
	 * 负责人ID
	 */
	private Long id;
	
	/**
	 * 第二负责人Name、
	 */
	private String sptOwnerName;
	
	/**
	 * 第二负责人Email
	 */
	private String sptOwnerEmail;
	
	public String getSptOwnerEmail() {
		return sptOwnerEmail;
	}

	public void setSptOwnerEmail(String sptOwnerEmail) {
		this.sptOwnerEmail = sptOwnerEmail;
	}

	public String getSptOwnerName() {
		return sptOwnerName;
	}

	public void setSptOwnerName(String sptOwnerName) {
		this.sptOwnerName = sptOwnerName;
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

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
