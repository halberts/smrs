package com.haier.openplatform.console.jira.webapp.action;

import com.haier.openplatform.console.jira.service.LfSqlService;
import com.haier.openplatform.console.jira.service.ProSeleService;
import com.haier.openplatform.console.jira.service.UserGroupService;
import com.haier.openplatform.console.jira.service.UserSatisfactionService;
import com.smrs.webapp.action.BaseConsoleAction;

/**
 * @author Vilight_Wu 所有监控action的父类
 */
public class BaseJiraAction extends BaseConsoleAction {

	private static final long serialVersionUID = 7707655100286443793L;

	/**
	 * 分页参数
	 */
	private Long rows = 20L;
	private Long page = 0L;
	private Long total = 0L;
	private Long records = 0L;
	private String sord;
	private String sidx;
	private LfSqlService lfSqlService;
	private ProSeleService proSeleService;
	private UserGroupService userGroupService;
	private UserSatisfactionService userSatisfactionService;
	
	public void setUserSatisfactionService(
			UserSatisfactionService userSatisfactionService) {
		this.userSatisfactionService = userSatisfactionService;
	}


	public UserSatisfactionService getUserSatisfactionService() {
		return userSatisfactionService;
	}


	public UserGroupService getUserGroupService() {
		return userGroupService;
	}

	
	public void setUserGroupService(UserGroupService userGroupService) {
		this.userGroupService = userGroupService;
	}


	public ProSeleService getProSeleService() {
		return proSeleService;
	}

	public void setProSeleService(ProSeleService proSeleService) {
		this.proSeleService = proSeleService;
	}

	public LfSqlService getLfSqlService() {
		return lfSqlService;
	}

	public void setLfSqlService(LfSqlService lfSqlService) {
		this.lfSqlService = lfSqlService;
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
	public int cNullToint(Object o){
		if(o==null||"null".equals(o)||o.equals("")){
			return 0;
		}else{
			return Integer.parseInt(o.toString());
		}
	}
	public double cNullTodou(Object o){
		if(o==null||"null".equals(o)||o.equals("")){
			return 0;
		}else{
			return Double.parseDouble(o.toString());
		}
	}
}
