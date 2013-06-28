package com.haier.openplatform.console.issue.webapp.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.haier.openplatform.console.issue.domain.AppLists;
import com.haier.openplatform.console.issue.domain.BusinessService;
import com.haier.openplatform.console.issue.util.DateUtil;
import com.haier.openplatform.util.Pager;

/**
 * @author Vilight_Wu 查看系统响应信息
 */
public class LevelSearchAction extends BaseIssueAction {

	private static final long serialVersionUID = 1404426259191626908L;

	/**
	 * service分页对象
	 */
	private Pager<BusinessService> pager = new Pager<BusinessService>();

	/**
	 * 应用系统列表
	 */
	private List<AppLists> appLists = new ArrayList<AppLists>();

	/**
	 * 当天，本周，本月，service响应数量
	 */
	private List<Long> serivceNum = new ArrayList<Long>();

	/**
	 * 响应级别汇总结果
	 */
	private TreeMap<String, Long> levelSummary;

	/**
	 * 应用系统id
	 */
	private String appId;

	/**
	 * 当天响应数量
	 */
	private Long todayCount;

	/**
	 * 本周响应数量
	 */
	private Long weekCount;

	/**
	 * 本月响应数量
	 */
	private Long monthCount;

	public Pager<BusinessService> getPager() {
		return pager;
	}

	public void setPager(Pager<BusinessService> pager) {
		this.pager = pager;
	}

	public Long getTodayCount() {
		return todayCount;
	}

	public void setTodayCount(Long todayCount) {
		this.todayCount = todayCount;
	}

	public Long getWeekCount() {
		return weekCount;
	}

	public void setWeekCount(Long weekCount) {
		this.weekCount = weekCount;
	}

	public Long getMonthCount() {
		return monthCount;
	}

	public void setMonthCount(Long monthCount) {
		this.monthCount = monthCount;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public List<Long> getSerivceNum() {
		return serivceNum;
	}

	public void setSerivceNum(List<Long> serivceNum) {
		this.serivceNum = serivceNum;
	}

	public TreeMap<String, Long> getLevelSummary() {
		return levelSummary;
	}

	public void setLevelSummary(TreeMap<String, Long> levelSummary) {
		this.levelSummary = levelSummary;
	}

	public List<AppLists> getAppLists() {
		return appLists;
	}

	public void setAppLists(List<AppLists> appLists) {
		this.appLists = appLists;
	}


	/**
	 * 获取level汇总结果和当天/本周/本月响应数量，及详细API响应级别列表
	 * 
	 * @return json格式，填充前台表格
	 * @throws Exception
	 */
	public String serviceLevelDetail() throws Exception {
		if (appId != null) {
			// 获取level汇总结果
			Map<String, Long> svcLevel = getAppMonitorService().getServiceLevel(Long.valueOf(appId));
			levelSummary = new TreeMap<String, Long>(svcLevel);
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			/**
			 * 当天/本周/本月响应数量
			 */
			DateUtil dt = new DateUtil();
			// 当天
			String today = dt.getNowTime(pattern);
			Date from = sdf.parse(today);
			Calendar cal = Calendar.getInstance();
			cal.setTime(from);
			cal.add(Calendar.DATE, 1); // 下一天
			Date to = cal.getTime();
			todayCount = getAppMonitorService().getServiceOvertimeNum(Long.valueOf(appId), from, to);
			// 本周一
			String monday = dt.getMondayOFWeek(pattern);
			// 本周日
			String sunday = dt.getCurrentWeekday(pattern);
			Date weekFrom = sdf.parse(monday);
			Date weekTo = sdf.parse(sunday);
			weekCount = getAppMonitorService().getServiceOvertimeNum(Long.valueOf(appId), weekFrom, weekTo);
			// 本月第一天
			String firstday = dt.getFirstDayOfMonth(pattern);
			// 本月最后一天
			String lastday = dt.getDefaultDay(pattern);
			Date monthFrom = sdf.parse(firstday);
			Date monthTo = sdf.parse(lastday);
			monthCount = getAppMonitorService().getServiceOvertimeNum(Long.valueOf(appId), monthFrom, monthTo);
			// 获取详细api响应级别列表
			pager.setCurrentPage(getPage());
			pager.setPageSize(getRows());
			if (StringUtils.isNotEmpty(getSidx())) {
				pager.setOrderProperty(getSidx());
				pager.setOrder(StringUtils.defaultIfEmpty(getSord(), "asc"));
			}
			pager = getBaseInfoService().getBusinessService(Long.valueOf(appId), "", pager);
			this.setTotal(pager.getTotalPages());
			this.setRecords(pager.getTotalRecords());
		}
		// 获取应用系统列表
		appLists = getBaseInfoService().getAppLists();
		return SUCCESS;
	}

	@Override
	public String execute() throws Exception {
		// 获取应用系统列表
		appLists = getBaseInfoService().getAppLists();
		return SUCCESS;
	}

}
