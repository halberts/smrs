package com.jof.framework.url;

import java.lang.reflect.Field;

import com.haier.openplatform.console.util.HOPCONS;

public class URLStatusBean {

	/*** 
	 * 1: Initial status without refresh<br/>
	 * 2: Success<br/>
	 * 3: FAILED<br/>
	 */
	private int urlStatus=0;
	
	private String urlStatusMsg=HOPCONS.STR_NOT_DEFINED;

	public URLStatusBean(int us, String usm){
		urlStatus=us;
		urlStatusMsg=usm;
	}
	
	public int getUrlStatus() {
		return urlStatus;
	}

	public void setUrlStatus(int urlStatus) {
		this.urlStatus = urlStatus;
	}

	public String getUrlStatusMsg() {
		return urlStatusMsg;
	}

	public void setUrlStatusMsg(String urlStatusMsg) {
		this.urlStatusMsg = urlStatusMsg;
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder("\r\n//==========URLStatusBean==========\r\n");
		try {
			Class<?> clazz = this.getClass();
			Field afield[] = clazz.getDeclaredFields();
			for (int i = 0; i < afield.length; i++){
				s.append(afield[i].getName() + ":" + afield[i].get(this) + "\r\n");
			}
		} catch (Exception exception) {
			exception.printStackTrace(System.out);
		}
		s.append("============URLStatusBean============\r\n");
		return s.toString();
	}
}
