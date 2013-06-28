package com.haier.openplatform.console.quartz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.openplatform.console.quartz.domain.JobPlan;
import com.haier.openplatform.dao.CommonDAO;
import com.haier.openplatform.util.Pager;

/**
 * @author WangXuzheng
 *
 */
public interface JobPlanDAO extends CommonDAO<JobPlan, Long> {
	public JobPlan getJobPlan(@Param("schedulerName") String schedulerName, @Param("jobGroup") String jobGroup,
			@Param("jobName") String jobName, @Param("cronExpression") String cronExpression);
	public List<JobPlan> getJobPlanByAppName(@Param("appName")String appName,@Param("pager")Pager<JobPlan> pager);
	public Long getJobPlanByAppNameCount(@Param("appName")String appName);
}
