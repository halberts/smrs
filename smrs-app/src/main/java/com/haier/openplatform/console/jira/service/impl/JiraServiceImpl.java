package com.haier.openplatform.console.jira.service.impl;

import hudson.plugins.jira.soap.JiraSoapService;
import hudson.plugins.jira.soap.JiraSoapServiceServiceLocator;
import hudson.plugins.jira.soap.RemoteComponent;
import hudson.plugins.jira.soap.RemoteCustomFieldValue;
import hudson.plugins.jira.soap.RemoteField;
import hudson.plugins.jira.soap.RemoteIssue;
import hudson.plugins.jira.soap.RemoteIssueType;
import hudson.plugins.jira.soap.RemotePriority;
import hudson.plugins.jira.soap.RemoteProject;
import hudson.plugins.jira.soap.RemoteVersion;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haier.openplatform.console.jira.dao.SoaSysContrastDAO;
import com.haier.openplatform.console.jira.domain.SoaSysContrast;
import com.haier.openplatform.console.jira.service.JiraService;
import com.haier.openplatform.hmc.domain.Email;
import com.haier.openplatform.hmc.domain.Recipient;
import com.haier.openplatform.hmc.sender.email.SendEmailService;
import com.smrs.util.Env;

public class JiraServiceImpl implements JiraService {

	private static final Logger LOGGER = LoggerFactory.getLogger(JiraServiceImpl.class);
	
	private JiraSoapServiceServiceLocator jiraSoapServiceServiceLocator;
	private JiraSoapService jiraSoapService;
	private SendEmailService emailSender;
	private SoaSysContrastDAO soaSysContrastDAO;
	private String jiraSoapUrl;
	private String jiraUser;
	private String jiraPassword;

	public void setJiraSoapServiceServiceLocator(
			JiraSoapServiceServiceLocator jiraSoapServiceServiceLocator) {
		this.jiraSoapServiceServiceLocator = jiraSoapServiceServiceLocator;
	}

	public void setEmailSender(SendEmailService emailSender) {
		this.emailSender = emailSender;
	}

	public void setSoaSysContrastDAO(SoaSysContrastDAO soaSysContrastDAO) {
		this.soaSysContrastDAO = soaSysContrastDAO;
	}
	
	public void setJiraSoapUrl(String jiraSoapUrl) {
		this.jiraSoapUrl = jiraSoapUrl;
	}

	public void setJiraUser(String jiraUser) {
		this.jiraUser = jiraUser;
	}

	public void setJiraPassword(String jiraPassword) {
		this.jiraPassword = jiraPassword;
	}

	@Override
	public RemoteIssue createIssue(String projectKey,String summary,String description,String issueType,Calendar dueDate){
		JiraSoapService jss = this.getJiraSoapService();
		String token = null;
		try{
			token = jss.login(jiraUser, jiraPassword);
			
			RemoteProject project = this.getProjectByKey(projectKey,token);
			if(project == null){
				LOGGER.error(projectKey + " not exist in jira.");
				return null;
			}
			RemoteIssue issue = new RemoteIssue();
			issue.setProject(project.getKey());                     //项目key
//			issue.setType(this.getTypes(token)[4].getId());         //问题类型
			issue.setType(issueType);                               //问题类型
			//issue.setComponents(this.getComponents(token, project.getKey()));//模块
			issue.setSummary(summary);                              //主题
			issue.setCustomFieldValues(this.getCustomFields(token));//自定义字段
			issue.setDuedate(dueDate);                              //到期日
			issue.setAssignee(project.getLead());                   //经办人
			issue.setPriority(this.getPrioritys(token)[2].getId()); //优先级
			issue.setDescription(description);                      //描述
			return jss.createIssue(token, issue);
		}catch(RemoteException e){
			LOGGER.error("JiraSoapService error.",e);
			return null;
		}catch(Exception e){
			LOGGER.error("createIssue error,projectKey=" + projectKey,e);
			return null;
		}finally{
			if(jss != null && token != null){
				try {
					jss.logout(token);
				} catch (RemoteException e) {
					LOGGER.error("jira logout error.",e);
				}
			}
		}
	}

	@Override
	public RemoteIssue[] getIssuesByTextFromProject(String token,String[] projectKeys,String textKey){
		JiraSoapService jss = this.getJiraSoapService();
		RemoteIssue[] issues = null;
		try{
			issues = jss.getIssuesFromTextSearchWithProject(token, projectKeys, textKey, Integer.MAX_VALUE);
//			issues = jss.getIssuesFromTextSearch(token, textKey);
		}catch(Exception e){
			LOGGER.error("getIssuesByTextFromProject error.textKey=" + textKey ,e);
		}
		return issues;
	}
	
	@Override
	public String loginJira(){
		try{
			JiraSoapService jss = this.getJiraSoapService();
			return jss.login(jiraUser, jiraPassword);
		}catch(Exception e){
			LOGGER.error("loginJira error." + e);
			return null;
		}
	}
	
	@Override
	public void logoutJira(String token){
		JiraSoapService jss = this.getJiraSoapService();
		try {
			jss.logout(token);
		} catch (RemoteException e) {
			LOGGER.error("logout error." + e);
		}
	}
	
	/**
	 * 取得jira连接
	 * @return
	 */
	private JiraSoapService getJiraSoapService(){
		if(jiraSoapService == null){
			try {
				jiraSoapServiceServiceLocator.setJirasoapserviceV2EndpointAddress(jiraSoapUrl);
				jiraSoapService = jiraSoapServiceServiceLocator.getJirasoapserviceV2();
			} catch (ServiceException e) {
				LOGGER.error("getJirasoapserviceV2 error.",e);
			} catch (Exception e){
				LOGGER.error("getJiraSoapService error.",e);
			}
		}
		return jiraSoapService;
	}
	
	/**
	 * 根据项目的key查询项目
	 * @param sonarProjectKey
	 * @param token
	 * @return
	 */
	private RemoteProject getProjectByKey(String sonarProjectKey,String token){
		if(StringUtils.isBlank(sonarProjectKey)){
			return null;
		}
		RemoteProject pro = null;
		try{
			RemoteProject[] projects = getProjects(token);
			for(RemoteProject rp : projects){
				if(!sonarProjectKey.equalsIgnoreCase(rp.getKey())){
					continue;
				}
				pro = rp;
				break;
			}
		}catch(Exception e){
			LOGGER.error("getProjectKey error,sonarProjectKey=" + sonarProjectKey,e);
		}
		return pro;
	}
	
	/**
	 * 取得jira上的所有项目
	 * @param token
	 * @return
	 * @throws Exception
	 */
	private RemoteProject[] getProjects(String token) throws Exception{
		return getJiraSoapService().getProjectsNoSchemes(token);
	}
	
	/**
	 * 取得自定义字段
	 * @param token
	 * @return
	 * @throws Exception
	 */
	private RemoteCustomFieldValue[] getCustomFields(String token) throws Exception{
		RemoteField[] fields = getJiraSoapService().getCustomFields(token);
		Map<String, RemoteCustomFieldValue> values = new HashMap<String, RemoteCustomFieldValue>(2);
		values.put("需求文档URL", new RemoteCustomFieldValue(null, null, new String[]{"http://docs.atlassian.com"}));
		values.put("评价", new RemoteCustomFieldValue(null, null, new String[]{"0"}));
		
		for(RemoteField f: fields){
			RemoteCustomFieldValue v = values.get(f.getName().trim());
			if(v != null){
				v.setCustomfieldId(f.getId());
			}
		}
		return  values.values().toArray(new RemoteCustomFieldValue[values.size()]);
	}
	
	/**
	 * 取得问题类型
	 * @param token
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private RemoteIssueType[] getTypes(String token) throws Exception{
		return getJiraSoapService().getIssueTypes(token);
	}
	
	/**
	 * 取得项目的版本
	 * @param token
	 * @param projectKey
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private RemoteVersion[] getVersions(String token,String projectKey) throws Exception{
		return getJiraSoapService().getVersions(token, projectKey);
	}
	
	/**
	 * 取得项目模块
	 * @param token
	 * @param projectKey
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private RemoteComponent[] getComponents(String token,String projectKey) throws Exception{
		return getJiraSoapService().getComponents(token, projectKey);
	}
	
	/**
	 * 取得优先级
	 * @param token
	 * @return
	 * @throws Exception
	 */
	private RemotePriority[] getPrioritys(String token) throws Exception{
		return getJiraSoapService().getPriorities(token);
	}
	
	@Override
	public void sendEmailToPsi(String emailContent){
		Email email = new Email();
	    Recipient recipient = new Recipient(); 
	    recipient.setUserName("HOP-监控平台");
	    recipient.setEmailAddress(Env.getProperty(Env.APP_EMAIL.isEmpty()||Env.APP_EMAIL == null ? "" : Env.APP_EMAIL));
	    email.setSender(recipient); 
	    List<Recipient> toRecipientList = new ArrayList<Recipient>();
	    Recipient reci = new Recipient();
	    reci.setUserName("赵晨");
	    reci.setEmailAddress("zhaoc@haier.com");
	    Recipient reci1 = new Recipient();
	    reci1.setUserName("张涛");
	    reci1.setEmailAddress("zhangt.psi@haier.com");
	    toRecipientList.add(reci);
	    toRecipientList.add(reci1);
	    email.setToRecipient(toRecipientList);
	    email.setSubject("HOP自动创建的jira问题");
	    email.setBodyContent(emailContent, false);
	    
		emailSender.sendEmail(email);
	}

	@Override
	public SoaSysContrast queryJiraByAppId(Long appId) {
		return soaSysContrastDAO.queryJiraByAppId(appId);
	}

	@Override
	public SoaSysContrast queryJiraByAppName(String appName) {
		if(StringUtils.isBlank(appName)){
			return null;
		}
		return soaSysContrastDAO.queryJiraByAppName(appName.toUpperCase());
	}

}
