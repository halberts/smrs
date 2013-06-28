package com.haier.openplatform.console.issue.webapp.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.haier.openplatform.console.issue.domain.AppLists;
import com.haier.openplatform.console.issue.domain.BusinessService;
import com.haier.openplatform.console.issue.domain.ServiceTimeoutLv;
import com.haier.openplatform.console.issue.module.TimeoutLevel;
import com.haier.openplatform.util.Pager;

/**
 * @author Vilight_Wu 响应级别设定
 */
public class LevelSetupAction extends BaseIssueAction {
	private static final long serialVersionUID = 2497617931665363591L;

	/**
	 * service分页对象
	 */
	private Pager<BusinessService> pager = new Pager<BusinessService>();

	/**
	 * 应用系统列表
	 */
	private List<AppLists> appLists = new ArrayList<AppLists>();

	/**
	 * 响应级别列表
	 */
	private List<TimeoutLevel> timeoutLevelList = new ArrayList<TimeoutLevel>();

	/**
	 * 应用系统id
	 */
	private String appId;

	/**
	 * service名
	 */
	private String serviceName;

	/**
	 * 响应级别
	 */
	private String level;

	/**
	 * 要设置的service id
	 */
	private String serviceIds;

	public Pager<BusinessService> getPager() {
		return pager;
	}

	public void setPager(Pager<BusinessService> pager) {
		this.pager = pager;
	}


	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getServiceIds() {
		return serviceIds;
	}

	public void setServiceIds(String serviceIds) {
		this.serviceIds = serviceIds;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
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

	/**
	 * 批量设置响应级别
	 * 
	 * @return json格式，填充前台表格
	 * @throws Exception
	 */
	public String saveServiceLevel() throws Exception {
		if (level != null && serviceIds != null) {
			// 将页面的service id转成long数组
			List<Long> svcIds = new ArrayList<Long>();
			String[] ids = serviceIds.split(",");
			for (String id : ids) {
				svcIds.add(Long.valueOf(id));
			}
			getBaseInfoService().saveServiceLevel(svcIds, level);
		}
		return SUCCESS;
	}

	/**
	 * 获取servcie列表
	 * 
	 * @return json格式，填充前台表格
	 * @throws Exception
	 */
	public String serviceLevel() throws Exception {
		if (appId != null && serviceName != null) {
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
		// 获取level列表
		List<ServiceTimeoutLv> serviceTimeoutLvList = getBaseInfoService().getServiceTimeoutLv();
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
		return SUCCESS;
	}

}
