package com.haier.openplatform.console.issue.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.openplatform.console.issue.domain.StatisticsAction;
import com.haier.openplatform.dao.CommonDAO;

public interface StatisticsActionDAO extends CommonDAO<StatisticsAction, Long>{

	/**
	 * 查询周/月最近的生成时间
	 * @param appId  应用id,可为null
	 * @param statisticsType
	 * @param statisticsNum
	 * @return
	 */
	public String queryActionMaxCreateTime(@Param("appId")Long appId,@Param("statisticsType")Integer statisticsType,
			@Param("statisticsNum")Integer statisticsNum);
	/**
	 * 根据统计类型查询最新的action统计结果
	 * @param appId          应用id,可为null
	 * @param statisticsType
	 * @param statisticsNum
	 * @param maxCreateTime 格式yyyy-MM-dd hh:mi:ss
	 * @return
	 */
	public List<StatisticsAction> queryActionAvgTime(@Param("appId")Long appId,@Param("statisticsType")Integer statisticsType,
					@Param("statisticsNum")Integer statisticsNum,@Param("maxCreateTime")String maxCreateTime);
	
	/**
	 * 新增数据
	 * @param statisticsAction
	 */
	public void saveActionAvgTime(StatisticsAction statisticsAction);
}
