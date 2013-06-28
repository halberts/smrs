package com.haier.openplatform.console.issue.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.haier.openplatform.console.issue.domain.BusinessService;
import com.haier.openplatform.console.issue.domain.BusinessServiceGrp;
import com.haier.openplatform.console.issue.module.ApiCallGroupByLevel;
import com.haier.openplatform.console.issue.module.AppApiIssue;
import com.haier.openplatform.console.issue.module.AppCall;
import com.haier.openplatform.console.issue.module.AppIssueSearcher;
import com.haier.openplatform.console.issue.module.AppOvertime;
import com.haier.openplatform.console.issue.module.IssueType;
import com.haier.openplatform.console.issue.module.ServiceGroupIssue;
import com.haier.openplatform.console.issue.module.ShowIssue;
import com.haier.openplatform.util.Pager;

/**
 * @author shanjing
 * 综合监控报告Service
 */
public interface ReportService {

	/**
	 * 所有业务响应横比按Level
	 * @param from：起始时间
	 * @param to：结束时间
	 */
	public List<ApiCallGroupByLevel> getAllApiOvertimeGroupByLevel();

	/**
	 * 全业务超时前10
	 * @param from：起始时间
	 * @param to：结束时间
	 * @return<系统名称,超时数量>
	 */
	public List<ApiCallGroupByLevel> getAllApiOvertimeTopTen(Date from, Date to, Long topNum);

	/**
	 * 应用系统超时前10
	 * @param appId：应用系统ID
	 * @param from：起始时间
	 * @param to：结束时间
	 */
	public Pager<AppOvertime> getAppOvertimeTopTen(Long appId, Date from, Date to, Pager<AppOvertime> pager);

	/**
	 * 全API调用频次和消耗时间
	 * @param appId：应用系统ID，为空则表示全部应用
	 * @param from：起始时间
	 * @param to：结束时间
	 */
	public List<AppCall> getAllApiCallGroupByApp(Long appId, Date from, Date to);

	/**
	 * 所有应用系统异常数量
	 * @param from：起始时间
	 * @param to：结束时间
	 * @return：<应用系统名称,异常数量>
	 */
	public Map<IssueType, Map<String, ShowIssue>> getAppIssue(Long appId, Date from, Date to);

	/**
	 * 获取某系统异常数量前10的Service
	 */
	public Pager<AppApiIssue> getAppApiIssueTopTen(AppIssueSearcher searcher);

	/**
	 * 保存服务分组
	 */
	public void addBusinessServiceGrp(String serviceGrpNm, String serviceGrpDetail, long appId);

	/**
	 * 更新服务分组
	 */
	public void updateBusinessServiceGrp(BusinessServiceGrp pojo);

	/**
	 * 删除服务分组
	 */
	public void deleteBusinessServiceGrp(long id);

	/**
	 * 查询服务分组
	 */
	public BusinessServiceGrp getBusinessServiceGrp(long id);

	/**
	 * 查询服务分组
	 */
	public List<BusinessServiceGrp> getAllBusinessServiceGrp(long appId);

	/**
	 * 按分组查询异常数量
	 */
	public List<ServiceGroupIssue> getServiceGroupIssue(Date from, Date to, long appId);

	/**
	 * 获取Service列表
	 */
	public List<BusinessService> getBusinessService(List<Long> businessServiceIds);

	/**
	 * 获取Service列表（基于appid和分组id）
	 */
	public List<BusinessService> getBusinessService(Long appId, Long groupId);
	/**
	 * 获取单个Service
	 */
	public BusinessService getBusinessService(Long id);
	
	/**
	 * 更新单个service
	 */
	public void updateBusinessService(BusinessService bs);
	
}
