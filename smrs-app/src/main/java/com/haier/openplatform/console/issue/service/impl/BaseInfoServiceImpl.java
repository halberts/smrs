package com.haier.openplatform.console.issue.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.haier.openplatform.console.domain.VIPBean;
import com.haier.openplatform.console.issue.dao.BaseInfoDAO;
import com.haier.openplatform.console.issue.domain.AppLists;
import com.haier.openplatform.console.issue.domain.BusinessService;
import com.haier.openplatform.console.issue.domain.IssueSupporter;
import com.haier.openplatform.console.issue.domain.ReleaseTracerConf;
import com.haier.openplatform.console.issue.domain.ServiceTimeoutLv;
import com.haier.openplatform.console.issue.module.AppIssueSupporter;
import com.haier.openplatform.console.issue.service.BaseInfoService;
import com.haier.openplatform.hmc.sender.SendMsgService;
import com.haier.openplatform.util.Pager;

/**
 * @author shanjing
 */
public class BaseInfoServiceImpl implements BaseInfoService {

	private BaseInfoDAO baseInfoDAO;

	private SendMsgService traceSender;

	public void setBaseInfoDAO(BaseInfoDAO baseInfoDAO) {
		this.baseInfoDAO = baseInfoDAO;
	}

	public void setTraceSender(SendMsgService traceSender) {
		this.traceSender = traceSender;
	}

	@Override
	public List<AppLists> getAppLists() {
		if(baseInfoDAO==null){
			 List<AppLists> list = new ArrayList<AppLists>();
			return list;
		}
		return baseInfoDAO.getAppLists();
	}

	@Override
	public List<ServiceTimeoutLv> getServiceTimeoutLv() {
		return baseInfoDAO.getServiceTimeoutLv();
	}

	@Override
	public Pager<BusinessService> getBusinessService(Long appId, String serviceName, Pager<BusinessService> pager) {
		return baseInfoDAO.getBusinessService(appId, serviceName, pager);
	}

	@Override
	public List<BusinessService> getBusinessService(Long appId, String serviceName) {
		return baseInfoDAO.getBusinessService(appId, serviceName);
	}

	@Override
	public void saveServiceLevel(List<Long> serviceIds, String level) {
		baseInfoDAO.saveServiceLevel(serviceIds, level);
	}

	@Override
	public void saveApplevel(Long appId, String level) {
		baseInfoDAO.saveApplevel(appId, level);
	}

	@Override
	public void saveServiceAlert(List<Long> serviceIds, Long alertMax) {
		baseInfoDAO.saveServiceAlert(serviceIds, alertMax);
	}

	@Override
	public void saveServiceVip(String appName, List<String> serviceNms, List<Long> serviceIds) {
		baseInfoDAO.saveServiceVip(serviceIds);
		VIPBean vipBean = new VIPBean();
		vipBean.setAppName(appName);
		vipBean.setServiceAPINM(serviceNms);
		vipBean.setVipType(VIPBean.VIP);
		traceSender.sendMsg(vipBean);
	}

	@Override
	public ReleaseTracerConf getReleaseTraceConf(Long id) {
		return baseInfoDAO.getReleaseTraceConf(id);
	}

	@Override
	public void saveReleaseTraceConf(ReleaseTracerConf rtc) {
		baseInfoDAO.saveReleaseTraceConf(rtc);
	}

	@Override
	public void updateReleaseTraceConf(ReleaseTracerConf rtc) {
		baseInfoDAO.updateReleaseTraceConf(rtc);
	}

	@Override
	public void activeReleaseTraceConf(String appNm, ReleaseTracerConf rtc) {
		baseInfoDAO.activeReleaseTraceConf(rtc);
		String[] sa = rtc.getTraceServices().split(",");
		List<String> serviceNms = new ArrayList<String>(Arrays.asList(sa));
		VIPBean vipBean = new VIPBean();
		vipBean.setAppName(appNm);
		vipBean.setServiceAPINM(serviceNms);
		vipBean.setTimeInterval(new Long(rtc.getTracePeriod()));
		vipBean.setVipType(VIPBean.VIPTemp);
		traceSender.sendMsg(vipBean);
	}

	@Override
	public void deleteReleaseTraceConf(long rtcId) {
		baseInfoDAO.deleteReleaseTraceConf(rtcId);
	}

	@Override
	public List<ReleaseTracerConf> getReleaseTraceConfs(Long appId) {
		return baseInfoDAO.getReleaseTraceConfs(appId);
	}

	@Override
	public IssueSupporter getIssueSupporter(Long id) {
		return baseInfoDAO.getIssueSupporter(id);
	}

	@Override
	public void saveIssueSupporter(IssueSupporter is, Long appId) {
		baseInfoDAO.saveIssueSupporter(is, appId);
	}

	@Override
	public void updateIssueSupporter(IssueSupporter is) {
		baseInfoDAO.updateIssueSupporter(is);
	}

	@Override
	public void updateAppLists(Long appId, Long stpId) {
		baseInfoDAO.updateAppLists(appId, stpId);
	}

	@Override
	public void deleteIssueSupporter(Long id) {
		baseInfoDAO.deleteIssueSupporter(id);
	}

	@Override
	public List<IssueSupporter> getIssueSupporters(Long appId) {
		return baseInfoDAO.getIssueSupporters(appId);
	}

	@Override
	public void activeServiceLv(long appId, String appName) {
		VIPBean vipBean = new VIPBean();
		vipBean.setAppName(appName);
		vipBean.setVipType(VIPBean.SERVIC_LV);
		List<BusinessService> slist = baseInfoDAO.getBusinessService(appId, null);
		for (BusinessService bs : slist) {
			vipBean.setServiceLV(bs.getServiceApiName(), bs.getOvertimeLv());
		}
		traceSender.sendMsg(vipBean);
	}

	@Override
	public void activeServiceVip(long appId, String appName) {
		VIPBean vipBean = new VIPBean();
		vipBean.setAppName(appName);
		vipBean.setVipType(VIPBean.VIP);
		List<BusinessService> slist = baseInfoDAO.getVipService(appId);
		List<String> apiList = new ArrayList<String>();
		for (BusinessService bs : slist) {
			apiList.add(bs.getServiceApiName());
		}
		traceSender.sendMsg(vipBean);
	}

	@Override
	public boolean addAppName(String appName) {
		return baseInfoDAO.addAppName(appName);
	}
	
	@Override
	public AppLists getAppById(Long appId){
		return baseInfoDAO.getAppById(appId);
	}
	
	@Override
	public AppLists getAppByName(String appName) {
		return baseInfoDAO.getAppByName(appName);
	}

	@Override
	public List<AppIssueSupporter> getAppIssueSupporter(){ 
		return baseInfoDAO.getAppIssueSupporter();
	}
	
	@Override
	public Long getAppLevel(String levelName,Long appId){
		Long levelNum = baseInfoDAO.getAppLevel(levelName,appId); 
		return levelNum;
	}

	@Override
	public List<IssueSupporter> getIssueSupporterList() {
		return baseInfoDAO.getIssueSupporterList();
	}
}
