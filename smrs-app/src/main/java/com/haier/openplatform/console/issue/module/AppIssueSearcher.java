package com.haier.openplatform.console.issue.module;

import java.util.Date;

import com.haier.openplatform.util.Pager;

/**
 * @author shanjing
 * 查询类：应用系统异常查询
 */
public class AppIssueSearcher {

	private Date from;

	private Date to;

	private Long appId;

	private Pager<AppApiIssue> pager;

	private Long issueTypeId;

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public Pager<AppApiIssue> getPager() {
		return pager;
	}

	public void setPager(Pager<AppApiIssue> pager) {
		this.pager = pager;
	}

	public Long getIssueTypeId() {
		return issueTypeId;
	}

	public void setIssueTypeId(Long issueTypeId) {
		this.issueTypeId = issueTypeId;
	}

	@Override
	public String toString() {
		return "AppIssueSearcher [from=" + from + ", to=" + to + ", appId=" + appId + ", pager=" + pager
				+ ", issueTypeId=" + issueTypeId + "]";
	}
	
	
}
