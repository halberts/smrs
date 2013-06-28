package com.haier.openplatform.console.jira.module;
/**
 * 开发质量对象
 */


public class QualityModel{
	private int dowmtimecount; 	//系统宕机或业务无法执行
	private int bugcount;		//BUG
	private int errorcount; 	//Error数
	private int warningcount;	//Warning数
	private int infocount;		//Info数
	private int failurecount;	//failure数
	private int unitcount;		//单元测试覆盖率低于60%
	private int gjfalsecount;	//未能构建成功
	private int goodprocount;	//优秀项目
	public int getDowmtimecount() {
		return dowmtimecount;
	}
	public void setDowmtimecount(int dowmtimecount) {
		this.dowmtimecount = dowmtimecount;
	}
	public int getBugcount() {
		return bugcount;
	}
	public void setBugcount(int bugcount) {
		this.bugcount = bugcount;
	}
	public int getErrorcount() {
		return errorcount;
	}
	public void setErrorcount(int errorcount) {
		this.errorcount = errorcount;
	}
	public int getWarningcount() {
		return warningcount;
	}
	public void setWarningcount(int warningcount) {
		this.warningcount = warningcount;
	}
	public int getInfocount() {
		return infocount;
	}
	public void setInfocount(int infocount) {
		this.infocount = infocount;
	}
	public int getFailurecount() {
		return failurecount;
	}
	public void setFailurecount(int failurecount) {
		this.failurecount = failurecount;
	}
	public int getUnitcount() {
		return unitcount;
	}
	public void setUnitcount(int unitcount) {
		this.unitcount = unitcount;
	}
	public int getGoodprocount() {
		return goodprocount;
	}
	public void setGoodprocount(int goodprocount) {
		this.goodprocount = goodprocount;
	}
	public int getGjfalsecount() {
		return gjfalsecount;
	}
	public void setGjfalsecount(int gjfalsecount) {
		this.gjfalsecount = gjfalsecount;
	}
	
	
}

