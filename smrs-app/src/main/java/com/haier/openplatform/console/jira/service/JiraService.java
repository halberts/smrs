package com.haier.openplatform.console.jira.service;

import hudson.plugins.jira.soap.RemoteIssue;

import java.util.Calendar;

import com.haier.openplatform.console.jira.domain.SoaSysContrast;

public interface JiraService {

	/**
	 * 登录jira
	 * @return 登录后返回token
	 */
	public String loginJira();
	
	/**
	 * 注销jira
	 * @param token
	 */
	public void logoutJira(String token);
	
	/**
	 * 创建问题
	 * @param projectKey   项目key
	 * @param summary      主题
	 * @param description  描述
	 * @param issueType    问题类型
	 * @param dueDate      到期日
	 * 
	 * @return RemoteIssue
	 */
	public RemoteIssue createIssue(String projectKey,String summary,String description,String issueType,Calendar dueDate);
	
	/**
	 * 根据关键字查询项目下的问题
	 * @param token        登录jira后返回的token
	 * @param projectKeys  项目的key
	 * @param textKey      关键字
	 * @return 返回问题列表
	 */
	public RemoteIssue[] getIssuesByTextFromProject(String token,String[] projectKeys,String textKey);
	
	/**
	 * 发送jira问题邮件给psi人员
	 * @param emailContent
	 */
	public void sendEmailToPsi(String emailContent);
	
	/**
	 * 根据应用id查询jira
	 * @param appId
	 * @return
	 */
	public SoaSysContrast queryJiraByAppId(Long appId);
	
	/**
	 * 根据应用名称查询jira
	 * @param appName
	 * @return
	 */
	public SoaSysContrast queryJiraByAppName(String appName);
}
