package com.haier.openplatform.console.quartz.service;

import java.util.Map;

/**
 * @author WangXuzheng
 *
 */
public interface QuartzListenerService {
	public void execute(Map<String,String> dataMap);
}
