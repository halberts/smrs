package com.haier.openplatform.console.issue.quartz;

import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haier.openplatform.console.issue.domain.enu.StatisticsTimeTypeEnum;
import com.haier.openplatform.console.issue.domain.enu.StatisticsTypeEnum;
import com.haier.openplatform.console.issue.module.ActionAverageTime;
import com.haier.openplatform.console.issue.service.ProfileService;
import com.haier.openplatform.console.issue.util.DateUtil;
import com.haier.openplatform.util.SpringApplicationContextHolder;
import com.smrs.util.DateCommonUtil;

public class StatisticsActionAvgTimeJob implements Job{

	private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsActionAvgTimeJob.class);
	private static final String DATE_PATTERN = "yyyy-MM-dd";
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try{
			ProfileService profileService = SpringApplicationContextHolder.getBean("profileService");
			//统计当月各系统action情况
			DateUtil du = new DateUtil();
			//取得当月第一天
			Date beginDate = DateCommonUtil.parse(DATE_PATTERN, du.getFirstDayOfMonth(DATE_PATTERN));
			//取得当月最后一天
			Date endDate = DateCommonUtil.parse(DATE_PATTERN, du.getDefaultDay(DATE_PATTERN));
			//取得当前月份数
			//取得当月各业务系统所有action的平均响应时间记录
			List<ActionAverageTime> list = profileService.getSystemActionAvgTime(beginDate, endDate);
			profileService.saveStatisticsSystem(list, StatisticsTypeEnum.CLASS.getType(), StatisticsTimeTypeEnum.MONTH.getType());
		}catch(Exception e){
			LOGGER.error("StatisticsSystemActionJob error.",e);
		}
	}
}
