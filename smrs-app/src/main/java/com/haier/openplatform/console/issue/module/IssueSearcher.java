package com.haier.openplatform.console.issue.module;

import java.util.Date;

import com.haier.openplatform.util.Pager;

/**
 * @author shanjing
 * 参数列表类：异常查询参数
 */
public class IssueSearcher {

	/**
	 * 应用ID:为空则查全部应用
	 */
	private Long appId;

	/**
	 * 起始时间
	 */
	private Date from;

	/**
	 * 结束时间
	 */
	private Date to;

	/**
	 * 异常类型：为空则查处Debug类型外所有类型异常
	 */
	private Long issueTypeId;

	/**
	 * Service名称：为空则查所有Service
	 */
	private String serviceName;

	/**
	 * 分页
	 */
	private Pager<Issue> pager;

	public IssueSearcher(Date from, Date to, Pager<Issue> pager) {
		super();
		this.from = from;
		this.to = to;
		this.pager = pager; 
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

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

	public Long getIssueTypeId() {
		return issueTypeId;
	}

	public void setIssueTypeId(Long issueTypeId) {
		this.issueTypeId = issueTypeId;
	}

	public Pager<Issue> getPager() {
		return pager;
	}

	public void setPager(Pager<Issue> pager) {
		this.pager = pager;
	}


	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}


	@Override
	public String toString() {
		return "IssueSearcher [appId=" + appId + ", from=" + from + ", to=" + to + ", issueTypeId=" + issueTypeId
				+ ", serviceName=" + serviceName + ", pager=" + pager + "]";
	}

}
