package com.haier.openplatform.console.issue.domain;

import java.util.Date;

import com.haier.openplatform.domain.BaseDomain;

/**
 * @author shanjing
 * 短信发送记录
 */
/**
 * @author shanjing
 * 
 */
public class HopSms extends BaseDomain<Long> {

	private static final long serialVersionUID = -673937881760576169L;

	/**
	 * 发送时间（带时分秒）
	 */
	private String sendTime;

	/**
	 * 系统名称
	 */
	private String system;

	/**
	 * 消息发送时间
	 */
	private Date msgSendTime;

	/**
	 * 短信发送时间
	 */
	private Date smsSendTime;

	/**
	 * 收信人号码
	 */
	private String phoneNum;

	/**
	 * 短信内容
	 */
	private String content;

	/**
	 * 消息唯一标识码
	 */
	private String msgCode;

	/**
	 * 短信服务器返回码
	 */
	private String returnCode;

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public Date getMsgSendTime() {
		return msgSendTime;
	}

	public void setMsgSendTime(Date msgSendTime) {
		this.msgSendTime = msgSendTime;
	}

	public Date getSmsSendTime() {
		return smsSendTime;
	}

	public void setSmsSendTime(Date smsSendTime) {
		this.smsSendTime = smsSendTime;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	@Override
	public String toString() {
		return "HopSms [sendTime=" + sendTime + ", system=" + system
				+ ", msgSendTime=" + msgSendTime + ", smsSendTime="
				+ smsSendTime + ", phoneNum=" + phoneNum + ", content="
				+ content + ", msgCode=" + msgCode + ", returnCode="
				+ returnCode + "]";
	}

}
