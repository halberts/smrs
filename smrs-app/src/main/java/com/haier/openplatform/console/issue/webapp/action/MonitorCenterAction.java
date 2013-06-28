package com.haier.openplatform.console.issue.webapp.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.haier.openplatform.console.issue.domain.AppLists;
import com.haier.openplatform.console.issue.module.Issue;
import com.haier.openplatform.console.issue.module.IssueDetail;
import com.haier.openplatform.console.issue.module.IssueSearcher;
import com.haier.openplatform.console.issue.module.ServiceApi;
import com.haier.openplatform.console.issue.util.DateUtil;
import com.haier.openplatform.util.Pager;

/**
 * @author Vilight_Wu 监控中心（首页）
 */
public class MonitorCenterAction extends BaseIssueAction {

	private static final long serialVersionUID = 2986568372732421489L;

	/**
	 * service分页对象
	 */
	private Pager<Issue> pager = new Pager<Issue>();

	private Pager<ServiceApi> apiPager = new Pager<ServiceApi>();

	/**
	 * 换异常类型过滤
	 */
	private String extype;

	/**
	 * 应用系统列表
	 */
	private List<AppLists> appLists = new ArrayList<AppLists>();

	/**
	 * 异常详细信息
	 */
	private IssueDetail issueDetail;

	/**
	 * 异常id
	 */
	private String issueId;

	/**
	 * 应用系统id
	 */
	private String appId;

	private String from;

	/**
	 * service名
	 */
	private String serviceName;

	public String getExtype() {
		return extype;
	}

	public void setExtype(String extype) {
		this.extype = extype;
	}

	public Pager<ServiceApi> getApiPager() {
		return apiPager;
	}

	public void setApiPager(Pager<ServiceApi> apiPager) {
		this.apiPager = apiPager;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}


	public Pager<Issue> getPager() {
		return pager;
	}

	public void setPager(Pager<Issue> pager) {
		this.pager = pager;
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

	public IssueDetail getIssueDetail() {
		return issueDetail;
	}

	public void setIssueDetail(IssueDetail issueDetail) {
		this.issueDetail = issueDetail;
	}

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public List<AppLists> getAppLists() {
		return appLists;
	}

	public void setAppLists(List<AppLists> appLists) {
		this.appLists = appLists;
	}

	/**
	 * 实时监控serviceApi情况
	 * 
	 * @return
	 * @throws Exception
	 */
	public String realTimeApi() throws Exception {
		if (appId != null) {
			// serviceApi
			apiPager.setPageSize(Long.valueOf(25)); // 显示25条数据
			apiPager = getAppMonitorService().getLastestExecApi(Long.valueOf(appId), apiPager);
			/**
			 * 设置平均消耗时间
			 */
			List<ServiceApi> apis = apiPager.getRecords();
			for (ServiceApi api : apis) {
				api.setAverageTime(((double) api.getExecuteTime() / api.getCallNum()));
			}
		}
		return SUCCESS;
	}

	/**
	 * 实时监控异常情况
	 * 
	 * @return
	 * @throws Exception
	 */
	public String realTimeEx() throws Exception {
		if (appId != null) {
			// 异常
			pager.setPageSize(Long.valueOf(25)); // 显示25条数据
			List<String> excludeList = new ArrayList<String>();
			if (extype != null) {
				for (int i = 1; i <= 3; i++) {
					if (!extype.contains(String.valueOf(i))) {
						excludeList.add(String.valueOf(i));
					}
				}
			}
			for (int i = excludeList.size(); i < 3; i++) {
				excludeList.add(null);
			}
			pager = getAppMonitorService().getLastestIssue(Long.valueOf(appId), pager, excludeList.get(0),
					excludeList.get(1), excludeList.get(2));
		}
		return SUCCESS;
	}

	/**
	 * 查询本月service.api所有异常。（除debug）
	 * 
	 * @return
	 * @throws Exception
	 */
	public String serviceInquiryIndex() throws Exception {
		Date fromDate = null, toDate = null;
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		DateUtil dt = new DateUtil();
		if (appId != null && "1m".equals(from) && serviceName != null) { // tab-2
			// 本月第一天
			String firstday = dt.getFirstDayOfMonth(pattern);
			// 本月最后一天
			String lastday = dt.getDefaultDay(pattern);
			fromDate = sdf.parse(firstday);
			toDate = sdf.parse(lastday);
			pager.setCurrentPage(getPage());
			pager.setPageSize(getRows());
			IssueSearcher issueSearcher = new IssueSearcher(fromDate, toDate, pager);
			issueSearcher.setServiceName(serviceName);
			issueSearcher.setAppId(Long.valueOf(appId));
			pager = getAppMonitorService().getIssueByPage(issueSearcher);
			this.setTotal(pager.getTotalPages());
			this.setRecords(pager.getTotalRecords());
		}
		return SUCCESS;
	}

	/**
	 * 根据监控id,获取异常详细信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String issueDetailIndex() throws Exception {
		if (issueId != null) {
			issueDetail = getAppMonitorService().getIssueDetail(Long.valueOf(issueId));
		}
		return SUCCESS;
	}

	@Override
	public String execute() throws Exception {
		// 获取应用系统列表
		appLists = getBaseInfoService().getAppLists();
		return SUCCESS;
	}

}
