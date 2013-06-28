package com.haier.openplatform.console.issue.domain;

import com.haier.openplatform.domain.BaseDomain;

/**
 * @author shanjing 持久化类:Service超时等级
 */
public class ServiceTimeoutLv extends BaseDomain<String> {

	private static final long serialVersionUID = 6553233539763798895L;

	/**
	 * 超时时间
	 */
	private Long overtimeMsec;
	/**
	 * LV个数 
	 */
	private Long num;
	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public Long getOvertimeMsec() {
		return overtimeMsec;
	}

	public void setOvertimeMsec(Long overtimeMsec) {
		this.overtimeMsec = overtimeMsec;
	}

	@Override
	public String toString() {
		return "ServiceTimeoutLv [overtimeMsec=" + overtimeMsec + "]";
	}

}
