package com.haier.openplatform.console.issue.webapp.action;

import java.util.Date;
import java.util.List;

import com.haier.openplatform.console.issue.module.AppOvertime;
import com.haier.openplatform.util.Pager;

/**
 * 根据时间段查询API调用频次排名前N条数据
 * @author WangXuzheng
 *
 */
public class SearchAppOvertimeTopAPIAction extends BaseIssueAction {
	private static final long serialVersionUID = -404386863293730663L;
	private Pager<AppOvertime> pager = new Pager<AppOvertime>();
	private long appId;
	private long fromeDate;
	private long toDate;
	public Pager<AppOvertime> getPager() {
		return pager;
	}
	public void setPager(Pager<AppOvertime> pager) {
		this.pager = pager;
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
		pager.setCurrentPage(getPage());
		pager.setPageSize(10L); // 只取前10
		pager = getReportService().getAppOvertimeTopTen(appId,new Date(fromeDate), new Date(toDate), pager);
		List<AppOvertime> apps = pager.getRecords();
		for(AppOvertime app : apps) {
			app.setAverageTime((double)app.getTotExecTime() / app.getNum());
		}
		return SUCCESS;
	}
}
