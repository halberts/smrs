package com.haier.openplatform.console.issue.webapp.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.haier.openplatform.console.issue.domain.AppLists;
import com.haier.openplatform.console.issue.module.Issue;
import com.haier.openplatform.console.issue.module.IssueDetail;
import com.haier.openplatform.console.issue.module.Searcher;
import com.haier.openplatform.console.issue.util.DateUtil;
import com.haier.openplatform.console.issue.util.MonitorUtil;
import com.haier.openplatform.util.Pager;

/**
 * @author Vilight_Wu 超时查询
 */
public class OvertimeInquiryAction extends BaseIssueAction {

	private static final long serialVersionUID = 6673305436295827285L;

	/**
	 * service分页对象
	 */
	private Pager<Issue> pager = new Pager<Issue>();

	/**
	 * 应用系统列表
	 */
	private List<AppLists> appLists = new ArrayList<AppLists>();

	/**
	 * 应用系统id
	 */
	private String appId;

	/**
	 * service名
	 */
	private String serviceName;

	/**
	 * 查询时间（tab-1）
	 */
	private String time;

	/**
	 * 查询起止时间（tab-2）
	 */
	private String from;

	private String to;

	/**
	 * 异常详细信息
	 */
	private IssueDetail issueDetail;

	/**
	 * 异常id
	 */
	private String issueId;

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public IssueDetail getIssueDetail() {
		return issueDetail;
	}

	public void setIssueDetail(IssueDetail issueDetail) {
		this.issueDetail = issueDetail;
	}

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


	public Pager<Issue> getPager() {
		return pager;
	}

	public void setPager(Pager<Issue> pager) {
		this.pager = pager;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public List<AppLists> getAppLists() {
		return appLists;
	}

	public void setAppLists(List<AppLists> appLists) {
		this.appLists = appLists;
	}

	/**
	 * 获取某个异常的详细信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String issueDetailInfo() {
		if (issueId != null) {
			issueDetail = getAppMonitorService().getIssueDetail(Long.valueOf(issueId));
		}
		return SUCCESS;
	}

	/**
	 * 获取某应用系统，某时间段内超时前10
	 * 
	 * @return json格式，填充前台表格
	 * @throws Exception
	 */
	public String overtimeTopTen() throws Exception {
		Date fromDate = null, toDate = null;
		DateUtil dt = new DateUtil();
		String fmt = "yyyy-MM-dd";
		String fmt2 = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(fmt); // 以天为单位的formatter
		SimpleDateFormat sdf2 = new SimpleDateFormat(fmt2); // 以小时为单位的formatter
		Calendar cal = Calendar.getInstance();
		if ("3m".equals(from)) { // tab-3
			pager.setPageSize(getRows()); // 有分页
		} else {
			pager.setPageSize(Long.valueOf(10)); // 设置为一页显示 10条记录
		}
		if (appId != null && time != null) { // tab-1
			/**
			 * 将tab-1中选择的时间转成相应的from, to
			 */
			// 获取当前时间
			String now = dt.getNowTime("yyyy-MM-ddHHmm");
			String hour = now.substring(10, 12);
			String minute = now.substring(12, 13);
			String today = dt.getNowTime(fmt);
			// 根据time，确定from, to
			if ("2h".equals(time)) {
				fromDate = MonitorUtil.getDateFrom(today, hour, minute);
				toDate = MonitorUtil.getDateTo(today, hour, minute);
				cal.setTime(fromDate);
				cal.add(Calendar.HOUR, -2);
				fromDate = cal.getTime();
				/**
				 * 设置返回的查询时间
				 */
				from = sdf2.format(fromDate);
				to = sdf2.format(toDate);
			} else if ("6h".equals(time)) {
				fromDate = MonitorUtil.getDateFrom(today, hour, minute);
				toDate = MonitorUtil.getDateTo(today, hour, minute);
				cal.setTime(fromDate);
				cal.add(Calendar.HOUR, -6);
				fromDate = cal.getTime();
				/**
				 * 设置返回的查询时间
				 */
				from = sdf2.format(fromDate);
				to = sdf2.format(toDate);
			} else if ("12h".equals(time)) {
				fromDate = MonitorUtil.getDateFrom(today, hour, minute);
				toDate = MonitorUtil.getDateTo(today, hour, minute);
				cal.setTime(fromDate);
				cal.add(Calendar.HOUR, -12);
				fromDate = cal.getTime();
				/**
				 * 设置返回的查询时间
				 */
				from = sdf2.format(fromDate);
				to = sdf2.format(toDate);
			} else if ("1d".equals(time)) { // 当天
				fromDate = sdf.parse(today); // 起始为今天
				cal.setTime(fromDate);
				cal.add(Calendar.DATE, 1);
				toDate = cal.getTime(); // 结止为明天
				/**
				 * 设置返回的查询时间
				 */
				from = sdf.format(fromDate);
				to = sdf.format(toDate);
			} else if ("3d".equals(time)) { // 近3天
				toDate = sdf.parse(today);// 结止为今天
				cal.setTime(toDate);
				cal.add(Calendar.DATE, -3);
				fromDate = cal.getTime(); // 起始为3天前
				/**
				 * 设置返回的查询时间
				 */
				from = sdf.format(fromDate);
				to = sdf.format(toDate);
			} else if ("week".equals(time)) { // 本周
				// 本周一
				String monday = dt.getMondayOFWeek(fmt);
				// 本周日
				String sunday = dt.getCurrentWeekday(fmt);
				fromDate = sdf.parse(monday);
				toDate = sdf.parse(sunday);
				/**
				 * 设置返回的查询时间
				 */
				from = sdf.format(fromDate);
				to = sdf.format(toDate);
			} else { // 本月
				// 本月第一天
				String firstday = dt.getFirstDayOfMonth(fmt);
				// 本月最后一天
				String lastday = dt.getDefaultDay(fmt);
				fromDate = sdf.parse(firstday);
				toDate = sdf.parse(lastday);
				/**
				 * 设置返回的查询时间
				 */
				from = sdf.format(fromDate);
				to = sdf.format(toDate);
			}
		} else if (appId != null && from != null && to != null) { // tab-2
			fromDate = sdf.parse(from);
			toDate = sdf.parse(to);
			/**
			 * 设置返回的查询时间
			 */
			from = sdf.format(fromDate);
			to = sdf.format(toDate);
			cal.setTime(toDate);
			cal.add(Calendar.DATE, 1);
			toDate = cal.getTime();
		} else if (appId != null && "3m".equals(from) && serviceName != null) { // tab-3
			// 设置近3个月起止时间
			toDate = new Date();
			cal.setTime(toDate);
			cal.add(Calendar.MONTH, -3);
			fromDate = cal.getTime();
			/**
			 * 设置返回的查询时间
			 */
			from = sdf.format(fromDate);
			to = sdf.format(toDate);
		}
		pager.setCurrentPage(getPage());
		Searcher searcher = new Searcher(fromDate, toDate, pager);
		searcher.setServiceName(serviceName);
		searcher.setAppId(Long.valueOf(appId));
		pager = getAppMonitorService().getOvertimeTopTen(searcher);
		this.setTotal(pager.getTotalPages());
		this.setRecords(pager.getTotalRecords());
		return SUCCESS;
	}

	public String execute() throws Exception {
		// 获取应用系统列表
		appLists = getBaseInfoService().getAppLists();
		return SUCCESS;
	}
}
