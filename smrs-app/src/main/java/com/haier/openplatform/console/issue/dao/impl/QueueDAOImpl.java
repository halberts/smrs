package com.haier.openplatform.console.issue.dao.impl;

import com.haier.openplatform.console.issue.dao.QueueDAO;
import com.haier.openplatform.console.issue.domain.HopIssueDetailEx;
import com.haier.openplatform.console.issue.domain.HopIssueQueue;
import com.haier.openplatform.console.issue.domain.ServiceApiStatusQueue;
import com.haier.openplatform.dao.hibernate.BaseDAOHibernateImpl;

/**
 * @author Mike Yang
 */
public class QueueDAOImpl extends BaseDAOHibernateImpl<Object, Long> implements QueueDAO {

	@Override
	public void saveServiceQueue(ServiceApiStatusQueue pojo) {
		save(pojo);
	}

	@Override
	public void saveIssueQueue(HopIssueQueue pojo) {
		save(pojo);
	}

	@Override
	public void saveHopIssueDetailEx(HopIssueDetailEx pojo) {
		save(pojo);
	}

}
