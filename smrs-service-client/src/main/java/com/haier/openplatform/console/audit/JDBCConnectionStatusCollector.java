package com.haier.openplatform.console.audit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import com.haier.openplatform.console.util.HOPCONS;
import com.haier.openplatform.hmc.domain.Email;
import com.haier.openplatform.hmc.domain.Recipient;
import com.haier.openplatform.hmc.domain.SMS;
import com.haier.openplatform.hmc.domain.SMSMessage;
import com.haier.openplatform.hmc.sender.SendMsgService;
import com.haier.openplatform.hmc.sender.email.SendEmailService;

public class JDBCConnectionStatusCollector {

	private static final Logger LOGGER = LoggerFactory.getLogger( JDBCConnectionStatusCollector.class );
	// 15000 msec=15 s
	private static final long readInterval = 300000;
	private static final String SPLIT_SEG=",";

	private static JDBCConnectionPoolConf jDBCPoolConf=null;
	private static JDBCConnectionTracker jDBCTracker=null;
	private static SimpleAsyncTaskExecutor springExcuter=null;
	private static SendEmailService jDBCEmailSender;
	private static SendMsgService jDBCSmsSender;
	private static String envType;

	private static Email email=new Email();
	private static SMSMessage smsMessage = new SMSMessage();
	private volatile static boolean finished=false;
	
	public void init(){
		
		if (!JDBCConnectionPoolConf.isEnableJDBCConnectionTracker()){
			return;
		}
			
		try {
			springExcuter.setConcurrencyLimit(1);
			springExcuter.setThreadNamePrefix("JDBCConnectionCollectorThread");
			springExcuter.execute(new JdbcCollector());
		} catch (Exception e) {
			LOGGER.error("JDBCConnectionStatusCollector thread can not start",e);
		} finally {
			initEmail();
			initSMS();
		}		
	}
	
	public static void cleanup(){
		finished=true;
	}
	
	public void initEmail(){

		String emailA=JDBCConnectionPoolConf.getNotificationEmailAddress();
		if(emailA==null || emailA==HOPCONS.TO_BE_DEFINED){
			return;
		}
		
		String[] emailAddrs=emailA.split(SPLIT_SEG);
		
		List<Recipient> toRecipient = new ArrayList<Recipient>();
		for (String s : emailAddrs) {
			toRecipient.add(new Recipient(s, HOPCONS.DEFAULT_USER));
		}
		email.setToRecipient(toRecipient);
		email.setSubject("JDBC fatal error in your system");
		Recipient sender = new Recipient();
		sender.setEmailAddress(HOPCONS.DEFAULT_HOP_EMAIL_SENDER);
		sender.setUserName(HOPCONS.DEFAULT_HOP_EMAIL_SENDER_NM);
		email.setSender(sender);
	}
	
	public void initSMS(){
		
		String smsA=JDBCConnectionPoolConf.getNotificationPhoneNum();
		if(smsA==null || smsA==HOPCONS.TO_BE_DEFINED){
			return;
		}
		
		String[] smsNum=smsA.split(SPLIT_SEG);
		
		List<SMS> list = new ArrayList<SMS>();
		for (String s : smsNum) {
			SMS sms=new SMS();
			sms.setDepartment(HOPCONS.DEFAULT_USER);
			sms.setPhoneNum(s);
			list.add(sms);
		}
		smsMessage.setSmsList(list);
	}
	
	public static JDBCConnectionPoolConf getjDBCPoolConf() {
		return jDBCPoolConf;
	}

	public void setjDBCPoolConf(JDBCConnectionPoolConf jDBCPoolConf) {
		JDBCConnectionStatusCollector.jDBCPoolConf = jDBCPoolConf;
	}

	public static JDBCConnectionTracker getjDBCTracker() {
		return jDBCTracker;
	}

	public void setjDBCTracker(JDBCConnectionTracker jDBCTracker) {
		JDBCConnectionStatusCollector.jDBCTracker = jDBCTracker;
	}

	public static SimpleAsyncTaskExecutor getSpringExcuter() {
		return springExcuter;
	}

	public void setSpringExcuter(SimpleAsyncTaskExecutor springExcuter) {
		JDBCConnectionStatusCollector.springExcuter = springExcuter;
	}

	public static String getEnvType() {
		return envType;
	}

	public void setEnvType(String envType) {
		JDBCConnectionStatusCollector.envType = envType;
	}

	public static SendEmailService getjDBCEmailSender() {
		return jDBCEmailSender;
	}

	public void setjDBCEmailSender(SendEmailService jDBCEmailSender) {
		JDBCConnectionStatusCollector.jDBCEmailSender = jDBCEmailSender;
	}

	public static SendMsgService getjDBCSmsSender() {
		return jDBCSmsSender;
	}

	public void setjDBCSmsSender(SendMsgService jDBCSmsSender) {
		JDBCConnectionStatusCollector.jDBCSmsSender = jDBCSmsSender;
	}
	
	public static void sendNotification(){
		
		if (HOPCONS.TO_BE_DEFINED.equals(jDBCTracker.getDetailReason())){
			return;
		}
		
		email.setBodyContent(envType+" JDBC ISSUE:"+ HOPCONS.LOGGER_RETURN+
				jDBCTracker.getDetailReason(),false);
		
		List<SMS> smsLs=smsMessage.getSmsList();
		SimpleDateFormat threadSafeDateFormat=(SimpleDateFormat)HOPCONS.SIMPLE_DATE_FORMAT.clone();
		for (SMS sms : smsLs) {
			sms.setMsgCode(threadSafeDateFormat.format(new Date(System.currentTimeMillis())));
			sms.setMsgContent(envType+" JDBC ISSUE:"+ HOPCONS.LOGGER_RETURN+
				jDBCTracker.getRootReason());
		}
		
		try {
			jDBCSmsSender.sendMsg(smsMessage);
			jDBCEmailSender.sendEmail(email);
		} catch (Exception e) {
			LOGGER.error("JDBC Monitor: Notification can't be sent out", e);
		}
		
	}

	class JdbcCollector extends Thread {
		
		private int runnInterval=5;
		
		public void run() {
			
			while (!finished) {
				try {
					if (runnInterval==5){
						jDBCTracker.queryJDBCPoolLists();
						runnInterval=1;
					}
					jDBCTracker.checkJDBCPoolStatus();
					sendNotification();
				} catch (Exception e) {
					LOGGER.error("JDBC Monitor: Fatal error happening", e);
				} finally {
					try {
						Thread.sleep(readInterval);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						LOGGER.error("JDBC Monitor: Thread is halted", e);
					}
					runnInterval++;
				}
			}
		}
	}

}
