package com.haier.openplatform.console.quartz.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QuartzRecoveryStore {
	private static ConcurrentLinkedQueue<Map<String,String>> dataMapStore = new ConcurrentLinkedQueue<Map<String,String>>();
	public static void addToRecoveryStore(Map<String,String> dataMap){
		dataMapStore.offer(dataMap);
	}
	public static void removeFromRecoveryStore(Map<String,String> dataMap){
		dataMapStore.remove(dataMap);
	}
	public static ConcurrentLinkedQueue<Map<String, String>> getRecoveryStore(){
		return dataMapStore;
	}
}
