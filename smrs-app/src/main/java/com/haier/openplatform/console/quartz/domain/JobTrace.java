package com.haier.openplatform.console.quartz.domain;

import java.util.Date;

import com.haier.openplatform.domain.BaseDomain;

/**
 * @author WangXuzheng
 *
 */
public class JobTrace extends BaseDomain<String> {
	private static final long serialVersionUID = -7559511679265005015L;
	private JobPlan jobPlan = new JobPlan();
	private String state;
	private Date nextFireTime;
	private Date previousFireTime;
	private String errorMessage;
	private String host;
	private Date startTime;
	private Date endTime;
	private Date finalFireTime;
	private String calendarName;
	private int isRecovering;
	private Date scheduledFireTime;
	private long jobRunTime;
	private int refireCount;
	private String appName;
	public JobPlan getJobPlan() {
		return jobPlan;
	}
	public void setJobPlan(JobPlan jobPlan) {
		this.jobPlan = jobPlan;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getNextFireTime() {
		return nextFireTime;
	}
	public void setNextFireTime(Date nextFireTime) {
		this.nextFireTime = nextFireTime;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Date getPreviousFireTime() {
		return previousFireTime;
	}
	public void setPreviousFireTime(Date previousFireTime) {
		this.previousFireTime = previousFireTime;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getFinalFireTime() {
		return finalFireTime;
	}
	public void setFinalFireTime(Date finalFireTime) {
		this.finalFireTime = finalFireTime;
	}
	public String getCalendarName() {
		return calendarName;
	}
	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}
	public int getIsRecovering() {
		return isRecovering;
	}
	public void setIsRecovering(int isRecovering) {
		this.isRecovering = isRecovering;
	}
	public Date getScheduledFireTime() {
		return scheduledFireTime;
	}
	public void setScheduledFireTime(Date scheduledFireTime) {
		this.scheduledFireTime = scheduledFireTime;
	}
	public long getJobRunTime() {
		return jobRunTime;
	}
	public void setJobRunTime(long jobRunTime) {
		this.jobRunTime = jobRunTime;
	}
	public int getRefireCount() {
		return refireCount;
	}
	public void setRefireCount(int refireCount) {
		this.refireCount = refireCount;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
}
