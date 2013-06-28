package com.haier.openplatform.console.quartz.util;

import java.util.Date;
import java.util.Map;

import com.haier.openplatform.console.quartz.domain.JobPlan;
import com.haier.openplatform.console.quartz.domain.JobTrace;

public class JobMapUtils {
	public static JobPlan createJobPlan(Map<String,String> dataMap){
		JobPlan jobPlan = new JobPlan();
		jobPlan.setCalenderName(dataMap.get("calendarName"));
		jobPlan.setCronExpression(dataMap.get("cronExpression"));
		jobPlan.setGmtCreate(new Date());
		jobPlan.setGmtModified(new Date());
		jobPlan.setJobGroup(dataMap.get("jobGroup"));
		jobPlan.setJobName(dataMap.get("jobName"));
		jobPlan.setJobDescription(dataMap.get("jobDescription"));
		jobPlan.setScheduledDate(new Date(Long.valueOf(dataMap.get("scheduledDate"))));
		jobPlan.setSchedulerName(dataMap.get("schedulerName"));
		jobPlan.setSchedulerVersion(dataMap.get("schedulerVersion"));
		jobPlan.setTriggerGroup(dataMap.get("triggerGroup"));
		jobPlan.setTriggerName(dataMap.get("triggerName"));
		jobPlan.setTriggerType(dataMap.get("triggerType"));
		jobPlan.setSchedulerInstanceId(dataMap.get("schedulerInstanceId"));
		jobPlan.setHost(dataMap.get("host"));
		jobPlan.setAppName(dataMap.get("appName"));
		return jobPlan;
	}
	
	public static JobTrace createJobTrace(Map<String, String> dataMap){
		JobTrace jobTrace = new JobTrace();
		jobTrace.setId(dataMap.get("UUID"));
		jobTrace.setGmtCreate(new Date());
		jobTrace.setGmtModified(new Date());
		jobTrace.setCalendarName(dataMap.get("calendarName"));
		jobTrace.setEndTime(new Date(Long.valueOf(dataMap.get("endTime"))));
		jobTrace.setErrorMessage(dataMap.get("errorMessage"));
		jobTrace.setFinalFireTime(new Date(Long.valueOf(dataMap.get("finalFireTime"))));
		jobTrace.setHost(dataMap.get("host"));
		jobTrace.setIsRecovering(Integer.valueOf(dataMap.get("isRecovering")));
		jobTrace.setJobRunTime(Long.valueOf(dataMap.get("jobRunTime")));
		jobTrace.setPreviousFireTime(new Date(Long.valueOf(dataMap.get("previousFireTime"))));
		jobTrace.setNextFireTime(new Date(Long.valueOf(dataMap.get("nextFireTime"))));
		jobTrace.setPreviousFireTime(new Date(Long.valueOf(dataMap.get("previousFireTime"))));
		jobTrace.setRefireCount(Integer.valueOf(dataMap.get("refireCount")));
		jobTrace.setScheduledFireTime(new Date(Long.valueOf(dataMap.get("scheduledFireTime"))));
		jobTrace.setStartTime(new Date(Long.valueOf(dataMap.get("startTime"))));
		jobTrace.setState(dataMap.get("state"));
		jobTrace.setAppName(dataMap.get("appName"));
		return jobTrace;
	}
}
