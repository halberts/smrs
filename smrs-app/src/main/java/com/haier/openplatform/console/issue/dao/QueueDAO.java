package com.haier.openplatform.console.issue.dao;

import com.haier.openplatform.console.issue.domain.HopIssueDetailEx;
import com.haier.openplatform.console.issue.domain.HopIssueQueue;
import com.haier.openplatform.console.issue.domain.ServiceApiStatusQueue;


/**
 * @author Mike Yang
 * 关于Queue的数据操作
 */
public interface QueueDAO {

	/**
	 * 保存ServiceApiStatusQueue
	 */
	public void saveServiceQueue(ServiceApiStatusQueue pojo);

	/**
	 * 保存HopIssueQueue
	 */
	public void saveIssueQueue(HopIssueQueue pojo);

	/**
	 * 保存异常详细信息
	 */
	public void saveHopIssueDetailEx(HopIssueDetailEx pojo);
	

}