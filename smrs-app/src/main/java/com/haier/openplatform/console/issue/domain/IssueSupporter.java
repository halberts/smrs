package com.haier.openplatform.console.issue.domain;

import com.haier.openplatform.domain.BaseDomain;

/**
 * @author shanjing 持久化类：错误负责人及汇报情况
 */
public class IssueSupporter extends BaseDomain<Long> {

	private static final long serialVersionUID = 3662977230934385383L;

	/**
	 * 海尔负责人姓名
	 */
	private String ownerName;

	/**
	 * 海尔负责人Email
	 */
	private String ownerEmail;

	/**
	 * 海尔负责人电话
	 */
	private String ownerPhone;

	/**
	 * 供应商负责人姓名
	 */
	private String sptOwnerName;

	/**
	 * 供应商负责人Email
	 */
	private String sptOwnerEmail;

	/**
	 * 供应商负责人电话
	 */
	private String sptOwnerPhone;

	/**
	 * 供应商Email
	 */
	private String sptEmail;

	/**
	 * 供应商电话
	 */
	private String sptPhone;

	/**
	 * 标记位(目前未使用)
	 */
	private String flag;

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public String getOwnerPhone() {
		return ownerPhone;
	}

	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
	}

	public String getSptOwnerEmail() {
		return sptOwnerEmail;
	}

	public void setSptOwnerEmail(String sptOwnerEmail) {
		this.sptOwnerEmail = sptOwnerEmail;
	}

	public String getSptOwnerPhone() {
		return sptOwnerPhone;
	}

	public void setSptOwnerPhone(String sptOwnerPhone) {
		this.sptOwnerPhone = sptOwnerPhone;
	}

	public String getSptEmail() {
		return sptEmail;
	}

	public void setSptEmail(String sptEmail) {
		this.sptEmail = sptEmail;
	}

	public String getSptPhone() {
		return sptPhone;
	}

	public void setSptPhone(String sptPhone) {
		this.sptPhone = sptPhone;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getSptOwnerName() {
		return sptOwnerName;
	}

	public void setSptOwnerName(String sptOwnerName) {
		this.sptOwnerName = sptOwnerName;
	}

	@Override
	public String toString() {
		return "IssueSupporter [ownerName=" + ownerName + ", ownerEmail="
				+ ownerEmail + ", ownerPhone=" + ownerPhone + ", sptOwnerName="
				+ sptOwnerName + ", sptOwnerEmail=" + sptOwnerEmail
				+ ", sptOwnerPhone=" + sptOwnerPhone + ", sptEmail=" + sptEmail
				+ ", sptPhone=" + sptPhone + ", flag=" + flag + "]";
	}

}
