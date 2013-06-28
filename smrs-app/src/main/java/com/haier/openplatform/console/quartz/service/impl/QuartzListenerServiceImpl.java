package com.haier.openplatform.console.quartz.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.haier.openplatform.console.quartz.service.QuartzCommand;
import com.haier.openplatform.console.quartz.service.QuartzListenerService;

/**
 * @author WangXuzheng
 *
 */
public class QuartzListenerServiceImpl implements QuartzListenerService,InitializingBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(QuartzListenerServiceImpl.class);
	private Map<String, QuartzCommand> quartzCommandMap = new HashMap<String, QuartzCommand>();
	
	public void setQuartzCommandMap(Map<String, QuartzCommand> quartzCommandMap) {
		this.quartzCommandMap = quartzCommandMap;
	}

	@Override
	public void execute(Map<String,String> dataMap) {
		String command = dataMap.get("EVENT_TYPE");
		QuartzCommand quartzCommand = quartzCommandMap.get(command);
		if(quartzCommand == null){
			LOGGER.warn("Can't find event handler for event[{}]",command);
			return;
		}else{
			LOGGER.warn("Got quartz event [{}] with handler [{}]",command,quartzCommand.getClass().getName());
		}
		quartzCommand.execute(dataMap);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				ConcurrentLinkedQueue<Map<String, String>> dataMaps = QuartzRecoveryStore.getRecoveryStore();
				while(!dataMaps.isEmpty()){
					Map<String, String> dataMap = dataMaps.remove();
					execute(dataMap);
				}
			}
		}, 60000);
	}
}
