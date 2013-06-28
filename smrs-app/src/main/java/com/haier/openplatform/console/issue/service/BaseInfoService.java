package com.haier.openplatform.console.issue.service;

import java.util.List;

import com.haier.openplatform.console.issue.domain.AppLists;
import com.haier.openplatform.console.issue.domain.BusinessService;
import com.haier.openplatform.console.issue.domain.IssueSupporter;
import com.haier.openplatform.console.issue.domain.ReleaseTracerConf;
import com.haier.openplatform.console.issue.domain.ServiceTimeoutLv;
import com.haier.openplatform.console.issue.module.AppIssueSupporter;
import com.haier.openplatform.util.Pager;

/**
 * @author shanjing
 * 基础数据维护Service
 */
public interface BaseInfoService {

	/**
	 * 获取所有应用系统名称
	 */
	public List<AppLists> getAppLists();

	/**
	 * 获取相应等级
	 */
	public List<ServiceTimeoutLv> getServiceTimeoutLv();

	/**
	 * 查找Service
	 * @param appId:应用
	 * @param serviceName：Service名称
	 * @param pager：分页
	 */
	public Pager<BusinessService> getBusinessService(Long appId, String serviceName, Pager<BusinessService> pager);

	/**
	 * 查找Service
	 * @param appId:应用
	 * @param serviceName：Service名称
	 */
	public List<BusinessService> getBusinessService(Long appId, String serviceName);

	/**
	 * 按Service设定响应等级
	 * @param serviceIds：ServiceId集合
	 * @param level:响应等级
	 */
	public void saveServiceLevel(List<Long> serviceIds, String level);

	/**
	 * 按应用系统设定响应等级
	 * @param appId
	 * @param level
	 */
	public void saveApplevel(Long appId, String level);

	/**
	 * 保存Service阀值
	 * @param serviceIds:ServiceId集合
	 * @param alertMax：阀值
	 */
	public void saveServiceAlert(List<Long> serviceIds, Long alertMax);

	/**
	 * 保存ServiceVIP值
	 * @param serviceIds:ServiceId集合
	 * @param vip：vip值
	 */
	public void saveServiceVip(String appName, List<String> serviceNms, List<Long> serviceIds);

	/**
	 * 精确查找上线追踪
	 * @param id
	 * @return
	 */
	public ReleaseTracerConf getReleaseTraceConf(Long id);

	/**
	 * 保存上线追踪
	 */
	public void saveReleaseTraceConf(ReleaseTracerConf rtc);

	/**
	 * 更新上线追踪
	 */
	public void updateReleaseTraceConf(ReleaseTracerConf rtc);

	/**
	 * 激活上线追踪
	 */
	public void activeReleaseTraceConf(String appNm, ReleaseTracerConf rtc);

	/**
	 * 在激活之前删除上线追踪
	 */
	public void deleteReleaseTraceConf(long rtcId);

	/**
	 * 查找上线追踪
	 */
	public List<ReleaseTracerConf> getReleaseTraceConfs(Long appId);

	/**
	 * 精确查找系统负责人
	 * @param id
	 */
	public IssueSupporter getIssueSupporter(Long id);

	/**
	 * 保存系统负责人
	 */
	public void saveIssueSupporter(IssueSupporter is, Long appId);

	/**
	 * 更新系统负责人
	 */
	public void updateIssueSupporter(IssueSupporter is);

	/**
	 * 更新应用系统中的联系人
	 * @param appId
	 * @param stpId
	 */
	public void updateAppLists(Long appId, Long stpId);

	/**
	 * 删除系统负责人
	 */
	public void deleteIssueSupporter(Long id);

	/**
	 * 查询系统负责人
	 */
	public List<IssueSupporter> getIssueSupporters(Long appId);

	/**
	 * 激活ServiceLV
	 */
	public void activeServiceLv(long appId, String appName);

	/**
	 * 激活ServiceVip
	 */
	public void activeServiceVip(long appId, String appName);
	
	/**
	 * 保存应用名称
	 * @return true:保存成功  false:名称已存在
	 */
	public boolean addAppName(String appName);
	
	/**
	 * 根据ID获取应用
	 * @return 
	 */
	public AppLists getAppById(Long appId);
	
	/**
	 * 通过app的名称获取app信息
	 * @param appName
	 * @return
	 */
	public AppLists getAppByName(String appName);
	
	/**
	 * 获取所有的APP负责人以及APPID
	 * @return 
	 */
	public List<AppIssueSupporter> getAppIssueSupporter();
	
	/**
	 * 获取app下level的个数
	 * @return 
	 */
	public Long getAppLevel(String levelName,Long appId);

	public List<IssueSupporter> getIssueSupporterList();
}
