package com.haier.openplatform.console.quartz.webapp.action;

import com.haier.openplatform.console.quartz.service.JobPlanService;
import com.haier.openplatform.console.quartz.service.JobTraceService;
import com.smrs.webapp.action.BaseConsoleAction;

/**
 * @author WangXuzheng
 *
 */
public class BaseQuartzAction extends BaseConsoleAction {
	private static final long serialVersionUID = -1216998769184842836L;
	protected JobPlanService jobPlanService;
	protected JobTraceService jobTraceService;
	public void setJobPlanService(JobPlanService jobPlanService) {
		this.jobPlanService = jobPlanService;
	}
	public void setJobTraceService(JobTraceService jobTraceService) {
		this.jobTraceService = jobTraceService;
	}
}
