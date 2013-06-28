package com.haier.openplatform.console.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haier.openplatform.console.issue.service.IssueMailService;
import com.haier.openplatform.util.SpringApplicationContextHolder;

/**
 * job is scheduled to process OT issues
 * 
 * @author Aaron_Guan
 * 
 */
public class NotificationOvertimeJob implements Job {
	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationOvertimeJob.class);

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		LOGGER.error("############################################NotificationOvertimeJob start############################################");
		IssueMailService issueMailService = SpringApplicationContextHolder.getBean("issueMailService");
		try {
			issueMailService.shootOvertimeIssues();
		} catch (Exception ex) {
			LOGGER.error("failed to shoot overtime emails...", ex);
		}
		LOGGER.error("############################################NotificationOvertimeJob end############################################");
	}
}
