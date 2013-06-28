package com.haier.openplatform.console.quartz.service.impl;

import java.util.List;

import com.haier.openplatform.console.quartz.dao.JobTraceDAO;
import com.haier.openplatform.console.quartz.domain.JobTrace;
import com.haier.openplatform.console.quartz.service.JobTraceService;
import com.haier.openplatform.util.Pager;

public class JobTraceServiceImpl implements JobTraceService {
	private JobTraceDAO jobTraceDAO;
	
	public void setJobTraceDAO(JobTraceDAO jobTraceDAO) {
		this.jobTraceDAO = jobTraceDAO;
	}

	@Override
	public Pager<JobTrace> getJobTraceListByJobPlanId(long jobPlanId, Pager<JobTrace> pager) {
		List<JobTrace> records = jobTraceDAO.getJobTraceListByJobPlanId(jobPlanId, pager);
		long totalRecords = jobTraceDAO.getJobTraceListByJobPlanIdCount(jobPlanId);
		Pager<JobTrace> result = Pager.cloneFromPager(pager, totalRecords, records);
		return result;
	}

}
