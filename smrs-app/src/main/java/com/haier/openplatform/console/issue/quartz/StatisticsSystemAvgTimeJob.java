package com.haier.openplatform.console.issue.quartz;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haier.openplatform.console.issue.domain.enu.StatisticsTimeTypeEnum;
import com.haier.openplatform.console.issue.module.ActionAverageTime;
import com.haier.openplatform.console.issue.service.ProfileService;
import com.haier.openplatform.console.issue.util.DateUtil;
import com.haier.openplatform.util.SpringApplicationContextHolder;
import com.smrs.util.DateCommonUtil;

public class StatisticsSystemAvgTimeJob implements Job{

	private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsSystemAvgTimeJob.class);
	private static final String pattern = "yyyy-MM-dd";
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try{
			LOGGER.error("************StatisticsSystemAvgTimeJob begin*************");
			ProfileService profileService = SpringApplicationContextHolder.getBean("profileService");
			DateUtil dt = new DateUtil();
			//统计本周
			String begin = dt.getMondayOFWeek(pattern);
			String end = dt.getCurrentWeekday(pattern);
			List<ActionAverageTime> weekList = profileService.getAverageTime(DateCommonUtil.parse(pattern, begin), DateCommonUtil.parse(pattern, end));
			profileService.saveActionAvgTime(weekList, StatisticsTimeTypeEnum.WEEK.getType());
			LOGGER.error("statistics system week end,weekList.size=" + (weekList == null ? 0 : weekList.size()));
			//统计本月
			begin = dt.getFirstDayOfMonth(pattern);
			end = dt.getDefaultDay(pattern);
			List<ActionAverageTime> monthList = profileService.getAverageTime(DateCommonUtil.parse(pattern, begin), DateCommonUtil.parse(pattern, end));
			profileService.saveActionAvgTime(monthList, StatisticsTimeTypeEnum.MONTH.getType());
			LOGGER.error("statistics system month end,weekList.size=" + (weekList == null ? 0 : weekList.size()));
			LOGGER.error("************StatisticsSystemAvgTimeJob end*************");
		}catch(Exception e){
			LOGGER.error("StatisticsSystemAvgTimeJob error.",e);
		}
	}

	
}
