package com.haier.openplatform.console.issue.webapp.action;

import java.util.Date;
import java.util.List;

import com.haier.openplatform.console.issue.module.ApiSearcher;
import com.haier.openplatform.console.issue.module.ServiceApi;
import com.haier.openplatform.util.Pager;

/**
 * 获取应用在某个时间段内调用量前N名的
 * @author WangXuzheng
 *
 */
public class SearchAppCallSumTopAPIAction extends BaseIssueAction {
	private static final long serialVersionUID = 6722516292477287190L;

	private Pager<ServiceApi> pager = new Pager<ServiceApi>();
	private long appId;
	private long fromeDate;
	private long toDate;
	public Pager<ServiceApi> getPager() {
		return pager;
	}
	public void setPager(Pager<ServiceApi> pager) {
	}
	
	public long getAppId() {
		return appId;
	}
	public void setAppId(long appId) {
		this.appId = appId;
	}
	public long getFromeDate() {
		return fromeDate;
	}
	public void setFromeDate(long fromeDate) {
		this.fromeDate = fromeDate;
	}
	public long getToDate() {
		return toDate;
	}
	public void setToDate(long toDate) {
		this.toDate = toDate;
	}
	@Override
	public String execute() throws Exception {
		// 获取该应用系统超时前10
		ApiSearcher apiSearcher = new ApiSearcher(new Date(fromeDate), new Date(toDate), pager);
		apiSearcher.setAppId(Long.valueOf(appId));
		pager = getAppMonitorService().getServiceApiCall(apiSearcher);
		pager.setPageSize(10L);
		List<ServiceApi> apis = pager.getRecords();
		for (ServiceApi api : apis) {
			api.setAverageTime((double)api.getExecuteTime() / api.getCallNum());
		}
		return SUCCESS;
	}
}
