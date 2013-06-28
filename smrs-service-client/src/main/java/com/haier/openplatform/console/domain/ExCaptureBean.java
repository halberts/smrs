package com.haier.openplatform.console.domain;

import java.lang.reflect.Field;

import com.haier.openplatform.hmc.domain.MessageModule;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ExCaptureBean")
public class ExCaptureBean extends MessageModule{

	private static final long serialVersionUID = 5266561182252589012L;

	@XStreamAlias("issueTypeName")
	private String issueTypeName;

	@XStreamAlias("serviceAPIName")
	private String serviceAPIName;

	@XStreamAlias("appName")
	private String appName;

	@XStreamAlias("actionName")
	private String actionName;

	@XStreamAlias("nodeName")
	private String nodeName;

	@XStreamAlias("nodeIP")
	private String nodeIP;

	@XStreamAlias("errorCD")
	private String errorCD;

	@XStreamAlias("exceptionNM")
	private String exceptionNM;

	@XStreamAlias("inputValue")
	private String inputValue;

	@XStreamAlias("serviceException")
	private String serviceException;

	@XStreamAlias("insight")
	private String insight;

	@XStreamAlias("excuteTime")
	private String excuteTime;

	@XStreamAlias("currentThreadNumber")
	private String currentThreadNumber;

	@XStreamAlias("gmtCreate")
	private String gmtCreate;

	public String getIssueTypeName() {
		return issueTypeName;
	}

	public void setIssueTypeName(String issueTypeName) {
		this.issueTypeName = issueTypeName;
	}

	public String getServiceAPIName() {
		return serviceAPIName;
	}

	public void setServiceAPIName(String serviceAPIName) {
		this.serviceAPIName = serviceAPIName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeIP() {
		return nodeIP;
	}

	public void setNodeIP(String nodeIP) {
		this.nodeIP = nodeIP;
	}

	public String getErrorCD() {
		return errorCD;
	}

	public void setErrorCD(String errorCD) {
		this.errorCD = errorCD;
	}

	public String getExceptionNM() {
		return exceptionNM;
	}

	public void setExceptionNM(String exceptionNM) {
		this.exceptionNM = exceptionNM;
	}

	public String getInputValue() {
		return inputValue;
	}

	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}

	public String getServiceException() {
		return serviceException;
	}

	public void setServiceException(String serviceException) {
		this.serviceException = serviceException;
	}

	public String getInsight() {
		return insight;
	}

	public void setInsight(String insight) {
		this.insight = insight;
	}

	public String getExcuteTime() {
		return excuteTime;
	}

	public void setExcuteTime(String excuteTime) {
		this.excuteTime = excuteTime;
	}

	public String getCurrentThreadNumber() {
		return currentThreadNumber;
	}

	public void setCurrentThreadNumber(String currentThreadNumber) {
		this.currentThreadNumber = currentThreadNumber;
	}

	public String getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String toString() {
		StringBuilder s = new StringBuilder("\r\n//==========ExCaptureBean=================\r\n");
		try {
			Class<? extends ExCaptureBean> class1 = this.getClass();
			Field afield[] = class1.getDeclaredFields();
			for (int i = 0; i < afield.length; i++){
				s.append(afield[i].getName() + ":" + afield[i].get(this) + "\r\n");
			}
		} catch (Exception exception) {
			exception.printStackTrace(System.out);
		}
		s.append("============ExCaptureBean============\r\n");
		return s.toString();
	}
}
