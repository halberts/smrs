package com.haier.openplatform.console.sonar.quartz;

import java.util.Calendar;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haier.openplatform.console.issue.service.ProfileService;
import com.haier.openplatform.console.sonar.service.SonarService;
import com.haier.openplatform.util.SpringApplicationContextHolder;
import com.smrs.util.DateCommonUtil;

public class CheckSonarProjectIssueJob implements Job{

	private static final Logger LOGGER = LoggerFactory.getLogger(CheckSonarProjectIssueJob.class);
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		LOGGER.error("------CheckSonarProjectIssue begin------");
		SonarService sonarService = SpringApplicationContextHolder.getBean("sonarService");
		sonarService.checkProjectIssue();
		LOGGER.error("------CheckSonarProjectIssue end------");
		
		LOGGER.error("------Check ActionAverageTime ProjectIssue begin------");
		ProfileService profileService = SpringApplicationContextHolder.getBean("profileService");
		Date start = DateCommonUtil.getDayOfWeekDate(-1, Calendar.MONDAY);
		Date end = DateCommonUtil.getDayOfWeekDate(-1, Calendar.SUNDAY);
		profileService.checkAppAverageTime(start, end);
		LOGGER.error("------Check ActionAverageTime ProjectIssue end------");
	}
}
