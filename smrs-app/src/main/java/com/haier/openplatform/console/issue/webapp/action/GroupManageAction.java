package com.haier.openplatform.console.issue.webapp.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.haier.openplatform.console.issue.domain.AppLists;
import com.haier.openplatform.console.issue.domain.BusinessService;
import com.haier.openplatform.console.issue.domain.BusinessServiceGrp;
import com.haier.openplatform.util.Pager;

/**
 * @author Vilight_Wu 业务分组管理
 */
public class GroupManageAction extends BaseIssueAction {

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
	 * service名
	 */
	private String serviceName;

	/**
	 * 要设置的service id
	 */
	private String serviceIds;

	/**
	 * 服务分组列表
	 */
	private List<BusinessServiceGrp> svcGroupList;

	/**
	 * 应用系统id
	 */
	private String appId;

	/**
	 * 分组名称
	 */
	private String serviceGrpName;

	/**
	 * 分组备注
	 */
	private String serviceGrpDetail;

	/**
	 * 操作类型
	 */
	private String oper;

	/**
	 * 分组id
	 */
	private String groupId;

	/**
	 * service列表
	 */
	private List<BusinessService> serviceList;

	public String getServiceGrpDetail() {
		return serviceGrpDetail;
	}

	public void setServiceGrpDetail(String serviceGrpDetail) {
		this.serviceGrpDetail = serviceGrpDetail;
	}

	public List<BusinessService> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<BusinessService> serviceList) {
		this.serviceList = serviceList;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getServiceGrpName() {
		return serviceGrpName;
	}

	public void setServiceGrpName(String serviceGrpName) {
		this.serviceGrpName = serviceGrpName;
	}

	public List<BusinessServiceGrp> getSvcGroupList() {
		return svcGroupList;
	}

	public void setSvcGroupList(List<BusinessServiceGrp> svcGroupList) {
		this.svcGroupList = svcGroupList;
	}

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
	 * 保存分组的service信息（只更新business_service表中的grp_id外键）
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateGroupService() throws Exception {
		Long grpId = Long.valueOf(groupId);
		// 更新business_service表中对应的groupId（外键）
		String[] ids = serviceIds.split(",");
		for (String id : ids) {
			BusinessService bs = getReportService().getBusinessService(Long.valueOf(id));
			bs.setServiceGrpId(grpId);
			getReportService().updateBusinessService(bs);
		}
		return SUCCESS;
	}

	/**
	 * 获取某app下指定分组的service列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String serviceListOfGrp() throws Exception {
		if (appId != null && groupId != null) {
			serviceList = getReportService().getBusinessService(Long.valueOf(appId), Long.valueOf(groupId));
		}
		return SUCCESS;
	}

	/**
	 * 获取所有service
	 * 
	 * @return
	 * @throws Exception
	 */
	public String businessServiceList() throws Exception {
		if (appId != null) {
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

	/**
	 * 增删改 分组名字及备注信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String groupCrud() throws Exception {
		if ("add".equals(oper)) {
			if (appId != null && serviceGrpName != null) {
				getReportService().addBusinessServiceGrp(serviceGrpName, serviceGrpDetail,
						Long.valueOf(appId).longValue());
			}
		} else if ("edit".equals(oper)) {
			BusinessServiceGrp bs = getReportService().getBusinessServiceGrp(Long.valueOf(groupId));
			bs.setServiceGrpName(serviceGrpName);
			bs.setServiceGrpDetail(serviceGrpDetail);
			getReportService().updateBusinessServiceGrp(bs);
		} else { // del
			getReportService().deleteBusinessServiceGrp(Long.valueOf(groupId));
		}
		return SUCCESS;
	}

	/**
	 * 获取service分组信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String serviceGrpList() throws Exception {
		if (appId != null) {
			svcGroupList = getReportService().getAllBusinessServiceGrp(Long.valueOf(appId).longValue());
		}
		return SUCCESS;
	}

	public String execute() throws Exception {
		// 获取应用系统列表
		appLists = getBaseInfoService().getAppLists();
		return SUCCESS;
	}

}
