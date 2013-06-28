package com.haier.openplatform.console.quartz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.openplatform.console.quartz.domain.JobTrace;
import com.haier.openplatform.dao.CommonDAO;
import com.haier.openplatform.util.Pager;

/**
 * @author WangXuzheng
 *
 */
public interface JobTraceDAO extends CommonDAO<JobTrace, String>{
	public List<JobTrace> getJobTraceListByJobPlanId(@Param("jobPlanId")long jobPlanId,@Param("pager")Pager<JobTrace> pager);
	public Long getJobTraceListByJobPlanIdCount(long jobPlanId);
}
