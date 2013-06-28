package com.haier.openplatform.console.jmx.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 定时获取hornetq中的queue保存到数据库中
 * @author WangJian
 */
public class JmxGetQueueForHornetJob implements Job { 

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
//		JmxResourcesService jmxResourcesService = SpringApplicationContextHolder.getBean("jmxResourcesService");
//		jmxResourcesService.saveAllResourcesParameters();
	}

}
