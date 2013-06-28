package com.haier.openplatform.console.issue.quartz;
/**
 * 自动设置level定时任务,每周自动计算一次
 */
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haier.openplatform.console.issue.service.AppMonitorService;
import com.haier.openplatform.util.SpringApplicationContextHolder;

public class LevelAutoSetJob implements Job{
	private static final Logger LOGGER = LoggerFactory.getLogger(IssueAppHealthyJob.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try{
			AppMonitorService appMonitorsvr = SpringApplicationContextHolder.getBean("appMonitorService");
			appMonitorsvr.getServiceLv();
		}catch (Exception e){
			LOGGER.error("自动设置level定时任务,LevelAutoSetJob执行时出现错误",e);
		} 
	}
}
