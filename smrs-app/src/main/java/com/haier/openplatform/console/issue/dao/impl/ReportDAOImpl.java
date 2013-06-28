package com.haier.openplatform.console.issue.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.haier.openplatform.console.issue.dao.ReportDAO;
import com.haier.openplatform.console.issue.domain.BusinessService;
import com.haier.openplatform.console.issue.domain.BusinessServiceGrp;
import com.haier.openplatform.console.issue.module.ApiCallGroupByLevel;
import com.haier.openplatform.console.issue.module.AppApiIssue;
import com.haier.openplatform.console.issue.module.AppCall;
import com.haier.openplatform.console.issue.module.AppIssueSearcher;
import com.haier.openplatform.console.issue.module.AppOvertime;
import com.haier.openplatform.console.issue.module.ServiceGroupIssue;
import com.haier.openplatform.console.issue.util.MonitorUtil;
import com.haier.openplatform.dao.hibernate.BaseDAOHibernateImpl;
import com.haier.openplatform.util.Pager;

/**
 * @author shanjing
 */
public class ReportDAOImpl extends BaseDAOHibernateImpl<Object, Long> implements ReportDAO {

	@Override
	public List<ApiCallGroupByLevel> getAllApiOvertimeGroupByLevel() {
		final Map<String, Object> param = new HashMap<String, Object>();
		String key = "issue.report.getAllApiOvertimeGroupByLevel";
		return findByNamedQuery(key, param);
	}

	@Override
	public List<ApiCallGroupByLevel> getAllApiOvertimeTopTen(Date from, Date to, Long topNum) {
		final Map<String, Object> param = new HashMap<String, Object>();
		param.put("from", Long.valueOf(MonitorUtil.getTimeId(from)));
		param.put("to", Long.valueOf(MonitorUtil.getTimeId(to)));
		if (topNum == null || topNum.longValue() <= 0) {
			param.put("topNum", Long.valueOf(10));
		} else {
			param.put("topNum", topNum);
		}
		String key = "issue.report.getAllApiOvertimeTopTen";
		return findByNamedQuery(key, param);
	}

	@Override
	public Pager<AppOvertime> getAppOvertimeTopTen(Long appId, Date from, Date to, Pager<AppOvertime> pager) {
		final Map<String, Object> param = new HashMap<String, Object>();
		param.put("from", Long.valueOf(MonitorUtil.getTimeId(from)));
		param.put("to", Long.valueOf(MonitorUtil.getTimeId(to)));
		param.put("appId", appId);
		String key = "issue.report.getAppOvertimeTopTen";
		return findPageByNamedQuery(key, pager, param);
	}

	@Override
	public List<AppCall> getAllApiCallGroupByApp(Long appId, Date from, Date to) {
		final Map<String, Object> param = new HashMap<String, Object>();
		param.put("from", Long.valueOf(MonitorUtil.getTimeId(from)));
		param.put("to", Long.valueOf(MonitorUtil.getTimeId(to)));
		param.put("appId", appId);
		String key = "issue.report.getAllApiCallGroupByApp";
		return findByNamedQuery(key, param);
	}

	@Override
	public List<AppCall> getAppIssue(Long appId, Date from, Date to) {
		final Map<String, Object> param = new HashMap<String, Object>();
		param.put("from", Long.valueOf(MonitorUtil.getTimeId(from)));
		param.put("to", Long.valueOf(MonitorUtil.getTimeId(to)));
		param.put("appId", appId);
		String key = "issue.report.getAppIssue";
		return findByNamedQuery(key, param);
	}

	@Override
	public Pager<AppApiIssue> getAppApiIssueTopTen(AppIssueSearcher searcher) {
		final Map<String, Object> param = new HashMap<String, Object>();
		param.put("from", Long.valueOf(MonitorUtil.getTimeId(searcher.getFrom())));
		param.put("to", Long.valueOf(MonitorUtil.getTimeId(searcher.getTo())));
		param.put("appId", searcher.getAppId());
		param.put("issueTypeId", searcher.getIssueTypeId());
		String key = "issue.report.getAppApiIssueTopTen";
		return findPageByNamedQuery(key, searcher.getPager(), param);
	}

	@Override
	public void addBusinessServiceGrp(String serviceGrpNm, String serviceGrpDetail, long appId) {
		BusinessServiceGrp bsg = new BusinessServiceGrp();
		bsg.setServiceGrpName(serviceGrpNm);
		bsg.setServiceGrpDetail(serviceGrpDetail);
		bsg.setGmtCreate(new Date());
		bsg.setAppId(appId);
		save(bsg);
	}

	@Override
	public void updateBusinessServiceGrp(BusinessServiceGrp pojo) {
		update(pojo);
	}

	@Override
	public void deleteBusinessServiceGrp(long id) {
		resetBusinessServiceGrpId(id);
		delete(getBusinessServiceGrp(id));
	}

	@Override
	public void resetBusinessServiceGrpId(Long groupId) {
		String hql = " update BusinessService bs set bs.serviceGrpId = 1 where bs.serviceGrpId = :groupid ";
		Query query = getSession().createQuery(hql);
		query.setLong("groupid", groupId);
		query.executeUpdate();
	}

	@Override
	public BusinessServiceGrp getBusinessServiceGrp(long id) {
		String hql = " from BusinessServiceGrp bsg where bsg.id = :id ";
		Query query = getSession().createQuery(hql);
		query.setLong("id", id);
		return (BusinessServiceGrp) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BusinessServiceGrp> getAllBusinessServiceGrp(long appId) {
		String hql = " from BusinessServiceGrp bsg where bsg.appId = :appid ";
		Query query = getSession().createQuery(hql);
		query.setLong("appid", appId);
		return (List<BusinessServiceGrp>) query.list();
	}

	@Override
	public List<ServiceGroupIssue> getServiceGroupIssue(Date from, Date to, long appId) {
		final Map<String, Object> param = new HashMap<String, Object>();
		param.put("from", Long.valueOf(MonitorUtil.getTimeId(from)));
		param.put("to", Long.valueOf(MonitorUtil.getTimeId(to)));
		param.put("appId", appId);
		String key = "issue.rpt.servicegroupissue";
		return findByNamedQuery(key, param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BusinessService> getBusinessService(List<Long> businessServiceIds) {
		String hql = " from BusinessService bs where bs.id in :ids ";
		Query query = getSession().createQuery(hql);
		query.setParameterList("ids", businessServiceIds);
		return (List<BusinessService>) query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BusinessService> getBusinessService(Long appId, Long groupId) {
		String hql = " from BusinessService bs where bs.appId = :appid and bs.serviceGrpId = :groupid ";
		Query query = getSession().createQuery(hql);
		query.setLong("appid", appId);
		query.setLong("groupid", groupId);
		return (List<BusinessService>) query.list();
	}

	@Override
	public BusinessService getBusinessService(Long id) {
		String hql = " from BusinessService bs where bs.id = :id ";
		Query query = getSession().createQuery(hql).setLong("id", id);
		return (BusinessService) query.uniqueResult();
	}

	@Override
	public void updateBusinessService(BusinessService bs) {
		update(bs);
	}

}
