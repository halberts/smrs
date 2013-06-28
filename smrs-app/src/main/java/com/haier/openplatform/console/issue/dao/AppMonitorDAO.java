package com.haier.openplatform.console.issue.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.haier.openplatform.console.issue.domain.AppHealthHistory;
import com.haier.openplatform.console.issue.domain.AppHealthUrl;
import com.haier.openplatform.console.issue.domain.AppServerChecker;
import com.haier.openplatform.console.issue.domain.HopEmail;
import com.haier.openplatform.console.issue.domain.HopSms;
import com.haier.openplatform.console.issue.module.ApiCallGroupByHour;
import com.haier.openplatform.console.issue.module.ApiSearcher;
import com.haier.openplatform.console.issue.module.AppBussiness;
import com.haier.openplatform.console.issue.module.AppHealthDetail;
import com.haier.openplatform.console.issue.module.Issue;
import com.haier.openplatform.console.issue.module.IssueDetailModule;
import com.haier.openplatform.console.issue.module.IssueSearcher;
import com.haier.openplatform.console.issue.module.Searcher;
import com.haier.openplatform.console.issue.module.ServiceApi;
import com.haier.openplatform.console.issue.module.ServiceApiAverage;
import com.haier.openplatform.util.Pager;

/**
 * @author shanjing
 * 应用系统监控DAO
 */
public interface AppMonitorDAO {

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
	public IssueDetailModule getIssueDetail(Long issueId);

	/**
	 * 查询超时前10的异常
	 */
	public Pager<Issue> getOvertimeTopTen(Searcher overtimeSearcher);

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
	public Pager<HopEmail> getHopEmail(String system, Date from, Date to, Pager<HopEmail> pager);

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
	public Pager<Issue> getLastestIssue(Long appId, Pager<Issue> pager, String param1, String param2, String param3);
	 
	/**
	 * 获取指定的server_checker记录
	 */
	public AppServerChecker getServerCheckerById(Long id);
	
	/**
	 * 更新server_checker记录
	 */
	public void updateServerChecker(AppServerChecker as); 
	
	/**
	 * 新增server_checker记录
	 */
	public void saveServerChecker(AppServerChecker as);

	/**
	 * 获取某appid下所有节点的健康状况
	 * @return 
	 */
	public List<AppServerChecker> getStatusPlot(Long appId);
	
	/**
	 * 获取某所有节点状态
	 * @return 
	 */
	public List<AppServerChecker> getAllServerChecker();
	
	/**
	 * 新增serverchcker
	 * @param appServerChecker
	 */
	public void addServerChecker(AppServerChecker appServerChecker);
	

	/**
	 * 删除serverchecker
	 * @param appServerChecker
	 */
	public void delServerChecker(Long id);
	
	/**
	 * 获取APP下所有的节点信息
	 * @param appServerChecker
	 */
	List<AppServerChecker> getServerCheckerByAppId(Long appId);
	
	/**
	 * 根据APPID获取某段时间内AppServerChecker记录
	 */ 
	public List<AppServerChecker> getServerCheckerByTime(Date startTime,Date endTime); 
	
	/**
	 * 根据AppId获取APP中有效(is_last属性为1)的的节点
	 */
	public List<AppServerChecker> getAppNodeName(Long appId);
	
	/**
	 * 根据开始结束时间以及APPID、NODE_NAME获取节点历史状态记录
	 */
	public List<AppServerChecker> getAppNodeList(Long appId, Date startTime,Date endTime , String nodeName);
	
	/**
	 *根据APPID获取某系统API列表
	 */
	public List<AppBussiness> getServiceApiByAppid(Long appId);
	
	/**
	 * 根据serviceId以及开始结束时间去除该段时间内该Service被调用的情况
	 * @param serviceApiId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<ServiceApi> getApiAverageResponseTime(Long serviceApiId,
			Date startTime, Date endTime);
	
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
	 * 新增健康检查历史记录
	 * @param appHealthHistory
	 */
	public void saveAppHealthHistory(AppHealthHistory appHealthHistory);
	
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
	 * 获取最近一个月所有service的平均响应时间
	 * @param start
	 * @param end
	 * @return
	 */
	public List<ServiceApiAverage> getAverageLv(String start,String end);

	public void updateBusinessService(Long serviceId, String overtimeLv);
}
