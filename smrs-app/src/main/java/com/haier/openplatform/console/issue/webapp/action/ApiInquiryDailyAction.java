package com.haier.openplatform.console.issue.webapp.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.haier.openplatform.console.issue.domain.AppLists;
import com.haier.openplatform.console.issue.module.ApiCallGroupByHour;

/**
 * @author Vilight_Wu API查询-24小时
 */
public class ApiInquiryDailyAction extends BaseIssueAction {

	private static final long serialVersionUID = -5729977366291677317L;

	/**
	 * 直线图数据
	 */
	private String linedata;

	/**
	 * 柱状图数据
	 */
	private String bardata;

	/**
	 * 图表横轴ticks
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
	 * 查询日期
	 */
	private String from;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public List<AppLists> getAppLists() {
		return appLists;
	}

	public void setAppLists(List<AppLists> appLists) {
		this.appLists = appLists;
	}

	public String getLinedata() {
		return linedata;
	}

	public void setLinedata(String linedata) {
		this.linedata = linedata;
	}

	public String getBardata() {
		return bardata;
	}

	public void setBardata(String bardata) {
		this.bardata = bardata;
	}

	public List<String> getTicks() {
		return ticks;
	}

	public void setTicks(List<String> ticks) {
		this.ticks = ticks;
	}

	/**
	 * 获取API24小时调用频次
	 * 
	 * @return json数据，展示前台报表
	 * @throws Exception
	 */
	public String apiDailyCall() throws Exception {
		if (appId != null && from != null) {
			Date fromDate = new SimpleDateFormat("yyyy-MM-dd").parse(from);
			Calendar cal = Calendar.getInstance();
			cal.setTime(fromDate);
			cal.add(Calendar.DATE, 1); // 下一天
			Date toDate = cal.getTime();
			List<ApiCallGroupByHour> apiDailyCall = getAppMonitorService().getApiCallGroupByHour(Long.valueOf(appId),
					fromDate, toDate);
			List<String> tks = new ArrayList<String>();
			List<HopPointData> apicall = new ArrayList<HopPointData>();
			List<HopPointData> apitime = new ArrayList<HopPointData>();
			for (ApiCallGroupByHour api : apiDailyCall) {
				String hour = api.getHour();
				tks.add(hour + "时");
				apicall.add(new HopPointData(String.valueOf(tks.size()), String.valueOf(api.getCallNum())));
				apitime.add(new HopPointData(String.valueOf(tks.size()), String.valueOf((double) api.getExecuteTime()
						/ api.getCallNum())));
			}

			ticks = tks;
			linedata = HopChartUtil.formatPointData(apitime);
			bardata = HopChartUtil.formatPointData(apicall);
		}
		return SUCCESS;
	}

	public String execute() throws Exception {
		// 获取应用系统列表
		appLists = getBaseInfoService().getAppLists();
		return SUCCESS;
	}
}
