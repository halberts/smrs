package com.haier.openplatform.console.issue.service.impl;

import hudson.plugins.jira.soap.RemoteIssue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.haier.openplatform.console.issue.dao.IssueMailDAO;
import com.haier.openplatform.console.issue.module.IssueModel;
import com.haier.openplatform.console.issue.module.IssueSummaryModel;
import com.haier.openplatform.console.issue.service.IssueMailService;
import com.haier.openplatform.console.issue.util.IssueBuilder;
import com.haier.openplatform.console.jira.domain.SoaSysContrast;
import com.haier.openplatform.console.jira.service.JiraService;
import com.haier.openplatform.console.jira.util.IssueTypeEnum;
import com.haier.openplatform.hmc.domain.Email;
import com.haier.openplatform.hmc.domain.SMS;
import com.haier.openplatform.hmc.domain.SMSMessage;
import com.haier.openplatform.hmc.sender.SendMsgService;
import com.haier.openplatform.hmc.sender.email.SendEmailService;
import com.smrs.util.DataConvertUtil;
import com.smrs.util.Env;

/**
 * @author shanjing
 * 
 */
public class IssueMailServiceImpl implements IssueMailService {
	/**
	 * set round to 3 as a default choice so that each time email/sms will be
	 * checked for multiple times to ensure queried as much as it can. In the
	 * meanwhile, limit it's execution time to avoid performance hazards
	 * 
	 */
	private static final String ENV_TYPE = "env.type";
	private static final Logger LOGGER = LoggerFactory
			.getLogger(IssueMailServiceImpl.class);
	private IssueBuilder issueBuilder;
	private IssueMailDAO issueDAO;
	private SendEmailService emailSender;
	private SendMsgService smsSender;
	private JiraService jiraService;
	
	private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS ,  
			new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.DiscardOldestPolicy() );

	public void setIssueBuilder(IssueBuilder issueBuilder) {
		this.issueBuilder = issueBuilder;
	}

	public void setSmsSender(SendMsgService smsSender) {
		this.smsSender = smsSender;
	}

	public void setIssueMailDAO(IssueMailDAO issueMailDAO) {
		this.issueDAO = issueMailDAO;
	}

	public void setEmailSender(SendEmailService emailSender) {
		this.emailSender = emailSender;
	}

	public void setJiraService(JiraService jiraService) {
		this.jiraService = jiraService;
	}
	
	private Long shootUnsentEmail(IssueModel issueModel) {
		Assert.notNull(issueBuilder, "doesn't allow empty issueBuilder");
		Assert.notNull(emailSender, "doesn't allow empty emailSender");
		Assert.notNull(issueModel, "doesn't allow empty issueModel");

		String clobString = "";
		if (issueModel.getServiceException() != null) {
			clobString = DataConvertUtil.doClobToString(issueModel
					.getServiceException());
		}

//		Email email = issueBuilder.build(issueModel.getOwnerEmail(),
//				issueModel.getOwnerName(), issueModel.getAppName(),
//				issueModel.getServiceName(), issueModel.getIssueId(),
//				Env.getProperty(ENV_TYPE), clobString);
		Email email = issueBuilder.build(issueModel,
				Env.getProperty(ENV_TYPE), clobString);

		/**
		 * only send out if it is not DEV env
		 */
		if (!"DEV".equals(Env.getProperty(ENV_TYPE))) {
			emailSender.sendEmail(email);
		} else {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("email won't be sent out because of the [DEV] env");
			}
		}

		return issueModel.getIssueId();
	}

	private void shootUnsentSMSs(List<IssueModel> smsModels) {
		Map<String, List<IssueModel>> smsAggrMap = new HashMap<String, List<IssueModel>>();
		Map<String, Long> alertMaxMap = new HashMap<String, Long>();

		/**
		 * aggregate smsModels according to appName, svcName
		 */
		for (IssueModel smsModel : smsModels) {
			String appName = smsModel.getAppName();
			String svcName = smsModel.getServiceName();
			final String smsPK = String.format("%s:%s", appName, svcName);

			if (!smsAggrMap.containsKey(smsPK)) {
				smsAggrMap.put(smsPK, new ArrayList<IssueModel>());
				alertMaxMap.put(smsPK, smsModel.getAlertMax());
			}

			smsAggrMap.get(smsPK).add(smsModel);
		}

		Iterator<String> smsAggrKeys = smsAggrMap.keySet().iterator();
		Iterator<List<IssueModel>> smsAggrVals = smsAggrMap.values().iterator();

		while (smsAggrKeys.hasNext()) {
			String smsPK = smsAggrKeys.next();
			List<IssueModel> smsAggrModels = smsAggrVals.next();

			/**
			 * only send out SMS if it is on production and the amount is over
			 * limit
			 */
			if (!"DEV".equals(Env.getProperty(ENV_TYPE))
					&& smsAggrModels.size() > alertMaxMap.get(smsPK)
					&& smsAggrModels.size() > 0) {
				List<Long> issueIds = new ArrayList<Long>();
				for (IssueModel smsAggrModel : smsAggrModels) {
					issueIds.add(smsAggrModel.getIssueId());
				}

				IssueModel smsAggrModel = smsAggrModels.get(0);

				List<SMS> unsentSMSs = issueBuilder.build(
						smsAggrModel.getAppName(),
						smsAggrModel.getServiceName(),
						smsAggrModel.getOwnerName(),
						smsAggrModel.getOwnerPhone(),
						smsAggrModel.getSptOwnerName(),
						smsAggrModel.getSptOwnerPhone(), issueIds);

				SMSMessage smsMsg = new SMSMessage();
				smsMsg.setSenderName("HOP监控");
				smsMsg.setSmsList(unsentSMSs);
				smsSender.sendMsg(smsMsg);
			}
		}
	}

	@Override
	public void shootUnsentIssues() {
		List<Long> ids = new ArrayList<Long>();// 记录日志用的
		List<IssueModel> issueModels = issueDAO.queryUnsentIssues();
		LOGGER.error("@@@@@@@@@" + issueModels.size() + " IssueModel@@@@@");

		while (!issueModels.isEmpty()) {
			LOGGER.error("while begin");
			List<Long> emailIds = new ArrayList<Long>();
			List<Long> smsIds = new ArrayList<Long>();
			List<IssueModel> smsModels = new ArrayList<IssueModel>();

			/**
			 * loop to aggregate SMSs and send Emails
			 */
			for (IssueModel issueModel : issueModels) {
				ids.add(issueModel.getIssueId());
				long emailStatus = issueModel.getEmailStatus();
				long smsStatus = issueModel.getSmsStatus();
				long issueType = issueModel.getIssueType();

				/**
				 * it is unsent yet, and also not an over time issue
				 */
				if (emailStatus == 0 && issueType != 3) {
					emailIds.add(shootUnsentEmail(issueModel));
				}

				if (smsStatus == 0) {
					smsIds.add(issueModel.getIssueId());
					smsModels.add(issueModel);
				}
			}
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			Date nowDate;
			try {
				nowDate = sdf.parse(sdf.format(date));
				Date minDate = sdf.parse("08:00:00");
				Date maxDate = sdf.parse("22:00:00");
				if(nowDate.after(minDate)&&nowDate.before(maxDate)){
					shootUnsentSMSs(smsModels);
				}
			} catch (ParseException e) {
				LOGGER.info("判断时间是否为早8点到晚22点时出现异常",e);
			}
			LOGGER.error("update email begin,emailIds=" + emailIds);
			issueDAO.updateEmailStatus(emailIds);
			LOGGER.error("update email end");
			LOGGER.error("update sms begin,smsIds=" + smsIds);
			issueDAO.updateSMSStatus(smsIds);
			LOGGER.error("update sms end");

			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(String.format(
						"%s SMSs and %s Emails have been sent out",
						smsIds.size(), emailIds.size()));
			}
			threadPool.execute(new ProcessIssue(new ArrayList<IssueModel>(issueModels)));
			issueModels = issueDAO.queryUnsentIssues();
			LOGGER.error("while end");
		}

		LOGGER.error("@@@@@@@@@@@@@@@" + StringUtils.join(ids, ",")
				+ "@@@@@@@@@@@@@@@@@@@@@");
	}

	private List<IssueSummaryModel> buildIssueSummary(
			List<IssueModel> issueModels) {
		Map<String, IssueSummaryModel> issueSums = new HashMap<String, IssueSummaryModel>();
		/**
		 * build up issueSumModels
		 */
		for (IssueModel issueModel : issueModels) {
			IssueSummaryModel issueSum = null;

			if (!issueSums.containsKey(issueModel.getServiceName())) {
				issueSum = new IssueSummaryModel(issueModel.getServiceName(),
						issueModel.getOwnerName(), issueModel.getOwnerEmail(),
						issueModel.getCount(), issueModel.getDuration(),
						new ArrayList<Long>());
				issueSums.put(issueSum.getApiName(), issueSum);
			} else {
				issueSum = issueSums.get(issueModel.getServiceName());
			}

			/**
			 * this is the summary record
			 */
			if (issueModel.getIssueId() == 0) {
				issueSum.setCount(issueModel.getCount());
				issueSum.setDuration(issueModel.getDuration());
			}
			/**
			 * this is the detail record
			 */
			else {
				issueSum.getIssueIds().add(issueModel.getIssueId());
			}
		}

		List<IssueSummaryModel> issueSumModels = new ArrayList<IssueSummaryModel>();
		for (IssueSummaryModel issueSum : issueSums.values()) {
			issueSumModels.add(issueSum);
		}

		return issueSumModels;
	}

	@Override
	public void shootOvertimeIssues() {
		List<IssueModel> issueModels = issueDAO.queryOvertimeIssues();
		Map<String, List<IssueModel>> issueModelCats = new HashMap<String, List<IssueModel>>();
		for (IssueModel issueModel : issueModels) {
			String appName = issueModel.getAppName();
			if (!issueModelCats.containsKey(appName)) {
				issueModelCats.put(appName, new ArrayList<IssueModel>());
			}

			/**
			 * models for that very appName
			 */
			List<IssueModel> catIssueModels = issueModelCats.get(appName);
			catIssueModels.add(issueModel);
		}

		/**
		 * build up issueSummary separately according to different appName
		 */
		for (List<IssueModel> catIssueModels : issueModelCats.values()) {
			List<IssueSummaryModel> issueSumModels = buildIssueSummary(catIssueModels);
			Email email = issueBuilder.build(issueSumModels);
			emailSender.sendEmail(email);
		}
	}
	
	class ProcessIssue implements Runnable{
		
		private List<IssueModel> issueList;
		
		public ProcessIssue(List<IssueModel> list){
			this.issueList = list;
		}
		
		@Override
		public void run() {
			if(!"production".equalsIgnoreCase(Env.getProperty(Env.ENV_TYPE))){
				return;
			}
			LOGGER.error("------process issue begin------");
			if(issueList == null || issueList.isEmpty()){
				return;
			}
			String token = null;
			try{
				token = jiraService.loginJira();
				if(StringUtils.isBlank(token)){
					return;
				}
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, 3);
				for(IssueModel issue : issueList){
					SoaSysContrast ssc = jiraService.queryJiraByAppName(issue.getAppName());
					if(ssc == null){
						continue;
					}
					RemoteIssue[] issues = jiraService.getIssuesByTextFromProject(token, new String[]{ssc.getJiraKey().toUpperCase()}, issue.getServiceName()+":"+issue.getExceptionName());
					if(!isCreateIssue(issues)){
						continue;
					}
					RemoteIssue result = jiraService.createIssue(ssc.getJiraKey(), issue.getServiceName()+":"+issue.getExceptionName(), 
							DataConvertUtil.doClobToString(issue.getServiceException()),IssueTypeEnum.ISSUE_CONSOLE_EXCEPTION.getId(), cal);
					if(result != null){
						LOGGER.error("exception create jira : APP NAME=" + issue.getAppName() + "---" + "SUMMARY=" + (issue.getServiceName()+":"+issue.getExceptionName()));
					}
				}
			}catch(Exception e){
				LOGGER.error("process issue error.",e);
			}finally{
				if(StringUtils.isNotBlank(token)){
					jiraService.logoutJira(token);
				}
			}
			LOGGER.error("------process issue end------");
		}
		
		private boolean isCreateIssue(RemoteIssue[] issues){
			if(issues == null || issues.length < 1){
				return true;
			}
			RemoteIssue tmp = issues[0];
			for(int i=0;i<issues.length;i++){
				if(tmp.getDuedate().after(issues[i].getDuedate())){
					continue;
				}
				tmp = issues[i];
			}
			return Calendar.getInstance().after(tmp.getDuedate());
		}
	}

}
