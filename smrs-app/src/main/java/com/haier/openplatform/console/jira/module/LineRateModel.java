package com.haier.openplatform.console.jira.module;

import java.sql.Date;

/**
 * 
 * @author lin
 * 线形图开发及时率和日期
 *
 */
public class LineRateModel {
	private double jsl;
	private int maxdate;
	private int startdate;
	/**private Date startTime;
	private Date endTime;*/
	public double getJsl() {
		return jsl;
	}
	public void setJsl(double jsl) {
		this.jsl = jsl;
	}
	public int getMaxdate() {
		return maxdate;
	}
	public void setMaxdate(int maxdate) {
		this.maxdate = maxdate;
	}
	public int getStartdate() {
		return startdate;
	}
	public void setStartdate(int startdate) {
		this.startdate = startdate;
	}
	/**public Date getStartTime() {
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
	*/
	
	

}
