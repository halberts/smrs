package com.jof.framework.util;

import java.util.HashMap;
import java.util.Map;

public class HOPConstant {

	public static final String ACTION_ERROR = "ActionError";
	private static String appName;
	private static final ThreadLocal<Map<String, String>> localInfo = new InheritableThreadLocal<Map<String, String>>() {
		@Override
		protected Map<String, String> initialValue() {
			return new HashMap<String, String>();
		}
	};
	
	public static String getAppName() {
		return appName;
	}

	public static void setAppName(String appName) {
		HOPConstant.appName = appName;
	}
	
	public static void putLocalInfo(String key,String value){
		localInfo.get().put(key, value);
	}
	
	public static String getLocalInfo(String key){
		return localInfo.get().get(key);
	}
}
