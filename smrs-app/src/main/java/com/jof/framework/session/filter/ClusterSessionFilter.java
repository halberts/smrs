package com.jof.framework.session.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.haier.openplatform.session.ClusterHttpServletRequestWrapper;
import com.haier.openplatform.session.ClusterHttpSessionWrapper;
import com.haier.openplatform.session.service.SessionService;
import com.haier.openplatform.util.IpUtil;

/**
 * 基于Memcached实现的session共享
 * 
 * @author WangXuzheng
 * 
 */
public class ClusterSessionFilter implements Filter {
	private static final Log LOG = LogFactory.getLog(ClusterSessionFilter.class);
	/**
	 * 默认的保存session的变量名
	 */
	private static final String DEFAULT_SESSION_KEY = "sid";
	private String sessionKey = DEFAULT_SESSION_KEY;
	private String cookieDomain;
	private String cookiePath;
	/**
	 * 忽略不被解析的url
	 */
	private Pattern requestUriIgnorePattern;
	/**
	 * sessionService对象
	 */
	private SessionService sessionService;

	@SuppressWarnings("unchecked")
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Map<String, String> initMap = new HashMap<String, String>();
		for (Enumeration<String> enu = filterConfig.getInitParameterNames(); enu.hasMoreElements();) {
			String key = enu.nextElement();
			initMap.put(key, filterConfig.getInitParameter(key));
		}
		String contextPath = filterConfig.getServletContext().getContextPath();
		String path = null;
		if(contextPath.startsWith("/")){
			path = contextPath.substring(1);
		}else{
			path = DEFAULT_SESSION_KEY;
		}
		this.sessionKey = StringUtils.defaultIfEmpty(initMap.get("sessionKey"), path+"_sessionId");
		this.cookieDomain = StringUtils.defaultIfEmpty(initMap.get("cookieDomain"), "");
		this.cookiePath = StringUtils.defaultIfEmpty(initMap.get("cookiePath"), "/");
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
		String sessionServiceId = StringUtils.defaultIfEmpty(initMap.get("sessionServiceId"), "sessionService");
		this.sessionService = applicationContext.getBean(sessionServiceId, SessionService.class);
		String ignorUrlPattern = StringUtils.defaultIfEmpty(initMap.get("requestUriIgnorePattern"), ".*");
		this.requestUriIgnorePattern = Pattern.compile(ignorUrlPattern, Pattern.CASE_INSENSITIVE);
		LOG.info("SessionFilter初始完成" + initMap);
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		//提出特殊连接的过滤
		String requestUrl = request.getRequestURI();
		if (this.requestUriIgnorePattern.matcher(requestUrl).matches()) {
			chain.doFilter(servletRequest, servletResponse);
			return;
		}

		Cookie cookies[] = request.getCookies();
		String sid = "";
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals(sessionKey)) {
					sid = cookie.getValue();
					break;
				}
			}
		}
		String ip = IpUtil.getIpAddress(request);
		int ipHash = ip.hashCode();
		String sessionId = generateSessionString(ipHash);
		if (StringUtils.isBlank(sid)) {
			resetSessionIdValueInCookie(response, sessionId);
			sid = sessionId;
		}else{//判断是否伪造session
			String[] sessionIdArr = sid.split("!");
			if(sessionIdArr != null && sessionIdArr.length == 2){
				if(!StringUtils.equals(String.valueOf(ipHash), sessionIdArr[1])){
					resetSessionIdValueInCookie(response, sessionId);
					sid = sessionId;
				}
			}
		}
		ClusterHttpServletRequestWrapper clusterHttpServletRequest = new ClusterHttpServletRequestWrapper(request, sid, this.sessionService);
		chain.doFilter(clusterHttpServletRequest, servletResponse);
		//更新session的活动时间
		ClusterHttpSessionWrapper clusterHttpSessionWrapper = (ClusterHttpSessionWrapper) clusterHttpServletRequest.getSession();
		clusterHttpSessionWrapper.saveSession();
	}

	private void resetSessionIdValueInCookie(HttpServletResponse response, String sessionId) {
		Cookie cookie = new Cookie(sessionKey, sessionId);
		cookie.setMaxAge(-1);
		if (StringUtils.isNotBlank(this.cookieDomain)) {
			cookie.setDomain(this.cookieDomain);
		}
		cookie.setPath(this.cookiePath);
		response.addCookie(cookie);
	}

	/**
	 * 生成sessionID，格式xxxx!zzzz
	 * @param ipHash
	 * @return
	 */
	private String generateSessionString(int ipHash){
		return new StringBuilder(UUID.randomUUID().toString()).append("!").append(ipHash).toString();
	}
	@Override
	public void destroy() {
	}

}
