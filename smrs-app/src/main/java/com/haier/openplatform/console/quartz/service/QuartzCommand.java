package com.haier.openplatform.console.quartz.service;

import java.util.Map;

import com.haier.openplatform.console.quartz.dao.JobPlanDAO;
import com.haier.openplatform.console.quartz.dao.JobTraceDAO;

public abstract class QuartzCommand {
	protected JobPlanDAO jobPlanDAO;
	protected JobTraceDAO jobTraceDAO;
	public void setJobPlanDAO(JobPlanDAO jobPlanDAO) {
		this.jobPlanDAO = jobPlanDAO;
	}

	public void setJobTraceDAO(JobTraceDAO jobTraceDAO) {
		this.jobTraceDAO = jobTraceDAO;
	}

	public abstract void execute(Map<String,String> dataMap);
}
