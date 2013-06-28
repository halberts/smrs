package com.haier.openplatform.console.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.haier.openplatform.console.issue.service.IssueMailService;
import com.haier.openplatform.util.SpringApplicationContextHolder;

/**
 * this is the notificationJob which is running at back-end periodically.
 * Email/SMS will be sent if condition is satisfied
 * 
 * @author Aaron_Guan
 * @author wangxuzheng@haier.com
 * 
 */
public class NotificationJob implements Job {
	@Override
	public void execute(JobExecutionContext context)throws JobExecutionException {
		IssueMailService issueMailService = SpringApplicationContextHolder.getBean("issueMailService");
		issueMailService.shootUnsentIssues();
	}
}
