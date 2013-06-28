package com.jof.framework.session.listener;

/**
 * session工具類
 * @author WangXuzheng
 *
 */
public final class MaxSessionUtil {
	private static final String SEPARATOR = "_$@#_";//分隔符
	private static String maxSessionKey = "MAX_SESSION_KEY";
	private MaxSessionUtil(){
	}
	/**
	 * 生成存儲session個數的sessionKey字符串
	 * @param userName
	 * @param appKey
	 * @return
	 */
	public static String generateMaxSessionKey(String userName){
		return new StringBuffer(userName).append(SEPARATOR).append(maxSessionKey).toString();
	}
	
	public static void setMaxSessionKey(String sessionKey){
		maxSessionKey = sessionKey;
	}
}
