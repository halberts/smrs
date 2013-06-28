package com.haier.openplatform.console.issue.webapp.action;

import java.util.ArrayList;
import java.util.List;

import com.haier.openplatform.console.issue.domain.AppLists;
import com.haier.openplatform.console.issue.domain.ServiceTimeoutLv;
import com.haier.openplatform.console.issue.module.TimeoutLevel;

/**
 * appLevel分布图
 * @author WangJian
 *
 */
public class LevelLocatedAction extends BaseIssueAction {

	private static final long serialVersionUID = -4951326825247330103L; 
	/**
	 * 响应级别列表
	 */
	private List<TimeoutLevel> timeoutLevelList = new ArrayList<TimeoutLevel>();
	/**
	 * 应用系统列表
	 */
	private List<AppLists> appList = new ArrayList<AppLists>(); 
	/**
	 * appName对应level数对应列表
	 */
	private List<Object> appLevelList = new ArrayList<Object>(); 
	public List<TimeoutLevel> getTimeoutLevelList() {
		return timeoutLevelList;
	}
	public void setTimeoutLevelList(List<TimeoutLevel> timeoutLevelList) {
		this.timeoutLevelList = timeoutLevelList;
	}
	public List<AppLists> getAppList() {
		return appList;
	}
	public void setAppList(List<AppLists> appList) {
		this.appList = appList;
	} 
	public List<Object> getAppLevelList() {
		return appLevelList;
	}
	public void setAppLevelList(List<Object> appLevelList) {
		this.appLevelList = appLevelList;
	}
	@Override
	public String execute() throws Exception {
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
		appList = getBaseInfoService().getAppLists(); 
		//根据系统名称查询level个数
		for(TimeoutLevel lvList:timeoutLevelList){
			List<Object> appOneInfo = new  ArrayList<Object>();
			for(int i=0;i<appList.size();i++){
				List<Object> levelNumber = new  ArrayList<Object>();
				//根据level和appName查询app在本level上的个数
				long levelNum = getBaseInfoService().getAppLevel(lvList.getLvName(), appList.get(i).getId());
				levelNumber.add(levelNum);
				levelNumber.add(i);
				appOneInfo.add(levelNumber);
			}
			appLevelList.add(appOneInfo);
			}
		return SUCCESS;
	}
}
