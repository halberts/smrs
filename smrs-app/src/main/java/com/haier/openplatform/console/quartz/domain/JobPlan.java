package com.haier.openplatform.console.quartz.domain;

import java.util.Date;

import com.haier.openplatform.domain.BaseDomain;

/**
 * @author WangXuzheng
 *
 */
public class JobPlan extends BaseDomain<Long> {
	private static final long serialVersionUID = -2048682197058809371L;
	private String schedulerName;
	private String triggerName;
	private String triggerGroup;
	private int triggerPriority;
	private String jobName;
	private String jobGroup;
	private String jobDescription;
	private String schedulerVersion;
	private String calenderName;
	private String cronExpression;
	private String triggerType;
	private Date scheduledDate;
	private String host;
	private String schedulerInstanceId;
	private String appName;
	public String getSchedulerName() {
		return schedulerName;
	}
	public void setSchedulerName(String schedulerName) {
		this.schedulerName = schedulerName;
	}
	public String getTriggerName() {
		return triggerName;
	}
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}
	public String getTriggerGroup() {
		return triggerGroup;
	}
	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}
	
	public int getTriggerPriority() {
		return triggerPriority;
	}
	public void setTriggerPriority(int triggerPriority) {
		this.triggerPriority = triggerPriority;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public String getSchedulerVersion() {
		return schedulerVersion;
	}
	public void setSchedulerVersion(String schedulerVersion) {
		this.schedulerVersion = schedulerVersion;
	}
	public String getCalenderName() {
		return calenderName;
	}
	public void setCalenderName(String calenderName) {
		this.calenderName = calenderName;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public String getTriggerType() {
		return triggerType;
	}
	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}
	public Date getScheduledDate() {
		return scheduledDate;
	}
	public void setScheduledDate(Date scheduledDate) {
		this.scheduledDate = scheduledDate;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getSchedulerInstanceId() {
		return schedulerInstanceId;
	}
	public void setSchedulerInstanceId(String schedulerInstanceId) {
		this.schedulerInstanceId = schedulerInstanceId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getJobDescription() {
		return jobDescription;
	}
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
}
