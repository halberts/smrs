package com.haier.openplatform.console.issue.webapp.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.haier.openplatform.console.issue.module.ApiCallGroupByLevel;
import com.haier.openplatform.console.issue.module.AppOvertime;
import com.haier.openplatform.console.issue.util.DateUtil;
import com.haier.openplatform.util.Pager;

/**
 * @author Vilight_Wu 全业务超时前10
 */
public class OvertimeTopChartAction extends BaseIssueAction {

	private static final long serialVersionUID = -2692632821461919453L;

	/**
	 * service分页对象
	 */
	private Pager<AppOvertime> pager = new Pager<AppOvertime>();

	/**
	 * 柱状图数据
	 */
	private String bardatas;

	/**
	 * 柱状图appid列表，用于click事件
	 */
	private List<Long> appIds;

	/**
	 * 柱状图ticks
	 */
	private List<String> ticks;

	/**
	 * 查询时间
	 */
	private String time;

	/**
	 * 应用系统id
	 */
	private String appId;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public List<String> getTicks() {
		return ticks;
	}

	public void setTicks(List<String> ticks) {
		this.ticks = ticks;
	}

	public List<Long> getAppIds() {
		return appIds;
	}

	public void setAppIds(List<Long> appIds) {
		this.appIds = appIds;
	}

	public String getBardatas() {
		return bardatas;
	}

	public void setBardatas(String bardatas) {
		this.bardatas = bardatas;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}


	public Pager<AppOvertime> getPager() {
		return pager;
	}

	public void setPager(Pager<AppOvertime> pager) {
		this.pager = pager;
	}

	/**
	 * 获取全业务超时前10
	 * 
	 * @return json格式，填充前台图表
	 * @throws Exception
	 */
	public String allOvertimeTopTen() throws Exception {
		Date fromDate = null, toDate = null;
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		if (time != null) {
			DateUtil dt = new DateUtil();
			if ("week".equals(time)) { // 本周
				// 本周一
				String monday = dt.getMondayOFWeek(pattern);
				// 本周日
				String sunday = dt.getCurrentWeekday(pattern);
				fromDate = sdf.parse(monday);
				toDate = sdf.parse(sunday);
			} else { // 本本月
				// 本月第一天
				String firstday = dt.getFirstDayOfMonth(pattern);
				// 本月最后一天
				String lastday = dt.getDefaultDay(pattern);
				fromDate = sdf.parse(firstday);
				toDate = sdf.parse(lastday);
			}

			// 获取全业务超时
			List<ApiCallGroupByLevel> topList = getReportService().getAllApiOvertimeTopTen(fromDate, toDate,
					Long.valueOf(10));
			List<HopValueData> values = new ArrayList<HopValueData>();
			List<Long> ids = new ArrayList<Long>();
			List<String> tks = new ArrayList<String>();
			for (ApiCallGroupByLevel api : topList) {
				values.add(new HopValueData(String.valueOf(api.getNum())));
				ids.add(api.getAppId());
				tks.add(api.getAppName());
			}
			ticks = tks;
			appIds = ids;
			bardatas = HopChartUtil.formatValueData(values);
		}
		return SUCCESS;
	}

	/**
	 * 获取某个应用系统超时前10
	 * 
	 * @return json格式，填充前台表格
	 * @throws Exception
	 */
	public String appOvertimeTopTen() throws Exception {
		Date fromDate = null, toDate = null;
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		if (time != null && appId != null) {
			DateUtil dt = new DateUtil();
			if ("week".equals(time)) { // 本周
				// 本周一
				String monday = dt.getMondayOFWeek(pattern);
				// 本周日
				String sunday = dt.getCurrentWeekday(pattern);
				fromDate = sdf.parse(monday);
				toDate = sdf.parse(sunday);
			} else { // 本本月
				// 本月第一天
				String firstday = dt.getFirstDayOfMonth(pattern);
				// 本月最后一天
				String lastday = dt.getDefaultDay(pattern);
				fromDate = sdf.parse(firstday);
				toDate = sdf.parse(lastday);
			}
			// 获取该应用系统超时前10
			pager.setCurrentPage(getPage());
			pager.setPageSize(Long.valueOf(10)); // 只取前10
			pager = getReportService().getAppOvertimeTopTen(Long.valueOf(appId), fromDate, toDate, pager);
			/**
			 * 设置平均消耗时间
			 */
			List<AppOvertime> apps = pager.getRecords();
			for (AppOvertime app : apps) {
				app.setAverageTime((double) app.getTotExecTime() / app.getNum());
			}
		}
		return SUCCESS;
	}

	public String execute() throws Exception {
		return SUCCESS;
	}

}
