package com.haier.openplatform.console.jira.module;


/**
 * 开发人天对象
 */


public class TimeWorkedModel{
	private String dispalyname;	//经办人
	private String id;
	private int suecount;		//需求数量
	private int submitdayscount; 	//提报人天
	private int contractdayscount;	//合同人天
	private int vacationcount;		//请假天数
	private int neglectworkcount;	//未申请请假天数
	private int price;
	private int kqday;	//考勤天数
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getDispalyname() {
		return dispalyname;
	}
	public void setDispalyname(String dispalyname) {
		this.dispalyname = dispalyname;
	}
	public int getSuecount() {
		return suecount;
	}
	public void setSuecount(int suecount) {
		this.suecount = suecount;
	}
	public int getSubmitdayscount() {
		return submitdayscount;
	}
	public void setSubmitdayscount(int submitdayscount) {
		this.submitdayscount = submitdayscount;
	}
	public int getContractdayscount() {
		return contractdayscount;
	}
	public void setContractdayscount(int contractdayscount) {
		this.contractdayscount = contractdayscount;
	}
	public int getVacationcount() {
		return vacationcount;
	}
	public void setVacationcount(int vacationcount) {
		this.vacationcount = vacationcount;
	}
	public int getNeglectworkcount() {
		return neglectworkcount;
	}
	public void setNeglectworkcount(int neglectworkcount) {
		this.neglectworkcount = neglectworkcount;
	}
	public int getKqday() {
		return kqday;
	}
	public void setKqday(int kqday) {
		this.kqday = kqday;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	
	
}

