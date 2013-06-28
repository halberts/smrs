package com.haier.openplatform.console.quartz.service.impl;

import java.util.Date;
import java.util.Map;

import com.haier.openplatform.console.quartz.domain.JobPlan;
import com.haier.openplatform.console.quartz.service.QuartzCommand;
import com.haier.openplatform.console.quartz.util.JobMapUtils;

/**
 * quartz启动时，创建quartz plan任务
 * @author WangXuzheng
 *
 */
public class QuartzSchedulerStartedCommand extends QuartzCommand {
	@Override
	public void execute(Map<String, String> dataMap) {
		JobPlan jobPlan = JobMapUtils.createJobPlan(dataMap);
		//判断是否存在该执行计划
		//schedulerName+jobName+jobGroup+cron
		JobPlan dbJobPlan = jobPlanDAO.getJobPlan(jobPlan.getSchedulerName(), jobPlan.getJobGroup(), jobPlan.getJobName(), jobPlan.getCronExpression());
		if(dbJobPlan == null){
			jobPlan.setGmtCreate(new Date());
			jobPlan.setGmtModified(new Date());
			jobPlanDAO.save(jobPlan);
		}else{
			dbJobPlan.setScheduledDate(jobPlan.getScheduledDate());
			dbJobPlan.setJobDescription(jobPlan.getJobDescription());
			dbJobPlan.setHost(jobPlan.getHost());
			dbJobPlan.setCalenderName(jobPlan.getCalenderName());
			jobPlanDAO.update(dbJobPlan);
		}
	}
}
