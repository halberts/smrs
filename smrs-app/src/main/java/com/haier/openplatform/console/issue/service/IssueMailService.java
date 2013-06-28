package com.haier.openplatform.console.issue.service;

/**
 * @author shanjing
 *
 */
public interface IssueMailService {

	/**
	 * 发送邮件和短信给系统负责人
	 */
	public void shootUnsentIssues();
	/**
	 * shoot only over-time issues 
	 */
	public void shootOvertimeIssues();
}
