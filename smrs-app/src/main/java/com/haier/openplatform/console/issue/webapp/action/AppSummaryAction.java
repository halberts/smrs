package com.haier.openplatform.console.issue.webapp.action;

import com.haier.openplatform.console.issue.module.AppSummaryModel;

/**
 * 应用每月变化趋势action
 * @author WangXuzheng
 *
 */
public class AppSummaryAction extends BaseIssueAction {
	private static final long serialVersionUID = -5820374799829516595L;
	private AppSummaryModel appSummaryModel = new AppSummaryModel();
	
	public AppSummaryModel getAppSummaryModel() {
		return appSummaryModel;
	}

	public void setAppSummaryModel(AppSummaryModel appSummaryModel) {
		this.appSummaryModel = appSummaryModel;
	}

	@Override
	public String execute() throws Exception {
		this.appSummaryModel = getAppMonitorService().getAppSummary();
		return SUCCESS;
	}

}
