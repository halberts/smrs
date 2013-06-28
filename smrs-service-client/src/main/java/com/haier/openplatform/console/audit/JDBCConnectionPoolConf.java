package com.haier.openplatform.console.audit;

import com.haier.openplatform.console.util.HOPCONS;

/**
 * Please refer <a href="http://docs.oracle.com/cd/E21764_01/apirefs.1111/e13951/mbeans/JDBCDataSourceRuntimeMBean.html?skipReload=true#ConnectionDelayTime">OracleJDBC Reference</a>
 * 
 * @author mk
 *
 */
public class JDBCConnectionPoolConf {

	static final String[] jdbcAttributes = 
		{"ActiveConnectionsCurrentCount","ActiveConnectionsHighCount","ConnectionDelayTime",
		"CurrCapacity","DeploymentState","DriverName","DriverVersion","FailuresToReconnectCount",
		"LeakedConnectionCount","State","WaitingForConnectionCurrentCount",
		"WaitingForConnectionFailureTotal","WaitSecondsHighCount"};
	
	static final String failureRC="FailuresToReconnectCount";
	static final String leakedCC="LeakedConnectionCount";
	static final String waitingCC="WaitingForConnectionCurrentCount";
	
	/**
	 * can be overrided by configure in spring configure file
	 */
	private static int FailuresToReconnectCount = 10;
	
	/**
	 * can be overrided by configure in spring configure file
	 */
	private static int LeakedConnectionCount = 5;
	
	/**
	 * can be overrided by configure in spring configure file
	 */
	private static int WaitingForConnectionCurrentCount = 20;
	
	private static boolean EnableJDBCConnectionTracker = false;
	
	private static String NotificationEmailAddress=HOPCONS.NOT_DEFINED_MAP;
	
	private static String NotificationPhoneNum=HOPCONS.NOT_DEFINED_MAP;

	public static int getFailuresToReconnectCount() {
		return FailuresToReconnectCount;
	}

	public void setFailuresToReconnectCount(int failuresToReconnectCount) {
		FailuresToReconnectCount = failuresToReconnectCount;
	}

	public static int getLeakedConnectionCount() {
		return LeakedConnectionCount;
	}

	public void setLeakedConnectionCount(int leakedConnectionCount) {
		LeakedConnectionCount = leakedConnectionCount;
	}

	public static int getWaitingForConnectionCurrentCount() {
		return WaitingForConnectionCurrentCount;
	}

	public void setWaitingForConnectionCurrentCount(
			int waitingForConnectionCurrentCount) {
		WaitingForConnectionCurrentCount = waitingForConnectionCurrentCount;
	}

	public static String getNotificationEmailAddress() {
		return NotificationEmailAddress;
	}

	public void setNotificationEmailAddress(String notificationEmailAddress) {
		NotificationEmailAddress = notificationEmailAddress;
	}

	public static String getNotificationPhoneNum() {
		return NotificationPhoneNum;
	}

	public void setNotificationPhoneNum(String notificationPhoneNum) {
		NotificationPhoneNum = notificationPhoneNum;
	}

	public static boolean isEnableJDBCConnectionTracker() {
		return EnableJDBCConnectionTracker;
	}

	public void setEnableJDBCConnectionTracker(
			String enableJDBCConnectionTracker) {
		if ("TRUE".equalsIgnoreCase(enableJDBCConnectionTracker)||"1".equalsIgnoreCase(enableJDBCConnectionTracker)){
			EnableJDBCConnectionTracker = true;
		}
		if ("FALSE".equalsIgnoreCase(enableJDBCConnectionTracker)||"0".equalsIgnoreCase(enableJDBCConnectionTracker)){
			EnableJDBCConnectionTracker = false;
		}
	}

}
