package com.haier.openplatform.console.issue.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.openplatform.console.issue.domain.StatisticsSystem;
import com.haier.openplatform.dao.CommonDAO;

public interface StatisticsSystemDAO extends CommonDAO<StatisticsSystem, Long>{

	/**
	 * 统计时间1相对于时间2的appId和statisticsValue的差集
	 * @param statisticsTime1           时间1
	 * @param statisticsTime2           时间2
	 * @param statisticsType     统计类型
	 * @param statisticsTimeType 统计的时间类型
	 * @return
	 */
	public List<StatisticsSystem> queryStatisticsValueMinus(@Param("statisticsTime1")Integer statisticsTime1,@Param("statisticsTime2")Integer statisticsTime2,
			@Param("statisticsType")Integer statisticsType,@Param("statisticsTimeType")Integer statisticsTimeType);
}
