package com.haier.openplatform.console.jmx.service;

import java.util.Map;

/** 
 * @author WangJian
 */
public interface JmxResourcesService { 
	public void setJmxUrl(String jmxUrl);

	/**
	 * 获取所有JMS消息列表
	 * @return 
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Map> getAllResourcesParameters();

	/**
	 * jmx检索资源后保存到数据库
	 * @return 
	 */
	public int saveAllResourcesParameters();

	/**
	 * 获取jmx工厂连接的参数 
	 * @param name 
	 * @return 
	 */
	public Map<String, Object> getConnectionFactoryParameters(String name);

	/**
	 * 通过jmx检索Topic数据 
	 * @param name 
	 * @return 
	 */
	public Map<String, Object> getTopicParameters(String name);

	/**
	 * 通过jmx检索queue数据  
	 * 
	 * @param name 
	 * @return  
	 */
	public Map<String, Object> getQueueParameters(String name);
}
