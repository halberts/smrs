package com.haier.openplatform.console.issue.domain;

import java.util.Date;

import com.haier.openplatform.domain.BaseDomain;

/**
 * 服务器健康状况
 */
public class AppServerChecker extends BaseDomain<Long> {

	private static final long serialVersionUID = -5521632199340364626L;
	private Long appId;
	private String nodeName;
	private String urlChecker;
	private int urlStatus;
	private String urlStatusMsg;
	private Date checkTime;
	private int isLast;
	private int useEnable;
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public int getUseEnable() {
		return useEnable;
	}
	public void setUseEnable(int useEnable) {
		this.useEnable = useEnable;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getUrlChecker() {
		return urlChecker;
	}
	public void setUrlChecker(String urlChecker) {
		this.urlChecker = urlChecker;
	} 
	public int getUrlStatus() {
		return urlStatus;
	}
	public void setUrlStatus(int urlStatus) {
		this.urlStatus = urlStatus;
	}
	public String getUrlStatusMsg() {
		return urlStatusMsg;
	}
	public void setUrlStatusMsg(String urlStatusMsg) {
		this.urlStatusMsg = urlStatusMsg;
	}  
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public int getIsLast() {
		return isLast;
	}
	public void setIsLast(int isLast) {
		this.isLast = isLast;
	}
}
