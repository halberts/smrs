package com.haier.openplatform.console.issue.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.openplatform.console.issue.domain.ProfileInfo;
import com.haier.openplatform.console.issue.module.ActionAverageTime;
import com.haier.openplatform.console.issue.module.ActionAverageTimeSearchModel;
import com.haier.openplatform.console.issue.module.ProfileSearchModel;
import com.haier.openplatform.dao.CommonDAO;

/**
 * @author WangXuzheng
 *
 */
public interface ProfileInfoDAO extends CommonDAO<ProfileInfo, Long> {
	public void save(@Param("profileInfo") ProfileInfo profileInfo,@Param("tableSuffix")String tableSuffx);
	/**
	 * 查询监控action信息
	 * @param searchModel
	 * @return
	 */
	public List<ProfileInfo> searchProfileList(@Param("searchModel") ProfileSearchModel searchModel,@Param("tableSuffix")String tableSuffix);
	public Long searchProfileListCount(@Param("searchModel") ProfileSearchModel searchModel,@Param("tableSuffix")String tableSuffx);
	
	/**
	 * 查询所有系统所有Action平均响应时间
	 * @param start
	 * @param end
	 * @return
	 */
	public List<ActionAverageTime> getAverageTime(@Param("start")Date start,@Param("end")Date end,@Param("tableSuffix")String tableSuffix);
	
	/**
	 * 查询单个系统所有Action平均响应时间以及调用频次
	 * @param start
	 * @param end
	 * @return
	 */
	public List<ActionAverageTime> getAllActionCallGroupByApp(@Param("appId")Long appId,@Param("start")Date start,@Param("end") Date end,@Param("tableSuffix")String tableSuffix);
	
	/**
	 * 查询平均时间超时前十的Action
	 * @param searchModel
	 * @return
	 */
	public List<ActionAverageTime> searchTopTenProfileList(@Param("searchModel")ActionAverageTimeSearchModel searchModel,@Param("tableSuffix")String tableSuffix);
	public long searchTopTenProfileListCount(@Param("searchModel")ActionAverageTimeSearchModel searchModel,@Param("tableSuffix")String tableSuffix);
	
	/**
	 * 查询各业务系统所有Action平均响应时间
	 * @param tableSuffix  表名后缀
	 * @param begin        开始时间 格式 yyyy-MM-dd
	 * @param end          结束时间 格式 yyyy-MM-dd
	 * @return
	 */
	public List<ActionAverageTime> getSystemActionAvgTime(@Param("tableSuffix")String tableSuffix,@Param("begin")String begin,@Param("end")String end);
	
}
