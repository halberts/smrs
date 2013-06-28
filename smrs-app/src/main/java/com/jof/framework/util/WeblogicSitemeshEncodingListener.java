package com.jof.framework.util;

import javax.servlet.ServletContextEvent;

import com.haier.openplatform.util.SystemStartupListener;

/**
 * 设置系统属性，解决weblogic下sitemesh乱码问题-设置系统编码为utf-8
 * @author WangXuzheng
 *
 */
public class WeblogicSitemeshEncodingListener implements SystemStartupListener{

	@Override
	public void onStartup(ServletContextEvent contextEvent) {
		System.getProperties().put("file.encoding", "utf-8");
	}
}
