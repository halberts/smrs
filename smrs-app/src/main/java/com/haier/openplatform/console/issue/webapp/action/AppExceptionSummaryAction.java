package com.haier.openplatform.console.issue.webapp.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.haier.openplatform.console.issue.domain.AppLists;
import com.haier.openplatform.console.issue.module.AppApiIssue;
import com.haier.openplatform.console.issue.module.AppIssueSearcher;
import com.haier.openplatform.console.issue.module.IssueType;
import com.haier.openplatform.console.issue.module.ShowIssue;
import com.haier.openplatform.console.issue.util.DateUtil;
import com.haier.openplatform.util.Pager;

/**
 * @author Vilight_Wu 全业务系统异常汇总
 */
public class AppExceptionSummaryAction extends BaseIssueAction {

	private static final long serialVersionUID = 1495635600822026612L;

	private List<String> barLabels = new ArrayList<String>();
	private List<Object> exAppLists = new ArrayList<Object>();
	private List<AppLists> appLists = new ArrayList<AppLists>();
	/**
	 * service分页对象
	 */
	private Pager<AppApiIssue> pager = new Pager<AppApiIssue>();
	/**
	 * 直线图数据
	 */
	private String linedata;
	/**
	 * 柱状图数据
	 */
	private String bardata;
	/**
	 * 柱状图对应labels
	 */
	private List<String> labels;
	/**
	 * 柱状图对应ticks
	 */
	private List<String> ticks;
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

	public List<String> getBarLabels() {
		return barLabels;
	}

	public void setBarLabels(List<String> barLabels) {
		this.barLabels = barLabels;
	}

	public List<Object> getExAppLists() {
		return exAppLists;
	}

	public void setExAppLists(List<Object> exAppLists) {
		this.exAppLists = exAppLists;
	}

	public List<AppLists> getAppLists() {
		return appLists;
	}

	public void setAppLists(List<AppLists> appLists) {
		this.appLists = appLists;
	}

	public Pager<AppApiIssue> getPager() {
		return pager;
	}

	public void setPager(Pager<AppApiIssue> pager) {
		this.pager = pager;
	}

	public String getLinedata() {
		return linedata;
	}

	public void setLinedata(String linedata) {
		this.linedata = linedata;
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

	public String getBardata() {
		return bardata;
	}

	public void setBardata(String bardata) {
		this.bardata = bardata;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	private List<String> buildLabels() {
		List<String> lbs = new ArrayList<String>();
		// 只看这三种异常
		lbs.add("已知异常");
		lbs.add("未知异常");

		return lbs;
	}

	private List<String> buildChart(List<Long> hopValData1,
			List<Long> hopValData2, List<Long> hopValData3) {
		List<String> chartdata = new ArrayList<String>();

		for (int i = 0; i < 2; i++) { // 3组数据
			List<HopValueData> values = new ArrayList<HopValueData>();
			values.add(new HopValueData(String.valueOf(hopValData3.get(i))));
			values.add(new HopValueData(String.valueOf(hopValData2.get(i))));
			values.add(new HopValueData(String.valueOf(hopValData1.get(i))));
			chartdata.add(HopChartUtil.formatValueData(values));
		}

		return chartdata;
	}

	/**
	 * 单系统同期三周或三月 异常情况（线图）
	 * 
	 * @return json格式。
	 * @throws Exception
	 */
	public String singleAppRecentEx() throws Exception {
		Date from1 = null, to1 = null, from2 = null, to2 = null, from3 = null, to3 = null;
		List<String> tks2 = new ArrayList<String>();
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		if (time != null && appId != null) {
			/**
			 * 获取起止时间
			 */
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
				String pprevMon = formatter.format(from3);
				String pprevSun = formatter.format(to3);
				String prevMonday = formatter.format(from2);
				String prevSunday = formatter.format(to2);
				tks2.add(pprevMon + " - " + pprevSun);
				tks2.add(prevMonday + " - " + prevSunday);
				tks2.add(monday + " - " + sunday);

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
				tks2.add(tk3);
				tks2.add(tk2);
				tks2.add(tk1);
			}

			/**
			 * 获取数据
			 */
			List<String> lbs = buildLabels();

			Map<IssueType, Map<String, ShowIssue>> map3 = getReportService()
					.getAppIssue(Long.valueOf(appId), from3, to3);
			Map<IssueType, Map<String, ShowIssue>> map2 = getReportService()
					.getAppIssue(Long.valueOf(appId), from2, to2);
			Map<IssueType, Map<String, ShowIssue>> map1 = getReportService()
					.getAppIssue(Long.valueOf(appId), from1, to1);
			/**
			 * 将map转为直线图数据
			 */
			List<Long> num3 = new ArrayList<Long>();
			List<Long> num2 = new ArrayList<Long>();
			List<Long> num1 = new ArrayList<Long>();
			for (int i = 0; i < 3; i++) { // 遍历三种异常
				IssueType it = IssueType.getIssueType(i + 1);
				Map<String, ShowIssue> m3 = map3.get(it);
				if (m3 == null || m3.size() == 0) {
					num3.add(Long.valueOf(0));
				} else {
					for (ShowIssue m3Issue : m3.values()) {
						num3.add(m3Issue.getNum()); // 获取数量
					}
				}
				Map<String, ShowIssue> m2 = map2.get(it);
				if (m2 == null || m2.size() == 0) {
					num2.add(Long.valueOf(0));
				} else {
					for (ShowIssue m2Issue : m2.values()) {
						num2.add(m2Issue.getNum()); // 获取数量
					}
				}
				Map<String, ShowIssue> m1 = map1.get(it);
				if (m1 == null || m1.size() == 0) {
					num1.add(Long.valueOf(0));
				} else {
					for (ShowIssue m1Issue : m1.values()) {
						num1.add(m1Issue.getNum()); // 获取数量
					}
				}

			}

			/**
			 * 将结果转成前面直线图数据格式
			 */
			List<String> chartdata = buildChart(num1, num2, num3);
			linedata = chartdata.toString();
			labels = lbs;
			ticks = tks2;
			/**
			 * 获取这个系统异常数量前10的Service(除超时的)
			 */
			AppIssueSearcher searcher = new AppIssueSearcher();
			searcher.setFrom(from1); // 本周/本月
			searcher.setTo(to1);
			searcher.setAppId(Long.valueOf(appId));
			pager.setCurrentPage(getPage());
			pager.setPageSize(Long.valueOf(10));
			searcher.setPager(pager);
			pager = getReportService().getAppApiIssueTopTen(searcher);
		}
		return SUCCESS;
	}

	/**
	 * 全业务异常比较（柱状图）
	 * 
	 * @return json格式。
	 * @throws Exception
	 */
	public String allAppEx() throws Exception {
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
			} else { // 本月
				// 本月第一天
				String firstday = dt.getFirstDayOfMonth(pattern);
				// 本月最后一天
				String lastday = dt.getDefaultDay(pattern);
				fromDate = formatter.parse(firstday);
				toDate = formatter.parse(lastday);
			}
			Map<IssueType, Map<String, ShowIssue>> issueList = getReportService()
					.getAppIssue(null, fromDate, toDate);
			/**
			 * 将map转为柱状图数据
			 */
			barLabels = new ArrayList<String>();
			// 只看这三种异常
			barLabels.add("已知异常");
			barLabels.add("未知异常");
			// 所有业务系统
			appLists = getBaseInfoService().getAppLists();
			//List<Long> ids = new ArrayList<Long>(); // 存放appid
			for (int j = 0; j < 2; j++) {
				List<Object> issueTypeData = new ArrayList<Object>();
				for (int i = 0; i < appLists.size(); i++) {
					List<Object> exDataList = new ArrayList<Object>();
					List<Object> ytick = new ArrayList<Object>();
					ytick.add(i);
					ytick.add(appLists.get(i).getAppName());

					IssueType it = IssueType.getIssueType(j + 1);
					Map<String, ShowIssue> m = issueList.get(it);
					if (m == null || m.size() == 0) {
						exDataList.add(0);
					} else {
						ShowIssue si = m.get(appLists.get(i).getAppName());
						if (si == null) {
							exDataList.add(0);
						} else {
							exDataList.add(si.getNum());
						}
					}
					exDataList.add(i);
					issueTypeData.add(exDataList);
				}
				exAppLists.add(issueTypeData);
			}
			// List<String> chartdata = new ArrayList<String>();
			// for (int i = 0; i < 3; i++) { // 遍历三种异常
			// List<HopValueData> oneGroupValue = new ArrayList<HopValueData>();
			// IssueType it = IssueType.getIssueType(i + 1);
			// Map<String, ShowIssue> m = issueList.get(it);
			// for (String tk : tks) {
			// if (m == null || m.size() == 0) {
			// oneGroupValue.add(new HopValueData("0"));
			// } else {
			// ShowIssue si = m.get(tk);
			// if (si == null) {
			// oneGroupValue.add(new HopValueData("0"));
			// } else {
			// oneGroupValue.add(new HopValueData(String
			// .valueOf(si.getNum())));
			// }
			// }
			// }
			// chartdata.add(HopChartUtil.formatValueData(oneGroupValue));
			//
			//
			// }
			// appIds = ids;
			// bardata = chartdata.toString();
			// ticks = tks;
			// labels = lbs;
		}
		return SUCCESS;
	}

	public String execute() throws Exception {
		return SUCCESS;
	}

}
