package com.haier.openplatform.console.issue.module;

import java.util.Date;

import com.haier.openplatform.util.Pager;

/**
 * @author shanjing
 * 参数列表类：超时前10查询
 */
public class Searcher {

	/**
	 * 应用ID：为空则查询全部
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
	 * Service名称：为空则查全部
	 */
	private String serviceName;

	/**
	 * 分页
	 */
	private Pager<Issue> pager;

	public Searcher(Date from, Date to, Pager<Issue> pager) {
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

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Pager<Issue> getPager() {
		return pager;
	}

	public void setPager(Pager<Issue> pager) {
		this.pager = pager;
	}

	@Override
	public String toString() {
		return "OvertimeSearcher [appId=" + appId + ", from=" + from + ", to=" + to + ", serviceName=" + serviceName
				+ ", pager=" + pager + "]";
	}

}
