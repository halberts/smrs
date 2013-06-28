package com.haier.openplatform.console.issue.webapp.action;

import java.util.ArrayList;
import java.util.List;

import com.haier.openplatform.console.issue.domain.AppLists;
import com.haier.openplatform.console.issue.domain.IssueSupporter;

/**
 * @author Vilight_Wu 设定系统负责人
 */
public class SystemSupporterAction extends BaseIssueAction {

	private static final long serialVersionUID = 551477998626373498L;

	/**
	 * 应用系统名
	 */
	private String appName;

	/**
	 * 应用系统是否存在
	 */
	private boolean appAdded;

	/**
	 * 应用系统列表
	 */
	private List<AppLists> appLists = new ArrayList<AppLists>();

	/**
	 * 负责人列表
	 */
	private List<IssueSupporter> supporterList = new ArrayList<IssueSupporter>();

	private IssueSupporter issueSupporter;

	/**
	 * 应用系统id
	 */
	private String appId;

	/**
	 * 负责人id，用于更新操作
	 */
	private String supporterId;

	/**
	 * 负责人各属性（用来接收前台ajax提交的数据）
	 */
	private String ownerName;
	private String ownerEmail;
	private String ownerPhone;
	private String sptOwnerName;
	private String sptOwnerEmail;
	private String sptOwnerPhone;
	private String sptEmail;
	private String sptPhone;

	public boolean isAppAdded() {
		return appAdded;
	}

	public void setAppAdded(boolean appAdded) {
		this.appAdded = appAdded;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public IssueSupporter getIssueSupporter() {
		return issueSupporter;
	}

	public void setIssueSupporter(IssueSupporter issueSupporter) {
		this.issueSupporter = issueSupporter;
	}

	public String getOwnerPhone() {
		return ownerPhone;
	}

	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getSptOwnerName() {
		return sptOwnerName;
	}

	public void setSptOwnerName(String sptOwnerName) {
		this.sptOwnerName = sptOwnerName;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public String getSptOwnerEmail() {
		return sptOwnerEmail;
	}

	public void setSptOwnerEmail(String sptOwnerEmail) {
		this.sptOwnerEmail = sptOwnerEmail;
	}

	public String getSptOwnerPhone() {
		return sptOwnerPhone;
	}

	public void setSptOwnerPhone(String sptOwnerPhone) {
		this.sptOwnerPhone = sptOwnerPhone;
	}

	public String getSptEmail() {
		return sptEmail;
	}

	public void setSptEmail(String sptEmail) {
		this.sptEmail = sptEmail;
	}

	public String getSptPhone() {
		return sptPhone;
	}

	public void setSptPhone(String sptPhone) {
		this.sptPhone = sptPhone;
	}

	public String getSupporterId() {
		return supporterId;
	}

	public void setSupporterId(String supporterId) {
		this.supporterId = supporterId;
	}

	public List<IssueSupporter> getSupporterList() {
		return supporterList;
	}

	public void setSupporterList(List<IssueSupporter> supporterList) {
		this.supporterList = supporterList;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public List<AppLists> getAppLists() {
		return appLists;
	}

	public void setAppLists(List<AppLists> appLists) {
		this.appLists = appLists;
	}

	/**
	 * 增加新系统
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addAppName() throws Exception {
		appAdded = false;
		if (appName != null) {
			appAdded = getBaseInfoService().addAppName(appName);
		}
		return SUCCESS;
	}

	/**
	 * 增删改 负责人
	 * 
	 * @return
	 * @throws Exception
	 */
	public String crudSupporter() throws Exception {
		if (appId != null && supporterId != null) {
			if ("1".equals(supporterId)) { // 新增
				getBaseInfoService().saveIssueSupporter(issueSupporter, Long.valueOf(appId));
			} else { // 更新
				issueSupporter.setId(Long.valueOf(supporterId));
				getBaseInfoService().updateIssueSupporter(issueSupporter);
			}
		}
		return SUCCESS;
	}

	/**
	 * 查找某应用系统负责人
	 * 
	 * @return json数据，填充前台表格
	 * @throws Exception
	 */
	public String issueSupporters() throws Exception {
		if (appId != null) {
			supporterList = getBaseInfoService().getIssueSupporters(Long.valueOf(appId));
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
