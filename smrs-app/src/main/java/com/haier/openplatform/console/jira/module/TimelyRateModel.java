package com.haier.openplatform.console.jira.module;
/**
 * 开发及时率对象
 */


public class TimelyRateModel{
	private String dispalyName;
	private double jjcount;
	private double timeworked;
	private double yqcount;
	private double yqtimeworked;
	private int tqday;
	private double tql;
	private double price;
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDispalyName() {
		return dispalyName;
	}
	public void setDispalyName(String dispalyName) {
		this.dispalyName = dispalyName;
	}
	public double getJjcount() {
		return jjcount;
	}
	public void setJjcount(double jjcount) {
		this.jjcount = jjcount;
	}
	public double getTimeworked() {
		return timeworked;
	}
	public void setTimeworked(double timeworked) {
		this.timeworked = timeworked;
	}
	public double getYqcount() {
		return yqcount;
	}
	public void setYqcount(double yqcount) {
		this.yqcount = yqcount;
	}
	public double getYqtimeworked() {
		return yqtimeworked;
	}
	public void setYqtimeworked(double yqtimeworked) {
		this.yqtimeworked = yqtimeworked;
	}
	public int getTqday() {
		return tqday;
	}
	public void setTqday(int tqday) {
		this.tqday = tqday;
	}
	public double getTql() {
		return tql;
	}
	public void setTql(double tql) {
		this.tql = tql;
	}
//	public void setKje(double kje) {
//		
//		this.kje = kje;
//	}
//	public double getKje() {
//		if(getTql()>10){
//			kje=getTqday()*1000*0.2;
//		}else{
//			kje = getTqday()*0.8*1000;
//		}
//		return kje;
//	}
}
