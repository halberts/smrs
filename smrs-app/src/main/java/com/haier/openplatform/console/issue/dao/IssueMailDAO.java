package com.haier.openplatform.console.issue.dao;

import java.util.List;

import com.haier.openplatform.console.issue.domain.HopIssueDetail;
import com.haier.openplatform.console.issue.module.IssueModel;
import com.haier.openplatform.dao.BaseDAO;

/**
 * @author shanjing 监控异常数量DAO
 * 
 */
public interface IssueMailDAO extends BaseDAO<HopIssueDetail, Long> {

	/**
	 * 待发送邮件异常列表 按Service的异常，每10分钟的查询结果，发送邮件
	 */
	public List<IssueModel> queryUnsentIssues();
	/**
	 * query only for overtime issues
	 */
	public List<IssueModel> queryOvertimeIssues();
	/**
	 * 标记为已发送
	 */
	public void updateEmailStatus(List<Long> issueIds);

	public void updateSMSStatus(List<Long> issueIds);

}
