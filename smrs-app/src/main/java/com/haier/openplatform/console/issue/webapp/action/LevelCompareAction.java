package com.haier.openplatform.console.issue.webapp.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.haier.openplatform.console.issue.domain.AppLists;
import com.haier.openplatform.console.issue.domain.ServiceTimeoutLv;
import com.haier.openplatform.console.issue.module.ApiCallGroupByLevel;
import com.haier.openplatform.console.issue.module.TimeoutLevel;
import com.haier.openplatform.console.issue.util.DateUtil;

/**
 * @author Vilight_Wu 全业务系统响应级别横向比较
 */
public class LevelCompareAction extends BaseIssueAction {

	private static final long serialVersionUID = 5214135191413220881L;
	/**
	 * 应用系统列表
	 */
	private List<AppLists> appLists = new ArrayList<AppLists>();

	/**
	 * 响应级别列表
	 */
	private List<TimeoutLevel> timeoutLevelList = new ArrayList<TimeoutLevel>();

	/**
	 * 各饼图数据
	 */
	private List<String> pieDatas;

	/**
	 * 各饼图名字
	 */
	private List<String> pieNames;

	/**
	 * 本周各业务系统超时数量
	 */
	private List<Long> weekNum;

	/**
	 * 本月各业务系统超时数量
	 */
	private List<Long> monthNum;

	/**
	 * 所有app的饼图数据
	 */
	private Map<String, String> pies;

	public List<String> getPieDatas() {
		return pieDatas;
	}

	public void setPieDatas(List<String> pieDatas) {
		this.pieDatas = pieDatas;
	}

	public List<String> getPieNames() {
		return pieNames;
	}

	public void setPieNames(List<String> pieNames) {
		this.pieNames = pieNames;
	}

	public List<Long> getWeekNum() {
		return weekNum;
	}

	public void setWeekNum(List<Long> weekNum) {
		this.weekNum = weekNum;
	}

	public List<Long> getMonthNum() {
		return monthNum;
	}

	public void setMonthNum(List<Long> monthNum) {
		this.monthNum = monthNum;
	}

	public Map<String, String> getPies() {
		return pies;
	}

	public void setPies(Map<String, String> pies) {
		this.pies = pies;
	}

	public List<TimeoutLevel> getTimeoutLevelList() {
		return timeoutLevelList;
	}

	public void setTimeoutLevelList(List<TimeoutLevel> timeoutLevelList) {
		this.timeoutLevelList = timeoutLevelList;
	}

	public List<AppLists> getAppLists() {
		return appLists;
	}

	public void setAppLists(List<AppLists> appLists) {
		this.appLists = appLists;
	}

	@Override
	public String execute() throws Exception {
		// 获取level列表
		List<ServiceTimeoutLv> serviceTimeoutLvList = getBaseInfoService()
				.getServiceTimeoutLv();
		for (ServiceTimeoutLv serviceTimeoutLv : serviceTimeoutLvList) {
			TimeoutLevel tl = new TimeoutLevel();
			tl.setLvName(serviceTimeoutLv.getId());
			long otMsec = serviceTimeoutLv.getOvertimeMsec();
			// 转换单位
			if (otMsec < 1000) {
				tl.setOtTime(otMsec + "ms");
			} else {
				tl.setOtTime(otMsec / 1000 + "s");
			}
			timeoutLevelList.add(tl);
		}

		/**
		 * 获取全业务所有Level数据进行比较
		 */
		List<ApiCallGroupByLevel> apiLevels = getReportService()
				.getAllApiOvertimeGroupByLevel();

		/**
		 * 将数据库结果转成如下格式： OMS -> LV1 - 200 -> LV2 - 299 -> LV3 - 321
		 */
		TreeMap<String, TreeMap<String, Long>> rsMap = new TreeMap<String, TreeMap<String, Long>>();
		List<Long> appIds = new ArrayList<Long>(); // 存放各appid
		for (ApiCallGroupByLevel api : apiLevels) {
			String appName = api.getAppName();
			TreeMap<String, Long> tmpMap = rsMap.get(appName);
			if (tmpMap == null) {
				tmpMap = new TreeMap<String, Long>();
				rsMap.put(appName, tmpMap);
				appIds.add(api.getAppId()); // 保证appid与appname顺序对应。
			}
			tmpMap.put(api.getLevel(), api.getNum());
		}
		
		/**
		 * 将rsMap数据转成前台图表数据格式（饼图）
		 */
		List<String> names = new ArrayList<String>();
		List<String> datas = new ArrayList<String>();
		Iterator<TreeMap<String, Long>> rsVals = rsMap.values().iterator();
		for (String appnm : rsMap.keySet()) { // 遍历每个app
			List<HopPointData> points = new ArrayList<HopPointData>();
			Map<String, Long> lvMap = rsVals.next(); // 获取这个app对应的子Map
			Iterator<Long> lvVals = lvMap.values().iterator();
			for (String lv : lvMap.keySet()) { // 遍历所有level
				Long num = lvVals.next();
				points.add(new HopPointData("'" + lv + ": " + num + "'", String
						.valueOf(num)));
			}
			names.add("'" + appnm + "'");
			datas.add(HopChartUtil.formatPointData(points));
		}
		pieDatas = datas;
		pieNames = names;

		/**
		 * 获取每个app本周，本月超时数量
		 */
		List<Long> weeks = new ArrayList<Long>();
		List<Long> months = new ArrayList<Long>();
		DateUtil dt = new DateUtil();
		int size = appIds.size();
		for (int i = 0; i < size; i++) { // 遍历每个app
			Long appId = appIds.get(i);
			Date weekFrom = null, weekTo = null, monthFrom = null, monthTo = null;
			String pattern = "yyyy-MM-dd";
			/**
			 * 本周超时数量
			 */
			// 本周一
			String monday = dt.getMondayOFWeek(pattern);
			// 本周日
			String sunday = dt.getCurrentWeekday(pattern);
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			weekFrom = sdf.parse(monday);
			weekTo = sdf.parse(sunday);
			Long week = getAppMonitorService().getServiceOvertimeNum(appId,
					weekFrom, weekTo);
			weeks.add(week);

			/**
			 * 本月超时数量
			 */
			// 本月第一天
			String firstday = dt.getFirstDayOfMonth(pattern);
			// 本月最后一天
			String lastday = dt.getDefaultDay(pattern);
			monthFrom = sdf.parse(firstday);
			monthTo = sdf.parse(lastday);
			Long month = getAppMonitorService().getServiceOvertimeNum(appId,
					monthFrom, monthTo);
			months.add(month);
		}
		weekNum = weeks;
		monthNum = months;
		return SUCCESS;
	}
}
