/**
 * 
 */
package com.haier.openplatform.console.util;

import java.text.SimpleDateFormat;

/**
 * Generic/Central hop platform constant
 * @author mk
 *
 */
public interface HOPCONS {

	// please list out all of possible Haier class prefix,should end with .
	public static final String[] PKG_PREFIX = { "com.haier." };

	String STR_NOT_DEFINED = "ISNULL";
	
	String HOP_RELEASE_VERSION = " HOPCore v2.0.0-20130221.17:00";

	String AUDIT_EX = "1";
	String AUDIT_EX_ERR = "2";
	String AUDIT_OT = "3";
	String AUDIT_BETA = "4";
	String AUDIT_DEBUG = "5";

	String NOT_DEFINED_MAP = "NOT_DEFINED_MAP";
	String TO_BE_DEFINED = "TO_BE_DEFINED";
	String DEFAULT_USER = "PSI";
	String DEFAULT_HOP_EMAIL_SENDER = "HOPNotifier@haier.com";
	String DEFAULT_HOP_EMAIL_SENDER_NM = "HOP Center";

	String HOP_APP_NAME = "server.name";

	String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(HOPCONS.DATE_FORMAT_NOW);
	
	String QUEUE_LOGGER_PREFIX = "\r\nQUEUE CHECKING:: ";
	String LOGGER_PREFIX = ":::::::";
	String QUEUE_LOGGER_SEG = "::";
	String QUEUE_LOGGER_WARNING = " :-:-: ATTENTION:!!!! ";
	String LOGGER_RETURN = "\r\n";
	
	public static final String[] EXCLUDE_SERVICE= 
		{"openplatform.console.issue.service.impl.IssueQueueServiceImpl.saveInvokeSumQueue",
		"openplatform.console.issue.service.impl.IssueQueueServiceImpl.saveIssueQueue",
		"openplatform.console.quartz.service.impl.SyncWorkerServiceImpl.run",
		"openplatform.console.quartz.service.impl.PersistWorkerServiceImpl.run",
		"openplatform.session.service.impl.SessionServiceImpl.getSession",
		"openplatform.session.service.impl.SessionServiceImpl.updateSession",
		"openplatform.console.quartz.service.impl.JobHisServiceImpl.queryLatestJobs",
		"openplatform.console.quartz.service.impl.JobHisServiceImpl.queryLatestJoinedJobs"};

}
