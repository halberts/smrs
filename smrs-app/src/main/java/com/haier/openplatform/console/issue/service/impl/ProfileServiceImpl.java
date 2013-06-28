package com.haier.openplatform.console.issue.service.impl;

import hudson.plugins.jira.soap.RemoteIssue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.haier.openplatform.console.domain.Profile;
import com.haier.openplatform.console.domain.ProfileBean;
import com.haier.openplatform.console.issue.dao.ProfileBeanInfoDAO;
import com.haier.openplatform.console.issue.dao.ProfileInfoDAO;
import com.haier.openplatform.console.issue.dao.StatisticsActionDAO;
import com.haier.openplatform.console.issue.dao.StatisticsSystemDAO;
import com.haier.openplatform.console.issue.domain.AppLists;
import com.haier.openplatform.console.issue.domain.ProfileBeanInfo;
import com.haier.openplatform.console.issue.domain.ProfileInfo;
import com.haier.openplatform.console.issue.domain.StatisticsAction;
import com.haier.openplatform.console.issue.domain.StatisticsSystem;
import com.haier.openplatform.console.issue.domain.enu.StatisticsTimeTypeEnum;
import com.haier.openplatform.console.issue.module.ActionAverageTime;
import com.haier.openplatform.console.issue.module.ActionAverageTimeSearchModel;
import com.haier.openplatform.console.issue.module.ProfileSearchModel;
import com.haier.openplatform.console.issue.service.BaseInfoService;
import com.haier.openplatform.console.issue.service.ProfileService;
import com.haier.openplatform.console.issue.util.TableSuffixGenerator;
import com.haier.openplatform.console.jira.domain.SoaSysContrast;
import com.haier.openplatform.console.jira.service.JiraService;
import com.haier.openplatform.console.jira.util.IssueTypeEnum;
import com.haier.openplatform.util.Pager;
import com.smrs.util.DateCommonUtil;
import com.smrs.util.Env;

/**
 * @author WangXuzheng
 *
 */
public class ProfileServiceImpl implements ProfileService,InitializingBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileServiceImpl.class);
	private ProfileBeanInfoDAO profileBeanInfoDAO;
	private ProfileInfoDAO profileInfoDAO;
	private StatisticsActionDAO statisticsActionDAO;
	private StatisticsSystemDAO statisticsSystemDAO;
	private BaseInfoService baseInfoService;
	private JiraService jiraService;
	private Map<String, AppLists> appNameMap = new HashMap<String, AppLists>();
	private static final double ACTION_AVERAGE_TIME = 2000;//毫秒
	@Override
	public void saveProfileBeanInfo(Profile profile) {
		AppLists app = this.appNameMap.get(profile.getSystem());
		if(app == null){
			app = baseInfoService.getAppByName(profile.getSystem());
			if(app == null){
				return;
			}
			this.appNameMap.put(app.getAppName(), app);
		}
		
		//创建top bean
		ProfileBean topMonitorBean = profile.getProfileBean();
		ProfileBeanInfo topMonitorBeanInfo = createProfileBeanInfoFromProfileBean(topMonitorBean);
		topMonitorBeanInfo.setParent(topMonitorBeanInfo);
		profileBeanInfoDAO.save(topMonitorBeanInfo,TableSuffixGenerator.generateDateSuffix());
		
		//创建profile信息
		ProfileInfo profileInfo = new ProfileInfo();
		profileInfo.setAppId(app.getId());
		profileInfo.setAppName(app.getAppName());
		profileInfo.setEndTime(new Date(profile.getEndTime()));
		profileInfo.setExecutionTime(profile.getProfileBean().getExecutionTime());
		profileInfo.setGmtCreate(new Date());
		profileInfo.setGmtModified(new Date());
		profileInfo.setHopVersion(profile.getHopVersion());
		profileInfo.setInfomationMap(profile.getInfomationMap().toString());
		profileInfo.setStartTime(new Date(profile.getStartTime()));
		profileInfo.setType(profile.getType());
		profileInfo.setProfileBeanInfo(topMonitorBeanInfo);
		profileInfoDAO.save(profileInfo,TableSuffixGenerator.generateDateSuffix());
		
		//保存子元素
		for(ProfileBean bean : profile.getProfileBean().getChildren()){
			saveChild(bean,topMonitorBeanInfo);
		}
	}
	
	private void saveChild(ProfileBean profileBean,ProfileBeanInfo parent){
		ProfileBeanInfo profileInfoBean = createProfileBeanInfoFromProfileBean(profileBean);
		profileInfoBean.setParent(parent);
		profileBeanInfoDAO.save(profileInfoBean,TableSuffixGenerator.generateDateSuffix());
		for(ProfileBean p : profileBean.getChildren()){
			saveChild(p,profileInfoBean);
		}
	}
	private ProfileBeanInfo createProfileBeanInfoFromProfileBean(ProfileBean topMonitorBean) {
		ProfileBeanInfo topMonitorBeanInfo = new ProfileBeanInfo();
		topMonitorBeanInfo.setClassName(topMonitorBean.getClassName());
		topMonitorBeanInfo.setExecutionTime(topMonitorBean.getExecutionTime());
		topMonitorBeanInfo.setGmtCreate(new Date());
		topMonitorBeanInfo.setGmtModified(new Date());
		topMonitorBeanInfo.setMethodName(topMonitorBean.getMethodName());
		topMonitorBeanInfo.setTimes(topMonitorBean.getTimes());
		return topMonitorBeanInfo;
	}
	
	@Override
	public Pager<ProfileInfo> searchProfileList(ProfileSearchModel searchModel) {
		String tableSuffix = getTableSuffix(searchModel.getFrom(),searchModel.getTo());
		long totalRecords = profileInfoDAO.searchProfileListCount(searchModel,tableSuffix);
		List<ProfileInfo> records = profileInfoDAO.searchProfileList(searchModel,tableSuffix);
		return Pager.cloneFromPager(searchModel.getPager(), totalRecords, records);
	}

	private String getTableSuffix(Date from,Date to){
		StringBuilder stringBuilder = new StringBuilder("_");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		if(to != null){//结束时间不为空以结束时间为准
			//传入的格式为2013-02-01
			stringBuilder.append(dateFormat.format(to));
		}else if(from != null){//开始时间不为空，以开始月份为准
			//传入的格式为2013-02-01
			stringBuilder.append(dateFormat.format(from));
		}else{//开始，结束时间都为空时取当前月份的数据
			stringBuilder = new StringBuilder(TableSuffixGenerator.generateDateSuffix());
		}
		return stringBuilder.toString();
	}
	@Override
	public List<ProfileBeanInfo> getChildProfileBeanInfoList(long parentId) {
		return profileBeanInfoDAO.getChildProfileBeanInfoList(parentId,TableSuffixGenerator.generateDateSuffix());
	}

	public void setProfileBeanInfoDAO(ProfileBeanInfoDAO profileBeanInfoDAO) {
		this.profileBeanInfoDAO = profileBeanInfoDAO;
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		List<AppLists> appList = baseInfoService.getAppLists();
		for(AppLists app : appList){
			this.appNameMap.put(app.getAppName(), app);
		}
	}
	public void setBaseInfoService(BaseInfoService baseInfoService) {
		this.baseInfoService = baseInfoService;
	}
	public void setProfileInfoDAO(ProfileInfoDAO profileInfoDAO) {
		this.profileInfoDAO = profileInfoDAO;
	}

	public void setStatisticsActionDAO(StatisticsActionDAO statisticsActionDAO) {
		this.statisticsActionDAO = statisticsActionDAO;
	}

	public void setStatisticsSystemDAO(StatisticsSystemDAO statisticsSystemDAO) {
		this.statisticsSystemDAO = statisticsSystemDAO;
	}
	
	public void setJiraService(JiraService jiraService) {
		this.jiraService = jiraService;
	}
	public List<ActionAverageTime> getAverageTime(Date start,Date end){
		String tableSuffix = getTableSuffix(start,end);
		return profileInfoDAO.getAverageTime(start,end,tableSuffix);
	}
	public List<ActionAverageTime> getAllActionCallGroupByApp(Long appId,Date start,Date end){
		String tableSuffix = TableSuffixGenerator.generateDateSuffix(end);
		return profileInfoDAO.getAllActionCallGroupByApp(appId,start,end,tableSuffix);
	}
	public Pager<ActionAverageTime> searchTopTenProfileList(ActionAverageTimeSearchModel searchModel){
		String tableSuffix = getTableSuffix(searchModel.getFrom(),searchModel.getTo());
		long totalRecords = profileInfoDAO.searchTopTenProfileListCount(searchModel, tableSuffix);
		List<ActionAverageTime> records = profileInfoDAO.searchTopTenProfileList(searchModel, tableSuffix);
		return Pager.cloneFromPager(searchModel.getPager(), totalRecords, records);
	}
	
	@Override
	public void checkAppAverageTime(Date start,Date end){
		if(!"production".equalsIgnoreCase(Env.getProperty(Env.ENV_TYPE))){
			return;
		}
		List<ActionAverageTime> list = this.getAverageTime(start, end);
		if(list == null || list.isEmpty()){
			return;
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 3);
		StringBuilder builder = new StringBuilder();
		for(ActionAverageTime at : list){
			int result = NumberUtils.compare(at.getAverageTime(), ACTION_AVERAGE_TIME);
			if(result <= 0){
				continue;
			}
			//大于2s则创建jira问题
			SoaSysContrast ssc = jiraService.queryJiraByAppName(at.getAppName());
			if(ssc == null){
				continue;
			}
			builder.append("HOP监控平台显示");
			builder.append(at.getAppName());
			builder.append("系统业务功能平均响应时间超过2秒，为保障用户体验，请及时优化相关代码。");
			RemoteIssue ri = jiraService.createIssue(ssc.getJiraKey(), "Action平均响应时间不达标", builder.toString(),IssueTypeEnum.ISSUE_AVERAGE_TIME.getId(), cal);
//			RemoteIssue ri = jiraService.createIssue("esptest", "Action平均响应时间不达标", builder.toString(), cal);
			if(ri != null){
				LOGGER.error(builder.toString());
			}
			builder.delete(0, builder.length());
		}
	}

	@Override
	public List<StatisticsAction> queryActionAvgTime(Long appId,Integer statisticsType,
			Integer statisticsNum) {
		if(statisticsType == null || statisticsNum == null){
			return null;
		}
		String maxCreateTime = statisticsActionDAO.queryActionMaxCreateTime(appId,statisticsType, statisticsNum);
		return statisticsActionDAO.queryActionAvgTime(appId,statisticsType, statisticsNum,maxCreateTime);
	}

	@Override
	public void saveActionAvgTime(StatisticsAction statisticsAction) {
		statisticsActionDAO.saveActionAvgTime(statisticsAction);
	}

	@Override
	public void saveActionAvgTime(List<ActionAverageTime> list,Integer statisticsType){
		if(list == null || list.isEmpty()){
			return;
		}
		StatisticsAction sa = new StatisticsAction();
		sa.setCreateBy("hop");
		sa.setLastModifiedBy("hop");
		sa.setStatisticsType(statisticsType);
		sa.setStatisticsNum(getStatisticsTime(statisticsType));
		
		for(ActionAverageTime aat : list){
			sa.setAppId(aat.getAppId());
			sa.setAppName(aat.getAppName());
			sa.setCallNum(aat.getNum());
			sa.setAverageTime(aat.getAverageTime());
			saveActionAvgTime(sa);
		}
	}

	@Override
	public void saveStatisticsSystem(List<ActionAverageTime> list,
			Integer statisticsType, Integer statisticsTimeType) {
		if(list == null || statisticsType == null || statisticsTimeType == null || list.isEmpty()){
			return;
		}
		Integer statisticsTime = getStatisticsTime(statisticsTimeType);
		StatisticsSystem ss = new StatisticsSystem();
		ss.setStatisticsTime(statisticsTime);
		ss.setStatisticsTimeType(statisticsTimeType);
		ss.setStatisticsType(statisticsType);
		ss.setCreateBy("hop");
		ss.setLastModifiedBy("hop");
		for(ActionAverageTime aa : list){
			ss.setAppId(aa.getAppId());
			ss.setStatisticsContent(aa.getActionName() + "." + aa.getActionMethod());
			ss.setStatisticsValue(String.valueOf(aa.getAverageTime()));
			statisticsSystemDAO.save(ss);
		}
	}

	private Integer getStatisticsTime(Integer statisticsTimeType){
		if(statisticsTimeType.intValue() == StatisticsTimeTypeEnum.WEEK.getType()){
			return Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
		}else if(statisticsTimeType.intValue() == StatisticsTimeTypeEnum.MONTH.getType()){
			return Calendar.getInstance().get(Calendar.MONTH) + 1;
		}else if(statisticsTimeType.intValue() == StatisticsTimeTypeEnum.YEAR.getType()){
			return Calendar.getInstance().get(Calendar.YEAR);
		}
		return 0;
	}
	
	@Override
	public List<ActionAverageTime> getSystemActionAvgTime(Date begin,Date end){
		String tableSuffix = this.getTableSuffix(begin, end);
		return profileInfoDAO.getSystemActionAvgTime(tableSuffix, DateCommonUtil.format("yyyy-MM-dd", begin), 
				DateCommonUtil.format("yyyy-MM-dd", end));
	}
}
