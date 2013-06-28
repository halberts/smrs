package com.haier.openplatform.console.quartz.service.impl;

import java.util.List;

import com.haier.openplatform.console.quartz.dao.JobPlanDAO;
import com.haier.openplatform.console.quartz.domain.JobPlan;
import com.haier.openplatform.console.quartz.service.JobPlanService;
import com.haier.openplatform.util.Pager;

public class JobPlanServiceImpl implements JobPlanService {
	private JobPlanDAO jobPlanDAO;
	
	public void setJobPlanDAO(JobPlanDAO jobPlanDAO) {
		this.jobPlanDAO = jobPlanDAO;
	}

	@Override
	public Pager<JobPlan> getJobPlanByAppName(String appName, Pager<JobPlan> pager) {
		List<JobPlan> records = jobPlanDAO.getJobPlanByAppName(appName, pager);
		long totalRecords = jobPlanDAO.getJobPlanByAppNameCount(appName);
		Pager<JobPlan> result = Pager.cloneFromPager(pager, totalRecords, records);
		return result;
	}

}
