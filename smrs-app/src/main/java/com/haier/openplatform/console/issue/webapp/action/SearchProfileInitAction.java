package com.haier.openplatform.console.issue.webapp.action;

import java.util.ArrayList;
import java.util.List;

import com.haier.openplatform.console.issue.domain.AppLists;

/**
 * @author WangXuzheng
 *
 */
public class SearchProfileInitAction extends BaseIssueAction {
	private static final long serialVersionUID = 7538032557576016767L;
	private List<AppLists> appList = new ArrayList<AppLists>();
	@Override
	public String execute() throws Exception {
		this.appList = getBaseInfoService().getAppLists();
		return SUCCESS;
	}
	public List<AppLists> getAppList() {
		return appList;
	}
}
