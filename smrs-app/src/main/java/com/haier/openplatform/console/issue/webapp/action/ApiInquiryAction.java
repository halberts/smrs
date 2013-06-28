package com.haier.openplatform.console.issue.webapp.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.haier.openplatform.console.issue.domain.AppLists;
import com.haier.openplatform.console.issue.module.ApiSearcher;
import com.haier.openplatform.console.issue.module.ServiceApi;
import com.haier.openplatform.console.issue.util.DateUtil;
import com.haier.openplatform.util.Pager;

/**
 * @author Vilight_Wu API查询
 */
public class ApiInquiryAction extends BaseIssueAction {

	private static final long serialVersionUID = -5729977366291677317L;

	/**
	 * service分页对象
	 */
	private Pager<ServiceApi> pager = new Pager<ServiceApi>();

	/**
	 * 应用系统列表
	 */
	private List<AppLists> appLists = new ArrayList<AppLists>();

	/**
	 * 应用系统id
	 */
	private String appId;

	/**
	 * 查询时间（tab-1）
	 */
	private String time;

	/**
	 * 查询起止时间（tab-2）
	 */

	private String to;

	private String from;

	/**
	 * service名
	 */
	private String serviceName;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Pager<ServiceApi> getPager() {
		return pager;
	}

	public void setPager(Pager<ServiceApi> pager) {
		this.pager = pager;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

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

	/**
	 * 获取API调用频次信息
	 * 
	 * @return json数据，展示前台表格
	 * @throws Exception
	 */
	public String apiCallInquiry() throws Exception {
		Date fromDate = null, toDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (appId != null && time != null) { // tab-1
			/**
			 * 将tab-1中选择的时间转成相应的from, to
			 */
			DateUtil dt = new DateUtil();
			if ("week".equals(time)) { // 本周
				// 本周一
				String monday = dt.getMondayOFWeek("yyyy-MM-dd");
				// 本周日
				String sunday = dt.getCurrentWeekday("yyyy-MM-dd");
				fromDate = formatter.parse(monday);
				toDate = formatter.parse(sunday);
			} else { // 本本月
				// 本月第一天
				String firstday = dt.getFirstDayOfMonth("yyyy-MM-dd");
				// 本月最后一天
				String lastday = dt.getDefaultDay("yyyy-MM-dd");
				fromDate = formatter.parse(firstday);
				toDate = formatter.parse(lastday);
			}
		} else if (appId != null && from != null && to != null) { // tab-2
			fromDate = formatter.parse(from);
			toDate = formatter.parse(to);
			Calendar cal = Calendar.getInstance();
			cal.setTime(toDate);
			cal.add(Calendar.DATE, 1);
			toDate = cal.getTime();
		}

		/**
		 * 设置返回的查询日期
		 */
		from = formatter.format(fromDate);
		to = formatter.format(toDate);
		pager.setCurrentPage(getPage());
		pager.setPageSize(getRows());
		ApiSearcher apiSearcher = new ApiSearcher(fromDate, toDate, pager);
		apiSearcher.setAppId(Long.valueOf(appId));
		apiSearcher.setServiceName(serviceName);
		pager = getAppMonitorService().getServiceApiCall(apiSearcher);
		this.setTotal(pager.getTotalPages());
		this.setRecords(pager.getTotalRecords());

		/**
		 * 设置平均消耗时间
		 */
		List<ServiceApi> apis = pager.getRecords();
		for (ServiceApi api : apis) {
			api.setAverageTime(((double) api.getExecuteTime() / api.getCallNum()));
		}
		return SUCCESS;
	}

	public String execute() throws Exception {
		// 获取应用系统列表
		appLists = getBaseInfoService().getAppLists();
		return SUCCESS;
	}
}
