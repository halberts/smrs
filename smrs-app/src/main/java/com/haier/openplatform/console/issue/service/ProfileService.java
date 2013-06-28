package com.haier.openplatform.console.issue.service;

import java.util.Date;
import java.util.List;

import com.haier.openplatform.console.domain.Profile;
import com.haier.openplatform.console.issue.domain.ProfileBeanInfo;
import com.haier.openplatform.console.issue.domain.ProfileInfo;
import com.haier.openplatform.console.issue.domain.StatisticsAction;
import com.haier.openplatform.console.issue.module.ActionAverageTime;
import com.haier.openplatform.console.issue.module.ActionAverageTimeSearchModel;
import com.haier.openplatform.console.issue.module.ProfileSearchModel;
import com.haier.openplatform.util.Pager;

/**
 * @author WangXuzheng
 *
 */
public interface ProfileService {
	/**
	 * 保存监控信息到DB中
	 * @param profile
	 */
	public void saveProfileBeanInfo(Profile profile);
	/**
	 * 查询监控action信息
	 * @param searchModel
	 * @return
	 */
	public Pager<ProfileInfo> searchProfileList(ProfileSearchModel searchModel);
	
	/**
	 * 查询某个profileBeanInfo下的直接子节点元素
	 * @param parentId
	 * @return
	 */
	public List<ProfileBeanInfo> getChildProfileBeanInfoList(long parentId);
	
	/**
	 * 查询每个系统所有Action的平均响应时间
	 * @return
	 */
	public List<ActionAverageTime> getAverageTime(Date start,Date end);
	/**
	 * 全业务Action调用频次和调用平均时间统计
	 * @param appId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<ActionAverageTime> getAllActionCallGroupByApp(Long appId,Date start,Date end);
	
	
	/**
	 * 查询平均响应时间前十个速度慢的Action
	 * @param searchModel
	 * @return
	 */
	public Pager<ActionAverageTime> searchTopTenProfileList(ActionAverageTimeSearchModel searchModel);

	/**
	 * 检查应用系统的action平均响应时间
	 * @param start
	 * @param end
	 */
	public void checkAppAverageTime(Date start,Date end);
	
	/**
	 * 根据统计类型查询最新的action统计结果
	 * @param appId          应用id,可为null
	 * @param statisticsType
	 * @param statisticsNum
	 * @return
	 */
	public List<StatisticsAction> queryActionAvgTime(Long appId,Integer statisticsType,
									Integer statisticsNum);
	/**
	 * 新增数据
	 * @param statisticsAction
	 */
	public void saveActionAvgTime(StatisticsAction statisticsAction);
	
	/**
	 * 根据统计维度新增
	 * @param list
	 * @param statisticsType
	 */
	public void saveActionAvgTime(List<ActionAverageTime> list,Integer statisticsType);
	
	/**
	 * 根据统计的业务类型和时间类型新增
	 * @param list                新增数据
	 * @param statisticsType      统计的业务类型
	 * @param statisticsTimeType  统计的时间类型
	 */
	public void saveStatisticsSystem(List<ActionAverageTime> list,Integer statisticsType,Integer statisticsTimeType);
	
	/**
	 * 查询某时间段内各系统的action平均响应时间
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<ActionAverageTime> getSystemActionAvgTime(Date begin,Date end);
	
}
