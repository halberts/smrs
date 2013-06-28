package com.haier.openplatform.console.quartz.webapp.action;

import java.util.List;

import com.haier.openplatform.console.issue.domain.AppLists;
import com.haier.openplatform.console.issue.service.BaseInfoService;

/**
 * 进入监控页面的action，展示应用列表
 * @author WangXuzheng
 *
 */
public class QuartzMonitorAction extends BaseQuartzAction {
	private static final long serialVersionUID = 200926720608126705L;
	private BaseInfoService baseInfoService;
	private List<AppLists> appList;
	public List<AppLists> getAppList() {
		return appList;
	}
	public void setAppList(List<AppLists> appList) {
		this.appList = appList;
	}
	public void setBaseInfoService(BaseInfoService baseInfoService) {
		this.baseInfoService = baseInfoService;
	}
	@Override
	public String execute() throws Exception {
		this.appList = baseInfoService.getAppLists();
		return SUCCESS;
	}
	
}
