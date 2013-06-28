package com.haier.openplatform.console.issue.module;

import java.util.Date;

import com.haier.openplatform.domain.BaseDomain;

public class HopEmailModel extends BaseDomain<Long> {

	private static final long serialVersionUID = 3891544661569789060L;

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
	 * 邮件SMTP发送时间
	 */
	private Date emailSendTime;

	/**
	 * 邮件主题
	 */
	private String subject;

	private String content;
	
	/**
	 * 错误信息
	 */
	private String error = "No error!";

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

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

	public Date getEmailSendTime() {
		return emailSendTime;
	}

	public void setEmailSendTime(Date emailSendTime) {
		this.emailSendTime = emailSendTime;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "HopEmail [sendTime=" + sendTime + ", system=" + system
				+ ", msgSendTime=" + msgSendTime + ", emailSendTime="
				+ emailSendTime + ", subject=" + subject + ", content="
				+ content + ", error=" + error + "]";
	}

}
