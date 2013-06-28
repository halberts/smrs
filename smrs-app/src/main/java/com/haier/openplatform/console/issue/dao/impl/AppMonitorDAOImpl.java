package com.haier.openplatform.console.issue.dao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.haier.openplatform.console.issue.dao.AppMonitorDAO;
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
import com.haier.openplatform.console.issue.util.MonitorUtil;
import com.haier.openplatform.dao.hibernate.BaseDAOHibernateImpl;
import com.haier.openplatform.util.Pager;

/**
 * @author shanjing
 */
public class AppMonitorDAOImpl extends BaseDAOHibernateImpl<Object, Long> implements AppMonitorDAO {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Map<String, Long> getServiceLevel(Long appId) {
		final Map<String, Object> param = new HashMap<String, Object>();
		param.put("appId", appId);
		String key = "issue.app.lv.tot";
		List<Object[]> ob = findByNamedQuery(key, param);
		Map<String, Long> rs = new HashMap<String, Long>();
		for (Object[] o : ob) {
			String level = (String) o[0];
			Long num = ((BigDecimal) o[1]).longValue();
			rs.put(level, num);
		}
		return rs;
	}

	@Override
	public Long getServiceOvertimeNum(Long appId, Date from, Date to) {
		final Map<String, Object> param = new HashMap<String, Object>();
		param.put("from", Long.valueOf(MonitorUtil.getTimeId(from)));
		param.put("to", Long.valueOf(MonitorUtil.getTimeId(to)));
		param.put("appId", appId);
		String key = "issue.app.overtime.num";
		List<BigDecimal> ob = findByNamedQuery(key, param);
		return ((BigDecimal) ob.get(0)).longValue();
	}

	@Override
	public Pager<Issue> getIssueByPage(IssueSearcher issueSearcher) {
		final Map<String, Object> param = new HashMap<String, Object>();
		param.put("from", Long.valueOf(MonitorUtil.getTimeId(issueSearcher.getFrom())));
		param.put("to", Long.valueOf(MonitorUtil.getTimeId(issueSearcher.getTo())));
		param.put("appId", issueSearcher.getAppId());
		param.put("serviceName", issueSearcher.getServiceName());
		param.put("issueTypeId", issueSearcher.getIssueTypeId());
		String key = "issue.app.exception.search";
		Pager<Issue> rs = findPageByNamedQuery(key, issueSearcher.getPager(), param);
		return rs;
	}

	@Override
	public IssueDetailModule getIssueDetail(Long issueId) {
		final Map<String, Object> param = new HashMap<String, Object>();
		param.put("issueId", issueId);
		String key = "issue.app.exception.detail";
		List<IssueDetailModule> rs = findByNamedQuery(key, param);
		if (rs == null || rs.size() == 0) {
			return null;
		}
		return rs.get(0);
	}

	@Override
	public Pager<Issue> getOvertimeTopTen(Searcher overtimeSearcher) {
		final Map<String, Object> param = new HashMap<String, Object>();
		param.put("from", Long.valueOf(MonitorUtil.getTimeId(overtimeSearcher.getFrom())));
		param.put("to", Long.valueOf(MonitorUtil.getTimeId(overtimeSearcher.getTo())));
		param.put("appId", overtimeSearcher.getAppId());
		param.put("serviceName", overtimeSearcher.getServiceName());
		String key = "issue.app.overtime.top10";
		Pager<Issue> rs = findPageByNamedQuery(key, overtimeSearcher.getPager(), param);
		return rs;
	}

	@Override
	public Pager<ServiceApi> getServiceApiCall(ApiSearcher searcher) {
		final Map<String, Object> param = new HashMap<String, Object>();
		param.put("from", Long.valueOf(MonitorUtil.getTimeId(searcher.getFrom())));
		param.put("to", Long.valueOf(MonitorUtil.getTimeId(searcher.getTo())));
		param.put("appId", searcher.getAppId());
		param.put("serviceName", searcher.getServiceName());
		String key = "issue.app.service.api.search";
		Pager<ServiceApi> rs = findPageByNamedQuery(key, searcher.getPager(), param);
		return rs;
	}

	@Override
	public List<ApiCallGroupByHour> getApiCallGroupByHour(Long appId, Date from, Date to) {
		final Map<String, Object> param = new HashMap<String, Object>();
		param.put("from", Long.valueOf(MonitorUtil.getTimeId(from)));
		param.put("to", Long.valueOf(MonitorUtil.getTimeId(to)));
		param.put("appId", appId);
		String key = "issue.app.service.api.24h";
		List<ApiCallGroupByHour> rs = findByNamedQuery(key, param);
		return rs;
	}

	@Override
	public Pager<HopEmail> getHopEmail(String system, Date from, Date to, Pager<HopEmail> pager) {
		final Map<String, Object> param = new HashMap<String, Object>();
		param.put("from", sdf.format(from));
		param.put("to", sdf.format(to));
		param.put("system", system);
		String key = "jms.email.sendlog_time";
		return findPageByNamedQuery(key, pager, param);
	}

	@Override
	public Pager<HopSms> getHopSms(String system, Date from, Date to, Pager<HopSms> pager) {
		final Map<String, Object> param = new HashMap<String, Object>();
		param.put("from", sdf.format(from));
		param.put("to", sdf.format(to));
		param.put("system", system);
		String key = "jms.sms.sendlog_time";
		return findPageByNamedQuery(key, pager, param);
	}

	@Override
	public Pager<ServiceApi> getLastestExecApi(Long appId, Pager<ServiceApi> pager) {
		final Map<String, Object> param = new HashMap<String, Object>();
		param.put("appId", appId);
		String key = "issue.app.service.lastest";
		Pager<ServiceApi> rs = findPageByNamedQuery(key, pager, param);
		return rs;
	}

	@Override
	public Pager<Issue> getLastestIssue(Long appId, Pager<Issue> pager, String param1, String param2, String param3) {
		final Map<String, Object> param = new HashMap<String, Object>();
		param.put("appId", appId);
		param.put("param1", param1);
		param.put("param2", param2);
		param.put("param3", param3);
		String key = "issue.app.issue.lastest";
		Pager<Issue> rs = findPageByNamedQuery(key, pager, param);
		return rs;
	}  

	@Override
	public AppServerChecker getServerCheckerById(Long id) {
		String hql = " from AppServerChecker checker where checker.id = :id ";
		Query query = getSession().createQuery(hql).setLong("id", id);
		return (AppServerChecker) query.uniqueResult();
	}

	@Override
	public void updateServerChecker(AppServerChecker as) {
		update(as);
	} 
	
	@Override
	public void saveServerChecker(AppServerChecker as){
		save(as);
	}
	
	@Override
	public List<AppServerChecker> getStatusPlot(Long appId){ 
		final Map<String, Object> param = new HashMap<String, Object>();
		param.put("appId", appId);
		String key = "issue.app.server.checker";
		return findByNamedQuery(key, param); 
	}
	
	@Override
	public List<AppServerChecker> getAllServerChecker(){ 
		String key = "issue.app.server.checke";
		return findByNamedQuery(key, null);
	}
	
	@Override
	public void addServerChecker(AppServerChecker appServerChecker){
		save(appServerChecker);
	}
	
	@Override
	public void delServerChecker(Long id){
		AppServerChecker appServerChecker = getServerCheckerById(id);
		delete(appServerChecker);
	}
	
	@Override
	public List<AppServerChecker> getServerCheckerByAppId(Long appId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("appId", appId);
		return findByNamedQuery("issue.app.server.checke.byappid", param);  
	}
	
	@Override
	public List<AppServerChecker> getServerCheckerByTime(Date startTime,Date endTime){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		return findByNamedQuery("issue.app.server.checke.bytime", param);  
	} 
	
	@Override
	public List<AppServerChecker> getAppNodeName(Long appId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("appId", appId); 
		return findByNamedQuery("issue.app.server.checke.node.name", param);  
	}
	
	@Override
	public List<AppServerChecker> getAppNodeList(Long appId, Date startTime,Date endTime , String nodeName){
		Map<String, Object> param = new HashMap<String, Object>();
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		param.put("appId", appId); 
		param.put("startTime", sdf.format(startTime)); 
		param.put("endTime", sdf.format(endTime)); 
		param.put("nodeName", nodeName); 
		return findByNamedQuery("issue.app.server.checke.node.list", param);  
	}

	@Override
	public List<AppBussiness> getServiceApiByAppid(Long appId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("appId", appId); 
		return findByNamedQuery("issue.app.getServiceApiByAppid", param);  
	}

	@Override
	public List<ServiceApi> getApiAverageResponseTime(Long serviceApiId,
			Date startTime, Date endTime) {
		sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("serviceApiId", serviceApiId); 
		param.put("startTime", sdf.format(startTime)); 
		param.put("endTime", sdf.format(endTime)); 
		return findByNamedQuery("issue.app.getApiAverageResponseTime", param);  
	}
	

	@Override
	public void addHealthUrl(AppHealthUrl appHealthUrl){
		save(appHealthUrl);
	}
	
	@Override
	public void updateHealthUrl(AppHealthUrl appHealthUrl){
		update(appHealthUrl);
	}
	
	@Override
	public void deleteHealthUrl(Long id){
		AppHealthUrl appHealthUrl = getAppHealthUrlById(id);
		delete(appHealthUrl);
	}
	
	@Override
	public List<AppHealthUrl> getAppHealthUrl(Long appId,Integer status){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("appId", appId);
		param.put("status", status);
		return findByNamedQuery("issue.app.health.url", param);
	}
	
	@Override
	public AppHealthUrl getAppHealthUrlById(Long id){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id == null ? 0 : id);
		List<AppHealthUrl> list = findByNamedQuery("issue.app.health.url.id", param);
		return (list == null || list.isEmpty()) ? null : list.get(0) ;
	}
	
	@Override
	public void saveAppHealthHistory(AppHealthHistory appHealthHistory){
		save(appHealthHistory);
	}
	
	@Override
	public List<AppHealthDetail> getLastAppHealthDetailByAppId(Long appId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("appId", appId);
		return findByNamedQuery("issue.app.health.detail.appid", param);
	}
	
	@Override
	public List<AppHealthDetail> getAppHealthDetailByidAndDate(Long id,String startDate,String endDate){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("urlId", id);
		param.put("startDate", startDate);
		param.put("endDate", endDate);
		return findByNamedQuery("issue.app.health.detail.id.date", param);
	}
	public List<ServiceApiAverage> getAverageLv(String start,String end){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("start", start);
		param.put("end", end);
		return findByNamedQuery("issue.getAverageLv", param);
	}
	public void updateBusinessService(Long id,String lv){
		String sql = "update business_service bs set bs.ot_lv = ? where bs.id = ?";
		Query query = getSession().createSQLQuery(sql);
		query.setString(0, lv);
		query.setLong(1, id);
		query.executeUpdate();
	}
}
