package com.jof.framework.audit.quartz;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerKey;
import org.quartz.TriggerListener;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.Assert;

import com.haier.openplatform.util.HOPConstant;

public class JobHistoryPlugin implements JobListener,TriggerListener,SchedulerListener,InitializingBean {
	//当前执行的是哪个方法
	private static final String EVENT_TYPE = "EVENT_TYPE";
	private static final Logger LOGGER = LoggerFactory.getLogger(JobHistoryPlugin.class);
	private JmsTemplate jmsTemplate;
	/**
	 * 接收和发送quartz监控消息的queue名字
	 */
	private String destinationName ="monitor.queue.quartz.runtime";
	private ExecutorService executorService = Executors.newCachedThreadPool();
	private Scheduler scheduler;
	private String schedulerName;
	private String schedulerInstanceId;
	private String schedulerVersion;
	private String host;
	private String appName;
	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	@Override
	public String getName() {
		return "QuartzJobHistoryPlugin-HOP";
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
//		Map<String,String> dataMap = this.buildAllJsonMap(context);
//		dataMap.put(EVENT_TYPE, "jobToBeExecuted");
//		String uuid = UUID.randomUUID().toString();
//		dataMap.put("UUID", uuid);
//		context.put("UUID", uuid);
//		configCronInfo(context.getTrigger(), dataMap);
//		this.sendNotification(dataMap);
//		LOGGER.info("jobToBeExecuted");
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
//		LOGGER.info("jobExecutionVetoed");
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		Map<String,String> dataMap = this.buildAllJsonMap(context);
		dataMap.put(EVENT_TYPE, "jobWasExecuted");
		dataMap.put("errorMessage", defuaultIfNull(jobException));
		configCronInfo(context.getTrigger(), dataMap);
		this.sendNotification(dataMap);
//		LOGGER.info("jobWasExecuted");
	}
	
	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
//		Map<String,String> dataMap = this.buildAllJsonMap(context);
//		dataMap.put(EVENT_TYPE, "triggerFired");
//		configCronInfo(trigger, dataMap);
//		this.sendNotification(dataMap);
//		LOGGER.info("triggerFired");
		String uuid = UUID.randomUUID().toString();
		context.put("UUID", uuid);
	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
//		LOGGER.info("vetoJobExecution");
		return false;
	}

	@Override
	public void triggerMisfired(Trigger trigger) {
//		Map<String,String> dataMap = this.buildMapFromTrigger(trigger);
//		dataMap.put(METHOD_CODE, "triggerMisfired");
//		this.sendMonitorData(dataMap);
//		LOGGER.info("triggerMisfired");
	}

	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			CompletedExecutionInstruction triggerInstructionCode) {
//		Map<String,String> dataMap = this.buildAllJsonMap(context);
//		dataMap.put(EVENT_TYPE, "triggerComplete");
//		configCronInfo(trigger, dataMap);
//		this.sendNotification(dataMap);
//		LOGGER.info("triggerComplete");
	}

	/** 
	 * 
	 * 调度器启动时，将调度计划信息发送到监控中心
	 * @see org.quartz.SchedulerListener#jobScheduled(org.quartz.Trigger)
	 */
	@Override
	public void jobScheduled(Trigger trigger) {
//		Map<String,String> result = buildSchedulerMap();
//		result.put(EVENT_TYPE, "jobScheduled");
//		configCronInfo(trigger, result);
//		result.putAll(this.buildTriggerMap(trigger));
//		this.sendNotification(result);
//		LOGGER.info("jobScheduled");
	}

	private void configCronInfo(Trigger trigger, Map<String, String> result) {
		String triggerType = "triggerType";
		String cronExpression = "cronExpression";
		if(trigger instanceof CronTrigger){
			CronTrigger cronTrigger = (CronTrigger)trigger;
			result.put(triggerType, "CRON");
			result.put(cronExpression, cronTrigger.getCronExpression());
		}else if(trigger instanceof SimpleTrigger){
			result.put(triggerType, "SIMPLE");
		}else{
			result.put(triggerType, "OTHER");
		}
	}

	@Override
	public void jobUnscheduled(TriggerKey triggerKey) {
//		LOGGER.info("jobUnscheduled");
	}

	@Override
	public void triggerFinalized(Trigger trigger) {
//		LOGGER.info("triggerFinalized");
	}

	@Override
	public void triggerPaused(TriggerKey triggerKey) {
//		LOGGER.info("triggerPaused");
	}

	@Override
	public void triggersPaused(String triggerGroup) {
//		LOGGER.info("triggersPaused");
	}

	@Override
	public void triggerResumed(TriggerKey triggerKey) {
//		LOGGER.info("triggerResumed");
	}

	@Override
	public void triggersResumed(String triggerGroup) {
//		LOGGER.info("triggersResumed");
	}

	@Override
	public void jobAdded(JobDetail jobDetail) {
//		LOGGER.info("jobAdded");
	}

	@Override
	public void jobDeleted(JobKey jobKey) {
//		LOGGER.info("jobDeleted");
	}

	@Override
	public void jobPaused(JobKey jobKey) {
//		LOGGER.info("jobPaused");
	}

	@Override
	public void jobsPaused(String jobGroup) {
//		LOGGER.info("jobsPaused");
	}

	@Override
	public void jobResumed(JobKey jobKey) {
//		LOGGER.info("jobResumed");
	}

	@Override
	public void jobsResumed(String jobGroup) {
//		LOGGER.info("jobsResumed");
	}

	@Override
	public void schedulerError(String msg, SchedulerException cause) {
//		LOGGER.info("schedulerError");
	}

	@Override
	public void schedulerInStandbyMode() {
//		LOGGER.info("schedulerInStandbyMode");
	}

	@Override
	public void schedulerStarted() {
		Map<String,String> schedulerMap = buildSchedulerMap();
		schedulerMap.put("scheduledDate", new Date().getTime()+"");
		try {
			List<String> triggerGroups = this.scheduler.getTriggerGroupNames();
			for(String triggerGroup : triggerGroups){
				Set<TriggerKey> triggerKeys = this.scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(triggerGroup));
				for(TriggerKey triggerKey : triggerKeys){
					Trigger trigger = this.scheduler.getTrigger(triggerKey);
					Map<String,String> result = new HashMap<String, String>();
					result.putAll(schedulerMap);
					result.put(EVENT_TYPE, "schedulerStarted");
					configCronInfo(trigger, result);
					result.putAll(this.buildTriggerMap(trigger));
					JobKey jobKey = trigger.getJobKey();
					JobDetail jobDetail = this.scheduler.getJobDetail(jobKey);
					result.putAll(this.buildJobMap(jobDetail,null));
					this.sendNotification(result);
				}
			}
//			LOGGER.info("schedulerStarted");
		} catch (SchedulerException e) {
			LOGGER.error(e.toString());
		}
	}

	@Override
	public void schedulerShutdown() {
//		LOGGER.info("schedulerShutdown");
	}

	@Override
	public void schedulerShuttingdown() {
//		LOGGER.info("schedulerShuttingdown");
	}

	@Override
	public void schedulingDataCleared() {
//		LOGGER.info("schedulingDataCleared");
	}
	
	protected Map<String,String> buildAllJsonMap(JobExecutionContext context) {
		Map<String,String> result = buildSchedulerMap();
		try {
			//trigger相关的属性
			result.putAll(buildTriggerMap(context.getTrigger()));
			
			//job相关的属性
			result.putAll(buildJobMap(context.getJobDetail(),context));
		} catch (Exception e) {
			// 忽略异常
			LOGGER.error(e.getMessage(),e.toString());
		}
		return result;
	}
	
	protected Map<String,String> buildJobMap(JobDetail jobDetail,JobExecutionContext context){
		Map<String,String> result = new HashMap<String, String>();
		result.put("jobName", jobDetail.getKey().getName());
		result.put("jobDescription", defuaultIfNull(jobDetail.getDescription()));
		result.put("jobGroup", jobDetail.getKey().getGroup());
		
		if(context == null){
			result.put("isRecovering", "-1");//任务是否支持重试
			result.put("scheduledFireTime", negativeIfNull(null));//job的实际执行开始时间
			result.put("jobRunTime","");//job实际的执行时间，如果执行出现异常或job未执行完毕，返回-1
			result.put("refireCount","");//job重试次数
			result.put("UUID", defuaultIfNull(null));
		}else{
			result.put("isRecovering", context.isRecovering()?"1":"0");//任务是否支持重试
			result.put("scheduledFireTime", negativeIfNull(context.getScheduledFireTime()));//job的实际执行开始时间
			result.put("jobRunTime", context.getJobRunTime()+"");//job实际的执行时间，如果执行出现异常或job未执行完毕，返回-1
			result.put("refireCount", context.getRefireCount()+"");//job重试次数
			result.put("UUID", defuaultIfNull(context.get("UUID")));
		}
		
		return result;
	}
	
	protected Map<String,String> buildSchedulerMap(){
		Map<String,String> result = new HashMap<String, String>();
		result.put("host", this.host);
		result.put("appName", appName);
		//调度器相关的属性
		result.put("schedulerInstanceId", this.schedulerInstanceId);
		result.put("schedulerName", this.schedulerName);
		result.put("schedulerVersion", this.schedulerVersion);
		return result;
	}
	
	private Map<String,String> buildTriggerMap(Trigger trigger){
		Map<String,String> result = new HashMap<String, String>();
		//trigger相关的属性
		result.put("triggerName", trigger.getKey().getName());
		result.put("triggerGroup", defuaultIfNull(trigger.getKey().getGroup()));
//		result.put("triggerDescription", defuaultIfNull(trigger.getDescription()));
		result.put("nextFireTime", negativeIfNull(trigger.getNextFireTime()));
		result.put("previousFireTime", negativeIfNull(trigger.getPreviousFireTime()));
		result.put("triggerPriority", trigger.getPriority()+"");
		result.put("endTime", negativeIfNull(trigger.getEndTime()));
		result.put("finalFireTime", negativeIfNull(trigger.getFinalFireTime()));
		result.put("startTime", negativeIfNull(trigger.getStartTime()));
		result.put("calendarName", defuaultIfNull(trigger.getCalendarName()));
		return result;
	}
	
	private String negativeIfNull(Date date){
		return date == null ? "-1":date.getTime()+"";
	}
	
	private String defuaultIfNull(Object obj){
		return obj == null ? "" : obj.toString();
	}
	
	protected void sendNotification(final Map<String,String> dataMap){
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				try{
					jmsTemplate.convertAndSend(destinationName, dataMap);
				}catch (Exception e) {
					LOGGER.error(e.toString());
				}
			}
		}) ;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.scheduler);
		this.schedulerName = scheduler.getSchedulerName();
		this.schedulerInstanceId = scheduler.getSchedulerInstanceId();
		this.schedulerVersion = this.scheduler.getMetaData().getVersion();
		//添加监听器
		this.scheduler.getListenerManager().addJobListener(this);
		this.scheduler.getListenerManager().addSchedulerListener(this);
		this.scheduler.getListenerManager().addTriggerListener(this);
		
		this.host = InetAddress.getLocalHost().getHostAddress();
		this.appName = defuaultIfNull(HOPConstant.getAppName());
	}
}
