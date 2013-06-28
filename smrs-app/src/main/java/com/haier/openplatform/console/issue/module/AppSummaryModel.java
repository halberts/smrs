package com.haier.openplatform.console.issue.module;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.haier.openplatform.console.issue.domain.AppLists;
import com.haier.openplatform.console.issue.domain.IssueSupporter;

public class AppSummaryModel implements Serializable{
	private static final long serialVersionUID = 3842525002676188472L;
	private long totalAppSize;
	private Map<String, Integer> appMap;
	private List<AppLists> appList;
	private Map<Long, IssueSupporter> issueSupporterMap;
	public long getTotalAppSize() {
		return totalAppSize;
	}
	public void setTotalAppSize(long totalAppSize) {
		this.totalAppSize = totalAppSize;
	}
	public Map<String, Integer> getAppMap() {
		return appMap;
	}
	public void setAppMap(Map<String, Integer> appMap) {
		this.appMap = appMap;
	}
	public List<AppLists> getAppList() {
		return appList;
	}
	public void setAppList(List<AppLists> appList) {
		this.appList = appList;
	}
	public Map<Long, IssueSupporter> getIssueSupporterMap() {
		return issueSupporterMap;
	}
	public void setIssueSupporterMap(Map<Long, IssueSupporter> issueSupporterMap) {
		this.issueSupporterMap = issueSupporterMap;
	}
}
