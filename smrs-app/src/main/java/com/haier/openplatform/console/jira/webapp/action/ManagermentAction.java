package com.haier.openplatform.console.jira.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haier.openplatform.console.issue.domain.AppLists;
import com.haier.openplatform.console.jira.module.ManagermentModel;
import com.haier.openplatform.console.jira.module.ManagermentSearchModel;
import com.haier.openplatform.console.jira.module.ProSeleModel;
import com.haier.openplatform.util.Pager;

/**
 * @author  Jira
 */
public class ManagermentAction extends BaseJiraAction {

	private static final long serialVersionUID = 2986568372732421489L;
	/**
	 * service分页对象
	 */
	private Pager<ManagermentModel> pager = new Pager<ManagermentModel>();

	private List<ProSeleModel> proList = new ArrayList<ProSeleModel>();

	/**
	 * 应用系统列表
	 */
	private List<AppLists> appLists = new ArrayList<AppLists>();

	private String serviceName;


	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}



	public Pager<ManagermentModel> getPager() {
		return pager;
	}

	public void setPager(Pager<ManagermentModel> pager) {
		this.pager = pager;
	}

	public List<AppLists> getAppLists() {
		return appLists;
	}

	public void setAppLists(List<AppLists> appLists) {
		this.appLists = appLists;
	}
	
	public List<ProSeleModel> getProList() {
		return proList;
	}

	public void setProList(List<ProSeleModel> proList) {
		this.proList = proList;
	}

	@Override
	public String execute() throws Exception {
		// 获取应用系统列表
		Map<String, Object> pram = new HashMap<String,Object>();
		pram.put("pkey", "hmmsqy");
		pram.put("start", "2011-10-01");
		pram.put("end", "2012-12-31");
		pram.put("effissuestatus", 10006);
		pram.put("effissuestatus_1", "5");
		pram.put("effissuestatus_2", "6");
		pram.put("Transformdays", 28800);
		pram.put("issuedoc", 10040);
		pram.put("tecdoc", 10061);
		pram.put("forstart", 10026);
		pram.put("bugtype", "1");
		ManagermentSearchModel searchModel = new ManagermentSearchModel();
		pager.setCurrentPage(getPage());
		pager.setPageSize(getRows());
		searchModel.setPager(pager);
		//pager = getLfSqlService().getManagerment(pram,searchModel);
		return SUCCESS;
	}
}
