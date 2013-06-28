package com.haier.openplatform.console.quartz.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haier.openplatform.console.quartz.domain.JobPlan;
import com.haier.openplatform.console.quartz.domain.JobTrace;
import com.haier.openplatform.console.quartz.service.QuartzCommand;
import com.haier.openplatform.console.quartz.util.JobMapUtils;

/**
 * @author WangXuzheng
 *
 */
public class QuartzJobToBeExecutedCommand extends QuartzCommand {
	private static final Logger LOGGER = LoggerFactory.getLogger(QuartzJobToBeExecutedCommand.class);
	@Override
	public void execute(Map<String, String> dataMap) {
		//保存执行理事记录，设置状态为开始运行
		String schedulerName = dataMap.get("schedulerName");
		String jobGroup = dataMap.get("jobGroup");
		String jobName = dataMap.get("jobName");
		String cronExpression = dataMap.get("cronExpression");
		JobPlan dbJobPlan = jobPlanDAO.getJobPlan(schedulerName, jobGroup, jobName, cronExpression);
		if(dbJobPlan == null){
			LOGGER.error("Job plan is null,schedulerName:{},jobGroup:{},jobName:{},cronExpression{}",schedulerName,jobGroup,jobName,cronExpression);
			QuartzRecoveryStore.addToRecoveryStore(dataMap);
		}else{
			JobTrace jobTrace = JobMapUtils.createJobTrace(dataMap);
			jobTrace.setState("jobToBeExecuted");
			jobTrace.setJobPlan(dbJobPlan);
			this.jobTraceDAO.save(jobTrace);
		}
	}

}
