package com.haier.openplatform.console.issue.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haier.openplatform.console.issue.domain.ServiceTimeoutLv;

/**
 * level对应时间查询
 * @author WangJian
 *
 */
public class LevelInfoAction  extends BaseIssueAction {
	private static final long serialVersionUID = -133014992428502601L;
	/**
	 * 响应级别列表
	 */ 
	private Map<String,String> timeoutLevelMap = new HashMap<String,String>();  
	private List<Object> timeoutLevelList = new ArrayList<Object>();

	public Map<String, String> getTimeoutLevelMap() {
		return timeoutLevelMap;
	}

	public void setTimeoutLevelMap(Map<String, String> timeoutLevelMap) {
		this.timeoutLevelMap = timeoutLevelMap;
	}
 
	public List<Object> getTimeoutLevelList() {
		return timeoutLevelList;
	}

	public void setTimeoutLevelList(List<Object> timeoutLevelList) {
		this.timeoutLevelList = timeoutLevelList;
	}

	@Override
	public String execute() throws Exception {
	// 获取level列表
			List<ServiceTimeoutLv> serviceTimeoutLvList = getBaseInfoService().getServiceTimeoutLv();
			for (ServiceTimeoutLv serviceTimeoutLv : serviceTimeoutLvList) {
				long otMsec = serviceTimeoutLv.getOvertimeMsec();
				// 转换单位
				if (otMsec < 1000) {
					timeoutLevelMap.put(serviceTimeoutLv.getId(), otMsec + "ms");
				} else {
					timeoutLevelMap.put(serviceTimeoutLv.getId(), otMsec / 1000 + "s");
				} 
			}
			timeoutLevelList.add(timeoutLevelMap);
			return SUCCESS;
	}
}
