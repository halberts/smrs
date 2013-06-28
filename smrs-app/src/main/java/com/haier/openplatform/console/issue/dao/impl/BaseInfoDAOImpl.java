package com.haier.openplatform.console.issue.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.haier.openplatform.console.issue.dao.BaseInfoDAO;
import com.haier.openplatform.console.issue.domain.AppLists;
import com.haier.openplatform.console.issue.domain.BusinessService;
import com.haier.openplatform.console.issue.domain.IssueSupporter;
import com.haier.openplatform.console.issue.domain.ReleaseTracerConf;
import com.haier.openplatform.console.issue.domain.ServiceTimeoutLv;
import com.haier.openplatform.console.issue.module.AppIssueSupporter;
import com.haier.openplatform.console.issue.module.LevelLocated;
import com.haier.openplatform.dao.hibernate.BaseDAOHibernateImpl;
import com.haier.openplatform.util.Pager;

/**
 * @author shanjing
 */
public class BaseInfoDAOImpl extends BaseDAOHibernateImpl<Object, Long> implements BaseInfoDAO {

	@Override
	public List<AppLists> getAppLists() {
		String hql = " from AppLists al where al.id >= 50 order by al.appName";
		@SuppressWarnings("unchecked")
		List<AppLists> rs = (List<AppLists>) getSession().createQuery(hql).list();
		return rs;
	}

	@Override
	public List<ServiceTimeoutLv> getServiceTimeoutLv() {
		String hql = " from ServiceTimeoutLv stl ";
		@SuppressWarnings("unchecked")
		List<ServiceTimeoutLv> rs = (List<ServiceTimeoutLv>) getSession().createQuery(hql).list();
		return rs;
	}

	@Override
	public Pager<BusinessService> getBusinessService(Long appId, String serviceName, Pager<BusinessService> pager) {
		final Map<String, Object> param = new HashMap<String, Object>();
		param.put("appId", appId);
		param.put("serviceName", serviceName);
		String key = "issue.base.businessService.pager";
		Pager<BusinessService> rs = findPageByNamedQuery(key, pager, param);
		return rs;
	}

	@Override
	public List<BusinessService> getBusinessService(Long appId, String serviceName) {
		final Map<String, Object> param = new HashMap<String, Object>();
		param.put("appId", appId);
		param.put("serviceName", serviceName);
		String key = "issue.base.businessService.pager";
		List<BusinessService> rs = findByNamedQuery(key, param); 
		return rs;
	}

	@Override
	public void saveServiceLevel(List<Long> serviceIds, String level) {
		String hql = " update BusinessService bs set bs.overtimeLv = :level where bs.id in :ids ";
		Query query = getSession().createQuery(hql);
		query.setString("level", level).setParameterList("ids", serviceIds);
		query.executeUpdate();
	}

	@Override
	public void saveApplevel(Long appId, String level) {
		String hql = " update BusinessService bs set bs.overtimeLv = :level where bs.appId = :appId";
		Query query = getSession().createQuery(hql).setString("level", level);
		query.setLong("appId", appId);
		query.executeUpdate();
	}

	@Override
	public void saveServiceAlert(List<Long> serviceIds, Long alertMax) {
		String hql = " update BusinessService bs set bs.alertMax = :alertMax where bs.id in :ids ";
		Query query = getSession().createQuery(hql);
		query.setLong("alertMax", alertMax).setParameterList("ids", serviceIds);
		query.executeUpdate();
	}

	@Override
	public void saveServiceVip(List<Long> serviceIds) {
		String hql = " update BusinessService bs set bs.serviceVip = 1 where bs.id in :ids ";
		Query query = getSession().createQuery(hql);
		query.setParameterList("ids", serviceIds);
		query.executeUpdate();
	}

	@Override
	public ReleaseTracerConf getReleaseTraceConf(Long id) {
		String hql = " from ReleaseTracerConf rtc where rtc.id = :id ";
		Query query = getSession().createQuery(hql).setLong("id", id);
		return (ReleaseTracerConf) query.uniqueResult();
	}

	@Override
	public void saveReleaseTraceConf(ReleaseTracerConf rtc) {
		save(rtc);
	}

	@Override
	public void updateReleaseTraceConf(ReleaseTracerConf rtc) {
		update(rtc);
	}

	@Override
	public void activeReleaseTraceConf(ReleaseTracerConf rtc) {
		ReleaseTracerConf tmp = getReleaseTraceConf(rtc.getId());
		tmp.setActiveFlag(rtc.getActiveFlag());
		update(tmp);
	}

	@Override
	public void deleteReleaseTraceConf(long rtcId) {
		ReleaseTracerConf tmp = getReleaseTraceConf(rtcId);
		if (tmp.getActiveFlag() != null && !"1".equals(tmp.getActiveFlag())) {
			delete(tmp);
		}

	}

	@Override
	public List<ReleaseTracerConf> getReleaseTraceConfs(Long appId) {
		String hql = " from ReleaseTracerConf rtc where rtc.traceApp = :appId ";
		Query query = getSession().createQuery(hql).setLong("appId", appId);
		@SuppressWarnings("unchecked")
		List<ReleaseTracerConf> rs = (List<ReleaseTracerConf>) query.list();
		return rs;
	}

	@Override
	public IssueSupporter getIssueSupporter(Long id) {
		String hql = " from IssueSupporter iss where iss.id = :id ";
		Query query = getSession().createQuery(hql).setLong("id", id);
		return (IssueSupporter) query.uniqueResult();
	}

	@Override
	public void saveIssueSupporter(IssueSupporter is, Long appId) {
		save(is);
		updateAppLists(appId, is.getId());
	}

	@Override
	public void updateIssueSupporter(IssueSupporter is) {
		update(is);
	}

	@Override
	public void updateAppLists(Long appId, Long stpId) {
		String hql = "from AppLists al where al.id = :id";
		AppLists app = (AppLists) getSession().createQuery(hql).setLong("id", appId).uniqueResult();
		app.setSptId(stpId);
		getSession().save(app);
	}

	@Override
	public void deleteIssueSupporter(Long id) {
		IssueSupporter tmp = getIssueSupporter(id);
		delete(tmp);
	}

	@Override
	public List<IssueSupporter> getIssueSupporters(Long appId) {
		String hql = "select iss from IssueSupporter iss , AppLists al where al.sptId = iss.id and al.id = :appId ";
		Query query = getSession().createQuery(hql).setLong("appId", appId);
		@SuppressWarnings("unchecked")
		List<IssueSupporter> rs = (List<IssueSupporter>) query.list();
		return rs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BusinessService> getVipService(long appId) {
		String hql = " from BusinessService bs where bs.serviceVip = '1' and bs.appId = :appId ";
		Query query = getSession().createQuery(hql).setLong("appId", appId);
		return (List<BusinessService>) query.list();
	}

	@Override
	public boolean addAppName(String appName) {
		if(getAppByName(appName) != null){
			return false;
		}
		AppLists al = new AppLists();
		al.setAppName(appName);
		save(al);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public AppLists getAppById(Long appId){
		String hql = " from AppLists a where a.id = :id ";
		Query query = getSession().createQuery(hql).setLong("id", appId);
		List<AppLists> alist = (List<AppLists>) query.list();
		if (alist.isEmpty()) {
			return null;
		}
		return alist.get(0);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public AppLists getAppByName(String appName) {
		String hql = " from AppLists a where a.appName = :appName ";
		Query query = getSession().createQuery(hql).setString("appName", appName);
		List<AppLists> alist = (List<AppLists>) query.list();
		if (alist.isEmpty()) {
			return null;
		}
		return alist.get(0);
	}

	public List<AppIssueSupporter> getAppIssueSupporter(){ 
		return findByNamedQuery("issue.app.appissuesupporter", null);   
	}  
	
	@Override
	public Long getAppLevel(String levelName,Long appId){  
		final Map<String, Object> param = new HashMap<String, Object>();
		param.put("levelName", levelName);
		param.put("appId", appId); 
		List<LevelLocated> rs = findByNamedQuery("issue.app.applevel.located", param);
		return rs.get(0).getNum(); 
		 
	}

	@Override
	public List<IssueSupporter> getIssueSupporterList() {
		return findByNamedQuery("issue.app.getIssueSupporterList", null);
	}
}
