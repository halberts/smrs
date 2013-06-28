package com.haier.openplatform.console.issue.webapp.action;

import com.haier.openplatform.console.issue.service.AppMonitorService;
import com.haier.openplatform.console.issue.service.BaseInfoService;
import com.haier.openplatform.console.issue.service.ProfileService;
import com.haier.openplatform.console.issue.service.ReportService;
import com.smrs.webapp.action.BaseConsoleAction;

/**
 * @author Vilight_Wu 所有监控action的父类
 */
public class BaseIssueAction extends BaseConsoleAction {

	private static final long serialVersionUID = 7707655100286443793L;

	/**
	 * 分页参数
	 */
	private Long rows = 0L;
	private Long page = 0L;
	private Long total = 0L;
	private Long records = 0L;
	private String sord;
	private String sidx;
	
	private ProfileService profileService;
	
	private BaseInfoService baseInfoService;

	private AppMonitorService appMonitorService;

	private ReportService reportService;

	public BaseInfoService getBaseInfoService() {
		return baseInfoService;
	}

	public ProfileService getProfileService() {
		return profileService;
	}

	public void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}

	public void setBaseInfoService(BaseInfoService baseInfoService) {
		this.baseInfoService = baseInfoService;
	}

	public AppMonitorService getAppMonitorService() {
		return appMonitorService;
	}

	public void setAppMonitorService(AppMonitorService appMonitorService) {
		this.appMonitorService = appMonitorService;
	}

	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public Long getRows() {
		return rows;
	}

	public void setRows(Long rows) {
		this.rows = rows;
	}

	public Long getPage() {
		return page;
	}

	public void setPage(Long page) {
		this.page = page;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getRecords() {
		return records;
	}

	public void setRecords(Long records) {
		this.records = records;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

}
