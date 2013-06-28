package com.haier.openplatform.console.issue.webapp.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.haier.openplatform.console.issue.domain.StatisticsAction;
import com.haier.openplatform.console.issue.domain.enu.StatisticsTimeTypeEnum;
import com.haier.openplatform.console.issue.util.DateUtil;

/**
 * 获取近三周或者三月Action的调用情况
 * @author WangJian
 *
 */
public class GetThreeWeekOrYearAverageAction extends BaseIssueAction{
	private static final long serialVersionUID = -1543866336722186092L;
	private String time;
	private Long appId;
	private List<String> ticks;
	private String bardatas;
	private String linedatas;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public List<String> getTicks() {
		return ticks;
	}
	public void setTicks(List<String> ticks) {
		this.ticks = ticks;
	}
	public String getBardatas() {
		return bardatas;
	}
	public void setBardatas(String bardatas) {
		this.bardatas = bardatas;
	}
	public String getLinedatas() {
		return linedatas;
	}
	public void setLinedatas(String linedatas) {
		this.linedatas = linedatas;
	}
	@Override
	public String execute() throws Exception {
		Date from1 = null, to1 = null;
		Date from2 = null, to2 = null;
		Date from3 = null, to3 = null;
		List<String> tks = new ArrayList<String>();
		List<HopPointData> callNum = new ArrayList<HopPointData>();
		List<HopPointData> avgTime = new ArrayList<HopPointData>();
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		
		List<StatisticsAction> curList = null,secList = null,thrList = null;
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
				
				int weekNum = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
				//取本周数据
				curList = getProfileService().queryActionAvgTime(appId, StatisticsTimeTypeEnum.WEEK.getType(), weekNum);
				//取上周数据
				secList = getProfileService().queryActionAvgTime(appId, StatisticsTimeTypeEnum.WEEK.getType(), weekNum-1);
				//取上上周数据
				thrList = getProfileService().queryActionAvgTime(appId, StatisticsTimeTypeEnum.WEEK.getType(), weekNum-2);
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

				int monthNum = Calendar.getInstance().get(Calendar.MONTH) + 1;
				//取本周数据
				curList = getProfileService().queryActionAvgTime(appId, StatisticsTimeTypeEnum.MONTH.getType(), monthNum);
				//取上周数据
				secList = getProfileService().queryActionAvgTime(appId, StatisticsTimeTypeEnum.MONTH.getType(), monthNum-1);
				//取上上周数据
				thrList = getProfileService().queryActionAvgTime(appId, StatisticsTimeTypeEnum.MONTH.getType(), monthNum-2);
			}

			/**
			 * 获取上上周/上上月数据
			 */
			if (thrList != null && !thrList.isEmpty()) {
				StatisticsAction app = thrList.get(0); // 取第一条数据（也只有一条）
				long num = app.getCallNum();
				callNum.add(new HopPointData("1", String.valueOf(num))); // 第1组数据
				avgTime.add(new HopPointData("1", String.valueOf((double) app.getAverageTime())));
			} else {
				callNum.add(new HopPointData("1", String.valueOf(0)));
				avgTime.add(new HopPointData("1", String.valueOf(0)));
			}

			/**
			 * 获取上周/上月数据
			 */
			if (secList != null && !secList.isEmpty()) {
				StatisticsAction app = secList.get(0); // 取第一条数据（也只有一条）
				long num = app.getCallNum();
				callNum.add(new HopPointData("2", String.valueOf(num))); // 第2组数据
				avgTime.add(new HopPointData("2", String.valueOf((double) app.getAverageTime())));
			} else {
				callNum.add(new HopPointData("2", String.valueOf(0)));
				avgTime.add(new HopPointData("2", String.valueOf(0)));
			}

			/**
			 * 获取本周/本月数据
			 */
			if (curList != null && !curList.isEmpty()) {
				StatisticsAction app = curList.get(0); // 取第一条数据（也只有一条）
				long num = app.getCallNum();
				callNum.add(new HopPointData("3", String.valueOf(num))); // 第3组数据
				avgTime.add(new HopPointData("3", String.valueOf((double) app.getAverageTime())));
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
}
