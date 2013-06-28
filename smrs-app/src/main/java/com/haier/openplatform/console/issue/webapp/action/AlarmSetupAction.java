package com.haier.openplatform.console.issue.webapp.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.haier.openplatform.console.issue.domain.AppLists;
import com.haier.openplatform.console.issue.domain.BusinessService;
import com.haier.openplatform.util.Pager;

/**
 * @author Vilight_Wu 设定告警阀值
 */
public class AlarmSetupAction extends BaseIssueAction {

	private static final long serialVersionUID = -2608229911492950672L;

	/**
	 * service分页对象
	 */
	private Pager<BusinessService> pager = new Pager<BusinessService>();

	/**
	 * 应用系统列表
	 */
	private List<AppLists> appLists = new ArrayList<AppLists>();

	/**
	 * 阀值上限
	 */
	private String alertMax;

	/**
	 * service名
	 */
	private String serviceName;

	/**
	 * 要设置的service id
	 */
	private String serviceIds;

	/**
	 * 应用系统id
	 */
	private String appId;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getServiceIds() {
		return serviceIds;
	}

	public void setServiceIds(String serviceIds) {
		this.serviceIds = serviceIds;
	}

	public Pager<BusinessService> getPager() {
		return pager;
	}

	public void setPager(Pager<BusinessService> pager) {
		this.pager = pager;
	}

	public String getAlertMax() {
		return alertMax;
	}

	public void setAlertMax(String alertMax) {
		this.alertMax = alertMax;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public List<AppLists> getAppLists() {
		return appLists;
	}

	public void setAppLists(List<AppLists> appLists) {
		this.appLists = appLists;
	}

	/**
	 * 设置新阀值
	 * 
	 * @return
	 * @throws Exception
	 */
	public String saveServiceAlarm() throws Exception {
		if (alertMax != null && serviceIds != null) {
			// 将页面的service id转成long数组
			List<Long> svcIds = new ArrayList<Long>();
			String[] ids = serviceIds.split(",");
			for (String id : ids) {
				svcIds.add(Long.valueOf(id));
			}
			getBaseInfoService().saveServiceAlert(svcIds, Long.valueOf(alertMax));
		}
		return SUCCESS;
	}

	/**
	 * 获取service阀值列表
	 * 
	 * @return json数据，填充前台表格
	 * @throws Exception
	 */
	public String serviceAlarm() throws Exception {
		if (serviceName != null) {
			pager.setCurrentPage(getPage());
			pager.setPageSize(getRows());
			if (StringUtils.isNotEmpty(getSidx())) {
				pager.setOrderProperty(getSidx());
				pager.setOrder(StringUtils.defaultIfEmpty(getSord(), "asc"));
			}
			pager = getBaseInfoService().getBusinessService(Long.valueOf(appId), serviceName, pager);
			this.setTotal(pager.getTotalPages());
			this.setRecords(pager.getTotalRecords());
		}
		return SUCCESS;
	}

	public String execute() throws Exception {
		// 获取应用系统列表
		appLists = getBaseInfoService().getAppLists();
		return SUCCESS;
	}

}
