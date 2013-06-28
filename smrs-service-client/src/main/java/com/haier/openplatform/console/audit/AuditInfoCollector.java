package com.haier.openplatform.console.audit;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.haier.openplatform.console.util.HOPCONS;

public class AuditInfoCollector {

	private static String servernm = null;
	private static String serverip = null;
	private static boolean called = false;
	private static String appNM = null;
	private static String serverSign=null;

	/**
	 * it will be excuted only at first time
	 * @throws Exception 
	 */
	public static void initServerName() {
		if (null == servernm && !called) {
			try {
				// is supposing os will throw exception other than return null
				servernm = InetAddress.getLocalHost().getHostName();
				serverip = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				StringBuffer sb = new StringBuffer("错误如下：");
				sb.append("\n").append("没有初始化服务器名称!");
				sb.append("\n").append("在com.haier.openplatform.showcase.util.SysconfigInitListener类中下列代码");
				sb.append("\n").append("AuditInfoCollector.setAppNM(Env.getProperty(Env.KEY_SERVER_NAME));");
				sb.append("\n").append("并且在EVN和Filter中设置Serrvice.name配置项");
				sb.append("\n").append("最终确保AuditInfoCollector.getAppNM()能够获得系统名称。");
				throw new RuntimeException(sb.toString());
			}
		}
	}

	public static String getServernm() {
		initServerName();
		return servernm;
	}

	public static String getServerip() {
		initServerName();
		return serverip;
	}

	public static String getAppNM() {
		return appNM;
	}

	/**
	 * zip "com.haier.oms.***.servicename" to "oms.***.servicename" 
	 * @param target
	 * @return
	 */
	public static String getClassNm(Object target, String[] in) {
		if (target == null || in == null){
			return HOPCONS.STR_NOT_DEFINED;
		}

		String cnm = target.getClass().getName();
		int leng = in.length;

		if (cnm != null && cnm.contains(".")) {
			for (int i = 0; i < leng; i++) {
				if (cnm.contains(in[i])) {
					return cnm.substring(cnm.indexOf(in[i]) + in[i].length());
				}
			}
		}
		return HOPCONS.STR_NOT_DEFINED;
	}

	public static void setAppNM(String appNM) {
		AuditInfoCollector.appNM = appNM == null ? "NOT_DEFINED" : appNM.toUpperCase();
	}
	
	public static String printServerSign() {
		if (serverSign == null || appNM==null){
			serverSign = HOPCONS.QUEUE_LOGGER_SEG + " APP:" + appNM
					+ " ServerName:" + getServernm() + " ServerIP:" + getServerip()
					+ HOPCONS.QUEUE_LOGGER_SEG + HOPCONS.LOGGER_RETURN;
		}
		return serverSign;
	}

}
