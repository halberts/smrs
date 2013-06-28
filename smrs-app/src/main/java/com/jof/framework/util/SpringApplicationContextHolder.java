package com.jof.framework.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * spring的applicationContext上下文.
 * 该类必须在spirng中进行配置后才可以使用
 * @author WangXuzheng
 * @see ApplicationContext
 * @see SpringApplicationContextHolder#getApplicationContext()
 *
 */
public class SpringApplicationContextHolder implements ApplicationContextAware {
	private static ApplicationContext applicationContext;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringApplicationContextHolder.applicationContext = applicationContext;
	}

	/**
	 * 获取applicationContext对象
	 * @return
	 */
	public static ApplicationContext getApplicationContext(){
		return applicationContext;
	}
	
	/**
	 * 根据beanid获取spring中的bean对象
	 * @param beanId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanId){
		return (T)applicationContext.getBean(beanId);
	}
}
