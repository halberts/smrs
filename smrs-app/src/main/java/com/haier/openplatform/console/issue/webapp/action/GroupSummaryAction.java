package com.haier.openplatform.console.issue.webapp.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.haier.openplatform.console.issue.domain.AppLists;
import com.haier.openplatform.console.issue.module.ServiceGroupIssue;

/**
 * @author Vilight_Wu 单业务分组汇总
 */
public class GroupSummaryAction extends BaseIssueAction {

	private static final long serialVersionUID = 1495635600822026612L;

	/**
	 * 柱状图数据
	 */
	private String bardata;

	/**
	 * 柱状图对应ticks
	 */
	private List<String> ticks;

	/**
	 * 应用系统列表
	 */
	private List<AppLists> appLists = new ArrayList<AppLists>();

	/**
	 * 应用系统id
	 */
	private String appId;

	/**
	 * 查询起止时间
	 */
	private String from;
	private String to;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public List<AppLists> getAppLists() {
		return appLists;
	}

	public void setAppLists(List<AppLists> appLists) {
		this.appLists = appLists;
	}

	public List<String> getTicks() {
		return ticks;
	}

	public void setTicks(List<String> ticks) {
		this.ticks = ticks;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getBardata() {
		return bardata;
	}

	public void setBardata(String bardata) {
		this.bardata = bardata;
	}

	/**
	 * 全业务异常比较（柱状图）
	 * 
	 * @return json格式。
	 * @throws Exception
	 */
	public String serviceGroupIssue() throws Exception {
		if (appId != null && from != null && to != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date fromDate = sdf.parse(from);
			Date toDate = sdf.parse(to);
			List<ServiceGroupIssue> groupIssueList = getReportService().getServiceGroupIssue(fromDate, toDate,
					Long.valueOf(appId));
			List<String> tks = new ArrayList<String>();
			List<HopValueData> values = new ArrayList<HopValueData>();
			for (ServiceGroupIssue groupIssue : groupIssueList) {
				tks.add(groupIssue.getGroupName());
				values.add(new HopValueData(groupIssue.getIssueNum().toString()));
			}
			List<String> crtdata = new ArrayList<String>();
			crtdata.add(HopChartUtil.formatValueData(values));
			bardata = crtdata.toString();
			ticks = tks;
		}
		return SUCCESS;
	}

	public String execute() throws Exception {
		// 获取应用系统列表
		appLists = getBaseInfoService().getAppLists();
		return SUCCESS;
	}

}
