package com.haier.openplatform.console.quartz.webapp.action;

import com.haier.openplatform.console.quartz.domain.JobPlan;
import com.haier.openplatform.util.Pager;

/**
 * 查询任务执行计划
 * @author WangXuzheng
 *
 */
public class SearchJobPlanAction extends BaseQuartzAction {
	private static final long serialVersionUID = 3200249749789635027L;
	private Pager<JobPlan> pager = new Pager<JobPlan>();
	private String appName;
	@Override
	public String execute() throws Exception {
		this.pager = this.jobPlanService.getJobPlanByAppName(appName, pager);
		return SUCCESS;
	}
	public Pager<JobPlan> getPager() {
		return pager;
	}
	public void setPager(Pager<JobPlan> pager) {
		this.pager = pager;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
}
