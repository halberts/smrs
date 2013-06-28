package com.haier.openplatform.console.issue.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.haier.openplatform.console.issue.domain.AppHealthUrl;
import com.haier.openplatform.console.issue.domain.AppServerChecker;
import com.haier.openplatform.console.issue.domain.HopEmail;
import com.haier.openplatform.console.issue.domain.HopSms;
import com.haier.openplatform.console.issue.module.ApiCallGroupByHour;
import com.haier.openplatform.console.issue.module.ApiSearcher;
import com.haier.openplatform.console.issue.module.AppHealthDetail;
import com.haier.openplatform.console.issue.module.AppSummaryModel;
import com.haier.openplatform.console.issue.module.HopEmailModel;
import com.haier.openplatform.console.issue.module.Issue;
import com.haier.openplatform.console.issue.module.IssueDetail;
import com.haier.openplatform.console.issue.module.IssueSearcher;
import com.haier.openplatform.console.issue.module.Searcher;
import com.haier.openplatform.console.issue.module.ServiceApi;
import com.haier.openplatform.console.issue.module.ServiceApiAverage;
import com.haier.openplatform.util.Pager;

/**
 * @author shanjing
 * 应用系统监控Service
 */
public interface AppMonitorService {

	/**
	 * 获取应用系统各个级别Service数量
	 * @param appId:应用系统ID
	 * @return：<响应级别,数量>
	 */
	public Map<String, Long> getServiceLevel(Long appId);

	/**
	 * 获取一定时间内应用系统的超时Service的数量
	 * @param appId:应用系统ID
	 * @param from：起始时间
	 * @param to：结束时间
	 * @return:超时数量
	 */
	public Long getServiceOvertimeNum(Long appId, Date from, Date to);

	/**
	 * 按条件查询异常
	 * @return：异常列表
	 */
	public Pager<Issue> getIssueByPage(IssueSearcher issueSearcher);

	/**
	 * 获取异常详细信息
	 */
	public IssueDetail getIssueDetail(Long issueId);

	/**
	 * 查询超时前10的异常
	 */
	public Pager<Issue> getOvertimeTopTen(Searcher overtimeTopTenSearcher);

	/**
	 * 查看Service的API调用频率和消耗时间
	 */
	public Pager<ServiceApi> getServiceApiCall(ApiSearcher searcher);

	/**
	 * 统计每个小时所有API调用次数
	 * @param appId:应用系统ID:为空则统计所有应用系统
	 * @param from：起始时间
	 * @param to：结束时间
	 */
	public List<ApiCallGroupByHour> getApiCallGroupByHour(Long appId, Date from, Date to);
	
	/**
	 * 获取Email信息
	 */
	public Pager<HopEmailModel> getHopEmail(String system, Date from, Date to, Pager<HopEmail> pager);

	/**
	 * 获取短信信息
	 */
	public Pager<HopSms> getHopSms(String system, Date from, Date to, Pager<HopSms> pager);
	
	/**
	 * 获取最新的Api调用
	 */
	public Pager<ServiceApi> getLastestExecApi(Long appId, Pager<ServiceApi> pager);

	/**
	 * 获取最新的Issue
	 */
	public Pager<Issue> getLastestIssue(Long appId, Pager<Issue> pager, String param1, String param2, String param3 );
	
	/**
	 * 获取指定的server_checker记录
	 */
	public AppServerChecker getServerCheckerById(Long id);
	
	
	/**
	 * 更新server_checker记录
	 */
	public void updateServerChecker(AppServerChecker as);
	
	/**
	 * 批量更新server_checker记录
	 */	
	public void updateServerCheckers(List<AppServerChecker> as); 

	/**
	 * 新增server_checker记录
	 */
	public void saveServerChecker(AppServerChecker as);
	
	/**
	 * 新增server_checker记录
	 * @return 
	 */
	public List<AppServerChecker> getStatusPlot(Long appId);
	
	/**
	 * 获取所有节点健康状况信息
	 */
	public List<AppServerChecker> getAllServerChecker();
	
	/**
	 * 新增AppServerChecker
	 */
	public void addServerChecker(AppServerChecker appServerChecker);
	
	/**
	 * 删除AppServerChecker
	 */
	public void delServerChecker(Long id);
	
	/**
	 * 获取APP下的AppServerChecker
	 */ 
	public List<AppServerChecker> getServerCheckerByAppId(Long appId);
	
	/**
	 * 根据APPID获取某段时间内AppServerChecker记录
	 */ 
	public List<AppServerChecker> getServerCheckerByTime(Date startTime,Date endTime);
	
	/**
	 * 根据APPID以及NODE_name、开始结束时间获取NODE以及按时间排序的NODE检查列表
	 */
	public List<AppServerChecker> getNodeInfo(Long appId, Date startTime,Date endTime,String nodeName);
	
	/**
	 * 根据APPID获取检查列表
	 * @param appId
	 * @return
	 */
	public List<String> getNodeName(Long appId);
	
	/**
	 *根据APPID获取某系统API列表
	 */
	public List<ServiceApi> getServiceApiByAppid(Long appId,String date);
	
	/**
	 * 根据APIID获取API平均响应时间
	 * @param serviceApiId
	 * @param time
	 * @return
	 */
	public List<ServiceApi> getApiAverageResponseTime(Long serviceApiId,Date startTime,Date endTime);
	
	/**
	 * 应用健康检查
	 */
	public void appHealthyCheck();
	
	/**
	 * 新增健康检查url
	 * @param appHealthUrl
	 */
	public void addHealthUrl(AppHealthUrl appHealthUrl);
	
	/**
	 * 更新健康检查url
	 * @param appHealthUrl
	 */
	public void updateHealthUrl(AppHealthUrl appHealthUrl);
	
	/**
	 * 根据id删除健康检查url
	 * @param id
	 */
	public void deleteHealthUrl(Long id);
	
	/**
	 * 根据应用id和url状态查询
	 * @param appId
	 * @param status
	 * @return
	 */
	public List<AppHealthUrl> getAppHealthUrl(Long appId,Integer status);
	
	/**
	 * 根据id查询健康检查url
	 * @param id
	 * @return
	 */
	public AppHealthUrl getAppHealthUrlById(Long id);
	
	/**
	 * 通过appid查询最近的健康检查详情
	 * @param appId
	 * @return
	 */
	public List<AppHealthDetail> getLastAppHealthDetailByAppId(Long appId);
	
	/**
	 * 查询一段时间内某url的健康检查情况
	 * @param appId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<AppHealthDetail> getAppHealthDetailByidAndDate(Long id,String startDate,String endDate);
	/**
	 * 根据平均时间计算当前service处于哪个level上
	 * @param time
	 * @return
	 */
	public void getServiceLv();
	/**
	 * 获取近一天中service的平均响应时间信息
	 */
	public List<ServiceApiAverage> getAverageLv(); 
	
	public void checkUrlIsNormal(Long urlId);
	/**
	 * 获取接入监控中心的应用按时间变化曲线-按月
	 * @return key:日期，value对应月份的应用个数
	 */
	public AppSummaryModel getAppSummary();
}
