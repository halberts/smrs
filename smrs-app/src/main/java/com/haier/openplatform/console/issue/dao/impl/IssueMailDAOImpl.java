package com.haier.openplatform.console.issue.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.util.CollectionUtils;

import com.haier.openplatform.console.issue.dao.IssueMailDAO;
import com.haier.openplatform.console.issue.domain.HopIssueDetail;
import com.haier.openplatform.console.issue.module.IssueModel;
import com.haier.openplatform.dao.hibernate.BaseDAOHibernateImpl;

public class IssueMailDAOImpl extends BaseDAOHibernateImpl<HopIssueDetail, Long> implements IssueMailDAO {
	public List<HopIssueDetail> getEmailNotSend() {
		String hql = " from HopIssueDetail hid where hid.alertStatus = '0' ";
		Query query = this.getSession().createQuery(hql).setMaxResults(100);
		@SuppressWarnings("unchecked")
		List<HopIssueDetail> list = (List<HopIssueDetail>) query.list();
		return list;
	}

	@Override
	public List<IssueModel> queryUnsentIssues() {
		final Map<String, Object> param = new HashMap<String, Object>();
		param.put("batchCount", 20);
		return findByNamedQuery("jms.send.issue.search", param);
	}

	@Override
	public List<IssueModel> queryOvertimeIssues(){
		String key = "jms.send.issue.overtime";
		return findByNamedQuery(key, new HashMap<String, Object>());		
	}
	
	@Override
	public void updateEmailStatus(List<Long> issueIds) {
		if(CollectionUtils.isEmpty(issueIds)){
			return;
		}
		StringBuffer smsHql = new StringBuffer();
		smsHql.append(" UPDATE HOP_ISSUE_DETAIL ");
		smsHql.append(" SET EMAIL_STATUS = 1 ");
		smsHql.append(String.format(" WHERE ISSUE_ID IN (%s)",StringUtils.join(issueIds, ",")));
		batchExecuteSQL(smsHql.toString());
	}

	@Override
	public void updateSMSStatus(List<Long> issueIds) {
		if(CollectionUtils.isEmpty(issueIds)){
			return;
		}
		StringBuffer smsHql = new StringBuffer();
		smsHql.append(" UPDATE HOP_ISSUE_DETAIL ");
		smsHql.append(" SET SMS_STATUS = 1 ");
		smsHql.append(String.format(" WHERE ISSUE_ID IN (%s)",StringUtils.join(issueIds, ",")));
		batchExecuteSQL(smsHql.toString());
	}

}
