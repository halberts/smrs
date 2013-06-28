package com.haier.openplatform.console.issue.webapp.action;
/**
 * 获取NodeName列表
 * @author WangJian
 */
import java.util.ArrayList;
import java.util.List;

import com.haier.openplatform.console.issue.domain.AppHealthUrl;
import com.haier.openplatform.console.issue.domain.AppServerChecker;

public class SearchNodeNameAction  extends BaseIssueAction { 
	private static final long serialVersionUID = 4870467919902290940L;
	private Long appId;
	private List<AppServerChecker> appServerChecker = new ArrayList<AppServerChecker>();
	private List<AppHealthUrl> urlList = new ArrayList<AppHealthUrl>();
	
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public List<AppServerChecker> getAppServerChecker() {
		return appServerChecker;
	}
	public void setAppServerChecker(List<AppServerChecker> appServerChecker) {
		this.appServerChecker = appServerChecker;
	}
	public List<AppHealthUrl> getUrlList() {
		return urlList;
	}
	public void setUrlList(List<AppHealthUrl> urlList) {
		this.urlList = urlList;
	}
	
	@Override
	public String execute() throws Exception {
		urlList = getAppMonitorService().getAppHealthUrl(appId, null);
		return SUCCESS;
	}
	
}
