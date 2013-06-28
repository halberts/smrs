package com.haier.openplatform.console.issue.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haier.openplatform.console.issue.service.AppMonitorService;
import com.haier.openplatform.util.SpringApplicationContextHolder;

public class IssueAppHealthyJob implements Job {
	private static final Logger LOGGER = LoggerFactory.getLogger(IssueAppHealthyJob.class);
	@Override
	public void execute(JobExecutionContext context)throws JobExecutionException { 
		try{
			LOGGER.error("*******************app healthy check start**********************");
			AppMonitorService appMonitorsvr = SpringApplicationContextHolder.getBean("appMonitorService");
			appMonitorsvr.appHealthyCheck();
			LOGGER.error("*******************app healthy check end**********************");
		}catch(Exception e){
			LOGGER.error("IssueAppHealthyJob error.",e);
		}
		
	}
}
