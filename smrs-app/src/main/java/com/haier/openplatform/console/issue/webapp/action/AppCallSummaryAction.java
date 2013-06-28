package com.haier.openplatform.console.issue.webapp.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.haier.openplatform.console.issue.module.ApiSearcher;
import com.haier.openplatform.console.issue.module.AppCall;
import com.haier.openplatform.console.issue.module.ServiceApi;
import com.haier.openplatform.console.issue.util.DateUtil;
import com.haier.openplatform.util.Pager;

/**
 * @author Vilight_Wu 全业务系统调用汇总
 */
public class AppCallSummaryAction extends BaseIssueAction {

	private static final long serialVersionUID = 1495635600822026612L;

	/**
	 * service分页对象
	 */
	private Pager<ServiceApi> pager = new Pager<ServiceApi>();

	/**
	 * 直线数据
	 */
	private String linedatas;

	/**
	 * 柱状数据
	 */
	private String bardatas;

	/**
	 * X轴ticks
	 */
	private List<String> ticks;

	/**
	 * 饼图数据
	 */
	private String piedata;

	/**
	 * 饼图对应表格详细数据
	 */
	private List<AppCall> appCallList;

	/**
	 * appid列表
	 */
	private List<Long> appIds;

	/**
	 * 查询时间
	 */
	private String time;

	/**
	 * 应用系统id
	 */
	private String appId;

	public List<AppCall> getAppCallList() {
		return appCallList;
	}

	public void setAppCallList(List<AppCall> appCallList) {
		this.appCallList = appCallList;
	}

	public String getPiedata() {
		return piedata;
	}

	public void setPiedata(String piedata) {
		this.piedata = piedata;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public List<Long> getAppIds() {
		return appIds;
	}

	public void setAppIds(List<Long> appIds) {
		this.appIds = appIds;
	}

	public String getLinedatas() {
		return linedatas;
	}

	public void setLinedatas(String linedatas) {
		this.linedatas = linedatas;
	}

	public String getBardatas() {
		return bardatas;
	}

	public void setBardatas(String bardatas) {
		this.bardatas = bardatas;
	}

	public List<String> getTicks() {
		return ticks;
	}

	public void setTicks(List<String> ticks) {
		this.ticks = ticks;
	}

	public Pager<ServiceApi> getPager() {
		return pager;
	}

	public void setPager(Pager<ServiceApi> pager) {
		this.pager = pager;
	}

	/**
	 * 查询某个app调用前10
	 * 
	 * @return json格式（填充前台表格）
	 * @throws Exception
	 */
	public String appCallTopTen() throws Exception {
		Date fromDate = null, toDate = null;
		DateUtil dt = new DateUtil();
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		if (time != null && appId != null) {
			if ("week".equals(time)) { // 同期近三周
				// 本周一
				String monday = dt.getMondayOFWeek(pattern);
				// 本周日
				String sunday = dt.getCurrentWeekday(pattern);
				fromDate = formatter.parse(monday);
				toDate = formatter.parse(sunday);
			} else {
				// 本月第一天
				String firstday = dt.getFirstDayOfMonth(pattern);
				// 本月最后一天
				String lastday = dt.getDefaultDay(pattern);
				fromDate = formatter.parse(firstday);
				toDate = formatter.parse(lastday);
			}
			/**
			 * 获取本周该应用系统前10api调用
			 */
			pager.setCurrentPage(getPage());
			pager.setPageSize(getRows());
			ApiSearcher apiSearcher = new ApiSearcher(fromDate, toDate, pager);
			apiSearcher.setAppId(Long.valueOf(appId));
			pager = getAppMonitorService().getServiceApiCall(apiSearcher);
			List<ServiceApi> apis = pager.getRecords();
			for (ServiceApi api : apis) {
				api.setAverageTime((double) api.getExecuteTime() / api.getCallNum());
			}
		}
		return SUCCESS;
	}

	/**
	 * 查询某个app调用频次
	 * 
	 * @return json格式（填充前台直线图）
	 * @throws Exception
	 */
	public String singleAppCall() throws Exception {
		Date from1 = null, to1 = null;
		Date from2 = null, to2 = null;
		Date from3 = null, to3 = null;
		List<String> tks = new ArrayList<String>();
		List<HopPointData> callNum = new ArrayList<HopPointData>();
		List<HopPointData> avgTime = new ArrayList<HopPointData>();
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		if (time != null && appId != null) {
			DateUtil dt = new DateUtil();
			if ("week".equals(time)) { // 同期近三周
				/**
				 * 设置本周起止日期
				 */
				// 本周一
				String monday = dt.getMondayOFWeek(pattern);
				// 本周日
				String sunday = dt.getCurrentWeekday(pattern);
				from1 = formatter.parse(monday);
				to1 = formatter.parse(sunday);

				/**
				 * 设置上周起止日期
				 */
				// 上周一
				Calendar cal = Calendar.getInstance();
				cal.setTime(from1);
				cal.add(Calendar.DATE, -7); // 前7天
				from2 = cal.getTime();
				cal.setTime(to1);
				cal.add(Calendar.DATE, -7);
				to2 = cal.getTime();

				/**
				 * 设置上上周起止日期
				 */
				// 上上周一
				cal.setTime(from2);
				cal.add(Calendar.DATE, -7); // 前7天
				from3 = cal.getTime();
				// 上上周日
				cal.setTime(to2);
				cal.add(Calendar.DATE, -7); // 前7天
				to3 = cal.getTime();
				SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				String pprevMon = sdf.format(from3);
				String pprevSun = sdf.format(to3);
				String prevMonday = formatter.format(from2);
				String prevSunday = formatter.format(to2);
				tks.add(pprevMon + " - " + pprevSun);
				tks.add(prevMonday + " - " + prevSunday);
				tks.add(monday + " - " + sunday);

			} else { // 本月
				/**
				 * 获取本月起止日期
				 */
				// 本月第一天
				String firstday = dt.getFirstDayOfMonth(pattern);
				// 本月最后一天
				String lastday = dt.getDefaultDay(pattern);
				from1 = formatter.parse(firstday);
				to1 = formatter.parse(lastday);
				/**
				 * 获取上月起止日期
				 */
				Calendar cal = Calendar.getInstance();
				cal.setTime(from1);
				int year1 = cal.get(Calendar.YEAR);
				int month1 = cal.get(Calendar.MONTH) + 1;
				String tk1 = year1 + "-" + month1;
				cal.add(Calendar.MONTH, -1); // 上个月
				from2 = cal.getTime();
				int year2 = cal.get(Calendar.YEAR);
				int month2 = cal.get(Calendar.MONTH) + 1;
				String tk2 = year2 + "-" + month2;
				cal.setTime(to1);
				cal.add(Calendar.MONTH, -1);
				to2 = cal.getTime();
				/**
				 * 获取上上月起止日期
				 */
				cal.setTime(from2);
				cal.add(Calendar.MONTH, -1); // 上个月
				from3 = cal.getTime();
				cal.setTime(to2);
				cal.add(Calendar.MONTH, -1);
				to3 = cal.getTime();
				int year3 = cal.get(Calendar.YEAR);
				int month3 = cal.get(Calendar.MONTH) + 1;
				String tk3 = year3 + "-" + month3;
				tks.add(tk3);
				tks.add(tk2);
				tks.add(tk1);

			}

			/**
			 * 获取上上周/上上月数据
			 */
			List<AppCall> apps3 = getReportService().getAllApiCallGroupByApp(Long.valueOf(appId), from3, to3);
			if (apps3 != null && apps3.size() != 0) {
				AppCall app = apps3.get(0); // 取第一条数据（也只有一条）
				long num = app.getNum();
				long itime = app.getExecuteTime();
				callNum.add(new HopPointData("1", String.valueOf(num))); // 第1组数据
				avgTime.add(new HopPointData("1", String.valueOf((double) itime / num)));
			} else {
				callNum.add(new HopPointData("1", String.valueOf(0)));
				avgTime.add(new HopPointData("1", String.valueOf(0)));
			}

			/**
			 * 获取上周/上月数据
			 */
			List<AppCall> apps2 = getReportService().getAllApiCallGroupByApp(Long.valueOf(appId), from2, to2);
			if (apps2 != null && apps2.size() != 0) {
				AppCall app = apps2.get(0); // 取第一条数据（也只有一条）
				long num = app.getNum();
				long ibctime = app.getExecuteTime();
				callNum.add(new HopPointData("2", String.valueOf(num))); // 第2组数据
				avgTime.add(new HopPointData("2", String.valueOf((double) ibctime / num)));
			} else {
				callNum.add(new HopPointData("2", String.valueOf(0)));
				avgTime.add(new HopPointData("2", String.valueOf(0)));
			}

			/**
			 * 获取本周/本月数据
			 */
			List<AppCall> apps = getReportService().getAllApiCallGroupByApp(Long.valueOf(appId), from1, to1);
			if (apps != null && apps.size() != 0) {
				AppCall app = apps.get(0); // 取第一条数据（也只有一条）
				long num = app.getNum();
				long icatime = app.getExecuteTime();
				callNum.add(new HopPointData("3", String.valueOf(num))); // 第3组数据
				avgTime.add(new HopPointData("3", String.valueOf((double) icatime / num)));
			} else {
				callNum.add(new HopPointData("3", String.valueOf(0)));
				avgTime.add(new HopPointData("3", String.valueOf(0)));
			}
			ticks = tks;
			bardatas = HopChartUtil.formatPointData(callNum);
			linedatas = HopChartUtil.formatPointData(avgTime);
		}
		return SUCCESS;
	}

	/**
	 * 全业务调用频次比较（饼图）
	 * 
	 * @return json格式。
	 * @throws Exception
	 */
	public String allAppCall() throws Exception {
		Date fromDate = null, toDate = null;
		if (time != null) {
			DateUtil dt = new DateUtil();
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			if ("week".equals(time)) { // 本周
				// 本周一
				String monday = dt.getMondayOFWeek(pattern);
				// 本周日
				String sunday = dt.getCurrentWeekday(pattern);
				fromDate = formatter.parse(monday);
				toDate = formatter.parse(sunday);
			} else { // 本本月
				// 本月第一天
				String firstday = dt.getFirstDayOfMonth(pattern);
				// 本月最后一天
				String lastday = dt.getDefaultDay(pattern);
				fromDate = formatter.parse(firstday);
				toDate = formatter.parse(lastday);
			}
			List<AppCall> apps = getReportService().getAllApiCallGroupByApp(null, fromDate, toDate);
			List<HopPointData> points = new ArrayList<HopPointData>();
			List<Long> ids = new ArrayList<Long>();
			for (AppCall app : apps) {
				// 设置每个app平均消耗时间
				long num = app.getNum();
				long executeTime = app.getExecuteTime();
				app.setAverageTime((double) executeTime / num);
				// 设置饼图数据
				points.add(new HopPointData("'" + app.getAppName() + "'", String.valueOf(num)));
				// 保存appid
				ids.add(app.getAppId());
			}
			appIds = ids;
			appCallList = apps;
			piedata = HopChartUtil.formatPointData(points);
		}
		return SUCCESS;
	}

	public String execute() throws Exception {
		return SUCCESS;
	}

}
