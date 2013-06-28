package com.haier.openplatform.console.issue.webapp.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.haier.openplatform.console.issue.domain.AppLists;
import com.haier.openplatform.console.issue.domain.HopEmail;
import com.haier.openplatform.console.issue.module.HopEmailModel;
import com.haier.openplatform.console.issue.util.IssueBuilder;
import com.haier.openplatform.util.Pager;

/**
 * @author Vilight_Wu 邮件监控
 */
public class EmailInquiryAction extends BaseIssueAction {
	private static final long serialVersionUID = -8745278923263590863L;

	/**
	 * email分页对象
	 */
	private Pager<HopEmailModel> pager = new Pager<HopEmailModel>();

	private IssueBuilder issueBuilder;

	/**
	 * 查询起止时间
	 */
	private String from;
	private String to;

	/**
	 * 系统名
	 */
	private String appName;

	/**
	 * 应用系统列表
	 */
	private List<AppLists> appLists = new ArrayList<AppLists>();

	public void setIssueBuilder(IssueBuilder issueBuilder) {
		this.issueBuilder = issueBuilder;
	}

	public List<AppLists> getAppLists() {
		return appLists;
	}

	public void setAppLists(List<AppLists> appLists) {
		this.appLists = appLists;
	}

	public Pager<HopEmailModel> getPager() {
		return pager;
	}

	public void setPager(Pager<HopEmailModel> pager) {
		this.pager = pager;
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

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * 查看指定时间段邮件发送情况
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchEmails() throws Exception {
		if (appName != null && from != null && to != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date fromDate = sdf.parse(from);
			Date toDate = sdf.parse(to);
			Calendar cal = Calendar.getInstance();
			cal.setTime(toDate);
			cal.add(Calendar.DATE, 1);
			toDate = cal.getTime();
			pager.setCurrentPage(getPage());
			pager.setPageSize(getRows());
			if (StringUtils.isNotEmpty(getSidx())) {
				pager.setOrderProperty(getSidx());
				pager.setOrder(StringUtils.defaultIfEmpty(getSord(), "asc"));
			}
			Pager<HopEmail> emailPager = issueBuilder.buildPager(pager);
			
			pager = getAppMonitorService().getHopEmail(appName, fromDate,
					toDate, emailPager);
			this.setTotal(pager.getTotalPages());
			this.setRecords(pager.getTotalRecords());
		}
		return SUCCESS;
	}

	public String execute() throws Exception {
		// 获取应用系统列表
		List<AppLists> list = getBaseInfoService().getAppLists();
		AppLists ap = new AppLists();
		ap.setAppName("ALL");
		appLists.add(ap);
		appLists.addAll(list);
		return SUCCESS;
	}

}
