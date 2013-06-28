package com.smrs.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.env.AbstractEnvironment;

import com.haier.openplatform.SysException;
import com.haier.openplatform.console.audit.AuditInfoCollector;
import com.haier.openplatform.console.util.HOPCONS;
import com.haier.openplatform.session.listener.MaxSessionUtil;
import com.haier.openplatform.util.HOPConstant;
import com.haier.openplatform.util.SystemStartupListener;

/**
 * 加载env.properties中的配置项，将静态资源地址和动态资源地址放到application变量中
 * @author WangXuzheng
 *
 */
public class SysconfigInitListener implements SystemStartupListener {
	/**
	 * CONFIG_FILE_PATH 系统变量配置文件路径
	 */
	private static final String CONFIG_FILE_PATH = "/env.properties";
	private static final Log LOG = LogFactory.getLog(SysconfigInitListener.class);
	@Override
	public void onStartup(ServletContextEvent contextEvent) {
		InputStream inputStream = null;
		Properties properties = new Properties();
		try{
			inputStream = SysconfigInitListener.class.getResourceAsStream(CONFIG_FILE_PATH);
			properties.load(inputStream);
			LOG.info("系统配置项:"+properties);
		}catch (Exception e) {
			LOG.error("读取系统配置文件时发生错误：",e);
			throw new SysException(e);
		}finally{
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					LOG.error("关闭文件输入流失败：",e);
				}
			}
		}
		Env.init(properties);
		// 静态资源地址&动态资源地址
		ServletContext servletContext = contextEvent.getServletContext();
		servletContext.setAttribute("staticURL", Env.getProperty(Env.KEY_STATIC_URL));
		servletContext.setAttribute("dynamicURL", Env.getProperty(Env.KEY_DYNAMIC_URL));
		//设置应用的启动时间
		servletContext.setAttribute("hopInfo", HOPCONS.HOP_RELEASE_VERSION+"@"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		AuditInfoCollector.setAppNM(Env.getProperty(Env.KEY_SERVER_NAME));
		HOPConstant.setAppName(Env.getProperty(Env.KEY_SERVER_NAME));
		//设置一些全局参数
		MaxSessionUtil.setMaxSessionKey(Env.getProperty(Env.KEY_SERVER_NAME)+"_MAX_SESSION_KEYS");
		//使用spring的profile
		System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, Env.getProperty(Env.ENV_TYPE));
	}
}
