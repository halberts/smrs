package com.haier.openplatform.console.jira.module;
/**
 * 开发管理对象
 */


public class ManagermentModel{
	private int jjzcount; 		//解决问题数量
	private int wgsuecount;		//违规需求
	private int suedoczcount; 	//需求文档数量
	private int lsuedoczcount;	//需求文档缺少数量
	private int tecdoczcount;	//技术文档数量
	private int ltecdoczcount;	//技术文档缺少数量
	private int vezcount;		//提交版本数量
	private int lvezcount;		//发版管理规定(未提交版本数量)
	private int lcode;			//未提交代码(代码未及时更新)
	private int ldocument;		//工作日志、文档等不合规
	private int lplatform;		//平台规范(预计开始日期缺少数量)
	
	public int getJjzcount() {
		return jjzcount;
	}
	public void setJjzcount(int jjzcount) {
		this.jjzcount = jjzcount;
	}
	public int getSuedoczcount() {
		return suedoczcount;
	}
	public void setSuedoczcount(int suedoczcount) {
		this.suedoczcount = suedoczcount;
	}
	public int getLsuedoczcount() {
		return lsuedoczcount;
	}
	public void setLsuedoczcount(int lsuedoczcount) {
		this.lsuedoczcount = lsuedoczcount;
	}
	public int getTecdoczcount() {
		return tecdoczcount;
	}
	public void setTecdoczcount(int tecdoczcount) {
		this.tecdoczcount = tecdoczcount;
	}
	public int getLtecdoczcount() {
		return ltecdoczcount;
	}
	public void setLtecdoczcount(int ltecdoczcount) {
		this.ltecdoczcount = ltecdoczcount;
	}
	public int getVezcount() {
		return vezcount;
	}
	public void setVezcount(int vezcount) {
		this.vezcount = vezcount;
	}
	public int getLvezcount() {
		return lvezcount;
	}
	public void setLvezcount(int lvezcount) {
		this.lvezcount = lvezcount;
	}
	public int getWgsuecount() {
		return wgsuecount;
	}
	public void setWgsuecount(int wgsuecount) {
		this.wgsuecount = wgsuecount;
	}
	public int getLcode() {
		return lcode;
	}
	public void setLcode(int lcode) {
		this.lcode = lcode;
	}
	public int getLdocument() {
		return ldocument;
	}
	public void setLdocument(int ldocument) {
		this.ldocument = ldocument;
	}
	public int getLplatform() {
		return lplatform;
	}
	public void setLplatform(int lplatform) {
		this.lplatform = lplatform;
	}
	
	
}
