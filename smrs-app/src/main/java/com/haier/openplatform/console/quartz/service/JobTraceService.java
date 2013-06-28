package com.haier.openplatform.console.quartz.service;

import com.haier.openplatform.console.quartz.domain.JobTrace;
import com.haier.openplatform.util.Pager;

public interface JobTraceService {
	public Pager<JobTrace> getJobTraceListByJobPlanId(long jobPlanId,Pager<JobTrace> pager);
}
