package com.haier.openplatform.console.quartz.webapp.action;

import com.haier.openplatform.console.quartz.domain.JobTrace;
import com.haier.openplatform.util.Pager;

/**
 * 查询任务执行历史
 * @author WangXuzheng
 *
 */
public class SearchJobTraceAction extends BaseQuartzAction {
	private static final long serialVersionUID = 6958177496280137913L;
	private Pager<JobTrace> pager = new Pager<JobTrace>();
	private long jobPlanId;
	@Override
	public String execute() throws Exception {
		this.pager = jobTraceService.getJobTraceListByJobPlanId(jobPlanId, pager);
		return SUCCESS;
	}
	public long getJobPlanId() {
		return jobPlanId;
	}

	public void setJobPlanId(long jobPlanId) {
		this.jobPlanId = jobPlanId;
	}

	public Pager<JobTrace> getPager() {
		return pager;
	}
	public void setPager(Pager<JobTrace> pager) {
		this.pager = pager;
	}
}
