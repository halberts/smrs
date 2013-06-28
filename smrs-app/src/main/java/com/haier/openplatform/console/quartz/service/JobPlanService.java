package com.haier.openplatform.console.quartz.service;

import com.haier.openplatform.console.quartz.domain.JobPlan;
import com.haier.openplatform.util.Pager;

public interface JobPlanService {
	public Pager<JobPlan> getJobPlanByAppName(String appName,Pager<JobPlan> pager);
}
