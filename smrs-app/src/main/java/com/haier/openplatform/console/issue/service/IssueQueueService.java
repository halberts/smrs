/**
 * 
 */
package com.haier.openplatform.console.issue.service;

import com.haier.openplatform.console.domain.ExCaptureBean;
import com.haier.openplatform.console.domain.InvokeSumBeans;
import com.haier.openplatform.console.issue.domain.HopIssueDetailEx;
import com.haier.openplatform.console.issue.domain.HopIssueQueue;

/**
 * @author mk
 *
 */
public interface IssueQueueService {

	/**
	 * save data into Invoke Sum table
	 * @param isb : passed from each app server via jms
	 */
	void saveInvokeSumQueue(InvokeSumBeans isb);

	/**
	 * save data into Issue table
	 * @param ecb : passed from each app server via jms
	 */
	HopIssueQueue saveIssueQueue(ExCaptureBean ecb);
	
	public void saveHopIssueDetailEx(HopIssueDetailEx ex);
}
