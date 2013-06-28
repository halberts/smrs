package com.haier.openplatform.console.issue.service.impl;

import static com.smrs.util.DataConvertUtil.doClobToString;
import hudson.plugins.jira.soap.RemoteIssue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haier.openplatform.console.issue.dao.AppMonitorDAO;
import com.haier.openplatform.console.issue.domain.AppHealthHistory;
import com.haier.openplatform.console.issue.domain.AppHealthUrl;
import com.haier.openplatform.console.issue.domain.AppLists;
import com.haier.openplatform.console.issue.domain.AppServerChecker;
import com.haier.openplatform.console.issue.domain.HopEmail;
import com.haier.openplatform.console.issue.domain.HopSms;
import com.haier.openplatform.console.issue.domain.IssueSupporter;
import com.haier.openplatform.console.issue.domain.ServiceTimeoutLv;
import com.haier.openplatform.console.issue.module.ApiCallGroupByHour;
import com.haier.openplatform.console.issue.module.ApiSearcher;
import com.haier.openplatform.console.issue.module.AppBussiness;
import com.haier.openplatform.console.issue.module.AppHealthDetail;
import com.haier.openplatform.console.issue.module.AppIssueSupporter;
import com.haier.openplatform.console.issue.module.AppSummaryModel;
import com.haier.openplatform.console.issue.module.HopEmailModel;
import com.haier.openplatform.console.issue.module.Issue;
import com.haier.openplatform.console.issue.module.IssueDetail;
import com.haier.openplatform.console.issue.module.IssueDetailModule;
import com.haier.openplatform.console.issue.module.IssueSearcher;
import com.haier.openplatform.console.issue.module.Searcher;
import com.haier.openplatform.console.issue.module.ServiceApi;
import com.haier.openplatform.console.issue.module.ServiceApiAverage;
import com.haier.openplatform.console.issue.service.AppMonitorService;
import com.haier.openplatform.console.issue.service.BaseInfoService;
import com.haier.openplatform.console.issue.util.IssueBuilder;
import com.haier.openplatform.console.jira.domain.SoaSysContrast;
import com.haier.openplatform.console.jira.service.JiraService;
import com.haier.openplatform.console.jira.util.IssueTypeEnum;
import com.haier.openplatform.hmc.domain.Email;
import com.haier.openplatform.hmc.domain.Recipient;
import com.haier.openplatform.hmc.sender.email.SendEmailService;
import com.haier.openplatform.util.Pager;
import com.jof.framework.url.URLCheckStatusEnum;
import com.jof.framework.url.URLStatusBean;
import com.jof.framework.url.URLStatusEnum;
import com.jof.framework.url.URLUtils;
import com.smrs.util.DateCommonUtil;
import com.smrs.util.Env;

/**
 * @author shanjing
 * @author WangJian
 */
public class AppMonitorServiceImpl implements AppMonitorService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppMonitorServiceImpl.class);
	private static final int APP_HEALTH_PERIOD = -35;
	private AppMonitorDAO appMonitorDAO; 
	private IssueBuilder issueBuilder;
	private BaseInfoService baseInfoService;
	private SendEmailService emailSender;
	private JiraService jiraService;
	
	private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(3, 10, 5, TimeUnit. SECONDS ,  
			new ArrayBlockingQueue<Runnable>(3), new ThreadPoolExecutor.DiscardOldestPolicy() );
	
	public void setIssueBuilder(IssueBuilder issueBuilder) {
		this.issueBuilder = issueBuilder;
	} 

	public void setAppMonitorDAO(AppMonitorDAO appMonitorDAO) {
		this.appMonitorDAO = appMonitorDAO;
	}

	public void setBaseInfoService(BaseInfoService baseInfoService) {
		this.baseInfoService = baseInfoService;
	}

	public void setEmailSender(SendEmailService emailSender) {
		this.emailSender = emailSender;
	}

	public void setJiraService(JiraService jiraService) {
		this.jiraService = jiraService;
	}
	
	@Override
	public Map<String, Long> getServiceLevel(Long appId) {
		return appMonitorDAO.getServiceLevel(appId);
	}

	@Override
	public Long getServiceOvertimeNum(Long appId, Date from, Date to) {
		return appMonitorDAO.getServiceOvertimeNum(appId, from, to);
	}

	@Override
	public Pager<Issue> getIssueByPage(IssueSearcher issueSearcher) {
		return appMonitorDAO.getIssueByPage(issueSearcher);
	}

	@Override
	public IssueDetail getIssueDetail(Long issueId) {
		IssueDetailModule idm = appMonitorDAO.getIssueDetail(issueId);
		if (idm == null) {
			return null;
		}
		IssueDetail issue = new IssueDetail();
		issue.setActionName(idm.getActionName());
		issue.setCreateTime(idm.getCreateTime());
		issue.setExcuteTime(idm.getExcuteTime());
		issue.setCurrentThrNum(idm.getCurrentThrNum());
		issue.setOwnerEmail(idm.getOwnerEmail());
		issue.setOwnerName(idm.getOwnerName());
		issue.setOwnerPhone(idm.getOwnerPhone());
		issue.setAppName(idm.getAppName());
		issue.setNodeAlias(idm.getNodeAlias());
		issue.setNodeIp(idm.getNodeIp());

		// 将clob转成string
		issue.setInsight(doClobToString(idm.getInsightClob()));
		issue.setInValue(doClobToString(idm.getInValueClob()));
		issue.setServiceEx(doClobToString(idm.getServiceExClob()));

		return issue;

	}

	@Override
	public Pager<Issue> getOvertimeTopTen(Searcher overtimeTopTenSearcher) {
		return appMonitorDAO.getOvertimeTopTen(overtimeTopTenSearcher);
	}

	@Override
	public Pager<ServiceApi> getServiceApiCall(ApiSearcher searcher) {
		return appMonitorDAO.getServiceApiCall(searcher);
	}

	@Override
	public List<ApiCallGroupByHour> getApiCallGroupByHour(Long appId,
			Date from, Date to) {
		return appMonitorDAO.getApiCallGroupByHour(appId, from, to);
	}
	
	@Override
	public Pager<HopEmailModel> getHopEmail(String system, Date from, Date to,
			Pager<HopEmail> pager) {
		Pager<HopEmail> emailPager = appMonitorDAO.getHopEmail(system, from,
				to, pager);
		
		return issueBuilder.build(emailPager);
	}

	@Override
	public Pager<HopSms> getHopSms(String system, Date from, Date to,
			Pager<HopSms> pager) {
		return appMonitorDAO.getHopSms(system, from, to, pager);
	}

	@Override
	public Pager<ServiceApi> getLastestExecApi(Long appId,
			Pager<ServiceApi> pager) {
		return appMonitorDAO.getLastestExecApi(appId, pager);
	}

	@Override
	public Pager<Issue> getLastestIssue(Long appId, Pager<Issue> pager,
			String param1, String param2, String param3) {
		return appMonitorDAO.getLastestIssue(appId, pager, param1, param2,
				param3);
	}  

	@Override
	public AppServerChecker getServerCheckerById(Long id) {
		return appMonitorDAO.getServerCheckerById(id);
	}

	@Override
	public void updateServerChecker(AppServerChecker as) {
		appMonitorDAO.updateServerChecker(as);
	}

	public void updateServerCheckers(List<AppServerChecker> as) {

		if (as == null || as.size() < 1) {
			return;
		}

		for (AppServerChecker appServerChecker : as) {
			updateServerChecker(appServerChecker);
		}
	} 
	
	@Override
	public void saveServerChecker(AppServerChecker appServerChecker) { 
			appMonitorDAO.saveServerChecker(appServerChecker);
	}

	@Override
	public List<AppServerChecker> getStatusPlot(Long appId) {
		return appMonitorDAO.getStatusPlot(appId);
	}
	
	@Override
	public List<AppServerChecker> getAllServerChecker(){
		return appMonitorDAO.getAllServerChecker();
	}
	
	@Override
	public void addServerChecker(AppServerChecker appServerChecker){
		URLStatusBean usb = URLUtils.getURLStatus(appServerChecker.getUrlChecker());
		appServerChecker.setUrlStatus(usb.getUrlStatus());
		appServerChecker.setUrlStatusMsg(usb.getUrlStatusMsg()); 
		appServerChecker.setCheckTime(new Date());
		appMonitorDAO.addServerChecker(appServerChecker);
	}
	
	@Override
	public void delServerChecker(Long id){
		appMonitorDAO.delServerChecker(id);
	}
	
	@Override
	public List<AppServerChecker> getServerCheckerByAppId(Long appId){
		return appMonitorDAO.getServerCheckerByAppId(appId);
	}
	
	@Override
	public List<AppServerChecker> getServerCheckerByTime(Date startTime,Date endTime){
		return appMonitorDAO.getServerCheckerByTime(startTime,endTime);
	}
	
	@Override
	public List<AppServerChecker> getNodeInfo(Long appId, Date startTime,Date endTime,String nodeName){
		return appMonitorDAO.getAppNodeList(appId, startTime, endTime, nodeName);
	}
	
	@Override
	public List<String> getNodeName(Long appId){
		List<String> nameList = new ArrayList<String>();
		List<AppServerChecker> appServerChecker = appMonitorDAO.getAppNodeName(appId);
		for(AppServerChecker asc:appServerChecker){
			nameList.add(asc.getNodeName());
		}
		return nameList;
	}

	@Override
	public List<ServiceApi> getServiceApiByAppid(Long appId,String date) {
		List<AppBussiness> allApi = appMonitorDAO.getServiceApiByAppid(appId);
		List<ServiceApi> singleApiTotal = new ArrayList<ServiceApi>();
		Date time = new Date();
		Calendar calendarStartTime = Calendar.getInstance();
		calendarStartTime.setTime(time);
		Calendar calendarEndTime= Calendar.getInstance();
		calendarEndTime.setTime(time);
		if("1".equals(date)){
			calendarStartTime.add(Calendar.DATE, -30);
		}else if("2".equals(date)){
			calendarStartTime.add(Calendar.DATE, -7);
		}else if("3".equals(date)){
			calendarStartTime.add(Calendar.DATE, -2);
		}
		
		calendarStartTime.add(Calendar.DATE, +1);
		Date startTime = calendarStartTime.getTime();
		Date endTime = calendarEndTime.getTime();
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startTime = null;
		Date endTime = null;
		try {
			startTime = sdf.parse(start);
			endTime = sdf.parse(end);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}*/
		for(AppBussiness single:allApi){
			List<ServiceApi> allService = this.getApiAverageResponseTime(single.getServiceApiId(), startTime , endTime);
			double totalCall = 0;
			double totalExecuteTime = 0;
			for(int i=0;i<allService.size();i++){
				totalCall = totalCall + allService.get(i).getCallNum();
				totalExecuteTime = allService.get(i).getExecuteTime();
				if(i==allService.size()-1){
					ServiceApi serviceApi = new ServiceApi();
					try {
						serviceApi = (ServiceApi) BeanUtils.cloneBean(allService.get(i));
					} catch (Exception e) {
						LOGGER.error(e.toString());
					} 
					serviceApi.setAverageTime(totalExecuteTime/totalCall);
					singleApiTotal.add(serviceApi);
				}
			}
		}
		return singleApiTotal;
	}

	@Override
	public List<ServiceApi> getApiAverageResponseTime(Long serviceApiId,
			Date startTime, Date endTime) {
		return appMonitorDAO.getApiAverageResponseTime(serviceApiId,startTime,endTime);
	}
	
	@Override
	public void appHealthyCheck(){
		try{
			List<AppHealthUrl> urlList = appMonitorDAO.getAppHealthUrl(null, URLStatusEnum.ENABLE.getStatus());
			if(urlList == null || urlList.isEmpty()){
				return;
			}
			List<AppIssueSupporter> appIssueSupporter = baseInfoService.getAppIssueSupporter(); 
			Map<Long,AppIssueSupporter> mapAppIssueSupporter = new HashMap<Long,AppIssueSupporter>();
			for(AppIssueSupporter ais:appIssueSupporter){
				mapAppIssueSupporter.put(ais.getId(), ais);
			}
			for(AppHealthUrl url : urlList){
				//检查node状态
				URLStatusBean usb = URLUtils.getURLStatus(url.getUrl());
				//新增node历史记录
				AppHealthHistory appHis = generalAppHealthHistory(url,usb);
				appMonitorDAO.saveAppHealthHistory(appHis);
				//node状态正常则继续
				if(usb.getUrlStatus() == URLCheckStatusEnum.ACTIVE.getStatus()){
					continue;
				}
				//node状态异常则发邮件
				threadPool.execute(new SendEmailTask(url,usb,mapAppIssueSupporter));
				//检查前30分钟是否都不正常,不正常则创建jira问题
				//checkUrlIsNormal(url.getId());
			}
		}catch(Exception e){
			LOGGER.error("appHealthyCheck error.",e);
		}
	}
	
	@Override
	public void checkUrlIsNormal(Long urlId){
		if(!"production".equalsIgnoreCase(Env.getProperty(Env.ENV_TYPE))){
			return;
		}
		Calendar cal = Calendar.getInstance();
		Date endDate = cal.getTime();
		cal.add(Calendar.MINUTE, APP_HEALTH_PERIOD);
		List<AppHealthDetail> urlList = this.getAppHealthDetailByidAndDate(urlId, DateCommonUtil.format("yyyy-MM-dd HH:mm", cal.getTime()), DateCommonUtil.format("yyyy-MM-dd HH:mm", endDate));
		if(urlList == null || urlList.isEmpty()){
			return;
		}
		for(AppHealthDetail det : urlList){
			if(det.getUrlStatus().equals(URLCheckStatusEnum.ACTIVE.getStatus())){
				return;
			}
		}
		//30分钟没有成功访问的记录，则创建jira问题
		AppLists app = baseInfoService.getAppById(urlList.get(0).getAppId());
		SoaSysContrast ssc = jiraService.queryJiraByAppId(app.getId());
		if(ssc == null){
			return;
		}
		String desc = "HOP监控平台显示" + app.getAppName() + "系统" + urlList.get(0).getUrl() + "服务节点健康检查失败，请及时查明异常原因尽快恢复服务能力。";
		RemoteIssue result = jiraService.createIssue(ssc.getJiraKey(), "应用服务器异常", desc,IssueTypeEnum.ISSUE_SERVER_HEALTH.getId(), Calendar.getInstance());
//		RemoteIssue result = jiraService.createIssue("esptest", "应用服务器异常", desc, Calendar.getInstance());
		if(result != null){
			LOGGER.error(desc);
		}
	}
	
	private AppHealthHistory generalAppHealthHistory(AppHealthUrl url,URLStatusBean usb){
		Date date = new Date();
		AppHealthHistory his = new AppHealthHistory();
		his.setUrlId(url.getId());
		his.setUrlStatus(usb.getUrlStatus());
		his.setUrlStatusMsg(usb.getUrlStatusMsg());
		his.setLastModifiedBy("system");
		his.setLastModifiedDate(date);
		his.setCreateBy("system");
		his.setCreateDate(date);
		return his;
	}
	
	public class SendEmailTask implements Runnable{

		/**
		 * 
		 */
		//private static final long serialVersionUID = -2982950740756138957L;
		private AppHealthUrl url;
		private URLStatusBean usb;
		private Map<Long,AppIssueSupporter> mapAppIssueSupporter;
		
		public SendEmailTask(AppHealthUrl url,URLStatusBean usb,Map<Long,AppIssueSupporter> mapAppIssueSupporter){
			this.url = url;
			this.usb = usb;
			this.mapAppIssueSupporter = mapAppIssueSupporter;
		}
		
		@Override
		public void run() {
			LOGGER.error("*******sendHealthCheckEmail begin*******");
			sendHealthCheckEmail(url,usb,mapAppIssueSupporter);
			LOGGER.error("*******sendHealthCheckEmail end*******");
		}
		
		private void sendHealthCheckEmail(AppHealthUrl url,URLStatusBean usb,Map<Long,AppIssueSupporter> mapAppIssueSupporter){
			Email email = new Email();
		    Recipient recipient = new Recipient(); 
		    recipient.setUserName("HOP-监控平台");
		    recipient.setEmailAddress(Env.getProperty(Env.APP_EMAIL.isEmpty()||Env.APP_EMAIL == null ? "" : Env.APP_EMAIL));
		    email.setSender(recipient); 
		    List<Recipient> toRecipientList = new ArrayList<Recipient>();
		    String ownerName = mapAppIssueSupporter.get(url.getAppId()).getOwnerName();
		    String ownerEmail = mapAppIssueSupporter.get(url.getAppId()).getOwnerEmail();
		    String sptOwnerName = mapAppIssueSupporter.get(url.getAppId()).getSptOwnerName();
		    String sptOwnerEmail =  mapAppIssueSupporter.get(url.getAppId()).getSptOwnerEmail();
		    if( ownerName != null && ownerEmail != null){ 
		    	 Recipient toRecipient = new Recipient(); 
			    toRecipient.setUserName(ownerName);
			    toRecipient.setEmailAddress(ownerEmail);
			    toRecipientList.add(toRecipient); 
		    }
		    if(sptOwnerName != null && sptOwnerEmail != null){
		    	Recipient toRecipient = new Recipient(); 
				toRecipient.setUserName(sptOwnerName);
				toRecipient.setEmailAddress(sptOwnerEmail);
				toRecipientList.add(toRecipient); 
		    }
		    email.setToRecipient(toRecipientList);
		    email.setSubject("应用["+mapAppIssueSupporter.get(url.getAppId()).getAppName()+"]-["+Env.getProperty(Env.ENV_TYPE)+"]环境中,服务器异常提示");
		    email.setBodyContent("您好在应用["+mapAppIssueSupporter.get(url.getAppId()).getAppName()+"]-["+Env.getProperty(Env.ENV_TYPE)+"环境中我们发现,节点"+url.getNodeName()+"出现异常,错误提示"+usb.getUrlStatusMsg()+"请及时查看", false);
		    emailSender.sendEmail(email);
		}
	}

	public List<AppHealthDetail> getLastAppHealthDetailByAppId(Long appId){
		return appMonitorDAO.getLastAppHealthDetailByAppId(appId);
	}

	@Override
	public void addHealthUrl(AppHealthUrl appHealthUrl) {
		appMonitorDAO.addHealthUrl(appHealthUrl);
	}

	@Override
	public void updateHealthUrl(AppHealthUrl appHealthUrl) {
		appMonitorDAO.updateHealthUrl(appHealthUrl);
	}

	@Override
	public void deleteHealthUrl(Long id) {
		appMonitorDAO.deleteHealthUrl(id);
	}

	@Override
	public List<AppHealthUrl> getAppHealthUrl(Long appId,Integer status){
		return appMonitorDAO.getAppHealthUrl(appId, status);
	}
	
	@Override
	public AppHealthUrl getAppHealthUrlById(Long id) {
		return appMonitorDAO.getAppHealthUrlById(id);
	}

	@Override
	public List<AppHealthDetail> getAppHealthDetailByidAndDate(Long id,String startDate,String endDate) {
		return appMonitorDAO.getAppHealthDetailByidAndDate(id, startDate, endDate);
	}
	public void getServiceLv(){
		List<ServiceApiAverage> averageLv = this.getAverageLv();
		List<ServiceTimeoutLv> rs = baseInfoService.getServiceTimeoutLv();
		List<ServiceTimeoutLv> returnLv = comparator(rs);
		for (ServiceApiAverage list : averageLv) {
			String overtimeLv = null;
			for (int i = 0; i < returnLv.size(); i++) {
				boolean first = returnLv.get(i).getOvertimeMsec() > list.getAverageTime();
				boolean greaterThan = returnLv.get(i).getOvertimeMsec() < list.getAverageTime();
				if(i != returnLv.size()-1){
					boolean lessThan = returnLv.get(i + 1).getOvertimeMsec() > list.getAverageTime();
					if (i != returnLv.size() - 1 && greaterThan && lessThan) {
						overtimeLv = "LV" + (i + 2);
						break;
					}  
				}
				if(i==0&&first){
					overtimeLv = "LV" + (i + 1);
					break;
				}
				if (i == returnLv.size() - 1){
					overtimeLv = "LV" + (i + 1);
					break;
				}
			}
			appMonitorDAO.updateBusinessService(list.getServiceId(), overtimeLv);
		}
	}
	public List<ServiceApiAverage> getAverageLv(){
		Date nowDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar date = Calendar.getInstance();
		date.setTime(nowDate);
		String end = sdf.format(nowDate);
		date.set(Calendar.DATE, date.get(Calendar.DATE) - 30);
		String start = sdf.format(date.getTime());
		return appMonitorDAO.getAverageLv(start,end);
	}
	
	
	public List<ServiceTimeoutLv> comparator(List<ServiceTimeoutLv> list){
		List<ServiceTimeoutLv> rs = baseInfoService.getServiceTimeoutLv();
		Comparator<ServiceTimeoutLv> comparator = new Comparator<ServiceTimeoutLv>() {
			@Override
			public int compare(ServiceTimeoutLv o1, ServiceTimeoutLv o2) {
				return o1.getOvertimeMsec().compareTo(o2.getOvertimeMsec());
			}
		};
		//进行level级别的排序
		Collections.sort(rs,comparator);
		return rs;
	}

	@Override
	public AppSummaryModel getAppSummary() {
		Map<String, Integer> result = new LinkedHashMap<String, Integer>();
		Map<String,List<AppLists>> appMap = new LinkedHashMap<String, List<AppLists>>();
		//初始化结果集
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -11);
		for(int i = 12; i >= 1; i--){//取12个月
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(calendar.get(Calendar.YEAR));
			stringBuffer.append("-");
			int month = calendar.get(Calendar.MONTH)+1;
			stringBuffer.append(month>9?(month+""):("0"+month));
			result.put(stringBuffer.toString(), 0);
			appMap.put(stringBuffer.toString(), new ArrayList<AppLists>());
			
			calendar.add(Calendar.MONTH, 1);
		}
		
		List<AppLists> appList = this.baseInfoService.getAppLists();
		for(AppLists app : appList){
			String dateString = new SimpleDateFormat("yyyy-MM").format(app.getGmtCreate());
			if(result.containsKey(dateString)){
				result.put(dateString, result.get(dateString)+1);
			}
			appMap.get(dateString).add(app);
		}
		
		AppSummaryModel model = new AppSummaryModel();
		//设置应用列表
		model.setTotalAppSize(appList.size());
		model.setAppMap(result);
		Collections.sort(appList, new Comparator<AppLists>() {
			@Override
			public int compare(AppLists source, AppLists target) {
				return target.getGmtCreate().compareTo(source.getGmtCreate());
			}
		});
		model.setAppList(appList);
		
		//设置应用服务责人
		Map<Long,IssueSupporter> issueSupporterMap = new HashMap<Long, IssueSupporter>();
		List<IssueSupporter> supporters = this.baseInfoService.getIssueSupporterList();
		for(IssueSupporter supporter : supporters){
			issueSupporterMap.put(supporter.getId(), supporter);
		}
		model.setIssueSupporterMap(issueSupporterMap);
		return model;
	}
}
