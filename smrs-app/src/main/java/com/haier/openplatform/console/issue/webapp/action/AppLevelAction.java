package com.haier.openplatform.console.issue.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haier.openplatform.console.issue.domain.AppLists;
import com.haier.openplatform.console.issue.domain.ServiceTimeoutLv;
import com.haier.openplatform.console.issue.module.TimeoutLevel;

/**
 * 获取全业务超时前十数据
 * @author WangJian
 *
 */
public class AppLevelAction  extends BaseIssueAction {
	private static final long serialVersionUID = -7906746139674188982L;
	/**
	 * 响应级别列表
	 */
	private List<TimeoutLevel> timeoutLevelList = new ArrayList<TimeoutLevel>();
	
	/**
	 * 应用系统列表
	 */
	private List<AppLists> appLists = new ArrayList<AppLists>();
	
	/**
	 * 平均响应时间与appName、level对应列表
	 */
	private List<Object> appLevel = new ArrayList<Object>();
	
	/**
	 * 平均响应时间与appName、level对应列表
	 */
	private List<Object> appInfoList = new ArrayList<Object>();
	
	public List<Object> getAppInfoList() {
		return appInfoList;
	}

	public void setAppInfoList(List<Object> appInfoList) {
		this.appInfoList = appInfoList;
	}

	public List<Object> getAppLevel() {
		return appLevel;
	}

	public void setAppLevel(List<Object> appLevel) {
		this.appLevel = appLevel;
	}

	public List<AppLists> getAppLists() {
		return appLists;
	}

	public void setAppLists(List<AppLists> appLists) {
		this.appLists = appLists;
	}

	public List<TimeoutLevel> getTimeoutLevelList() {
		return timeoutLevelList;
	}

	public void setTimeoutLevelList(List<TimeoutLevel> timeoutLevelList) {
		this.timeoutLevelList = timeoutLevelList;
	}
	
	@SuppressWarnings("unused")
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		// 获取level列表
		List<ServiceTimeoutLv> serviceTimeoutLvList = getBaseInfoService()
				.getServiceTimeoutLv();
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
		//获取app列表
		appLists = getBaseInfoService().getAppLists();
		//根据appName查询出appName与appId的对应Map
		Map<Long,String> appMap = new HashMap<Long,String>();
		
		//根据系统名称查询level、 和平均时间
		for(AppLists appList:appLists){
			List<Object> appOneInfo = new  ArrayList<Object>();
			for(TimeoutLevel lvList:timeoutLevelList){
				long time = 60;
				//long time = getBaseInfoService.getAppLevel(lvList.getLvName(),appList.getId());
				appMap.put(time, appList.getAppName());
				List<Object> levelOneInfo = new  ArrayList<Object>();
				levelOneInfo.add(time);
				levelOneInfo.add(appList.getAppName());
				appOneInfo.add(levelOneInfo);
			}
			appLevel.add(appOneInfo);
			}
		return SUCCESS;
	}
	
}
