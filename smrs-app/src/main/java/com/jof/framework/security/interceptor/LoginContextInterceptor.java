package com.jof.framework.security.interceptor;

import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.jof.framework.security.LoginContext;
import com.jof.framework.security.LoginContextHolder;
import com.jof.framework.security.SessionSecurityConstants;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 从session中读取用户信息，设置到线程上线文中
 * @author jonathan_bian
 *
 */
public class LoginContextInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = -2622192412895411710L;
	/**
	 * 用户session中的用户表示
	 */
	private String keyUserName = SessionSecurityConstants.KEY_USER_NAME;
	private String keyUserId = SessionSecurityConstants.KEY_USER_ID;
	private String keyLocalLanguage = SessionSecurityConstants.KEY_LOCAL_LANGUAGE;
	
	public void setKeyUserName(String keyUserName) {
		this.keyUserName = keyUserName;
	}

	public void setKeyUserId(String keyUserId) {
		this.keyUserId = keyUserId;
	}

	public void setKeyLocalLanguage(String keyLocalLanguage) {
		this.keyLocalLanguage = keyLocalLanguage;
	}
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest httpServletRequest = ServletActionContext.getRequest();
		HttpSession httpSession = httpServletRequest.getSession();
		Long userId = (Long)httpSession.getAttribute(keyUserId);
		String userName = (String)httpSession.getAttribute(keyUserName);
		Locale language = (Locale)httpSession.getAttribute(keyLocalLanguage);
		if(userName == null){
			//仅仅记住get请求的链接
			if(StringUtils.equalsIgnoreCase(httpServletRequest.getMethod(),"GET")){
				HttpSession session = httpServletRequest.getSession();
				String servletPath = httpServletRequest.getServletPath();
				String fullURL = new StringBuffer(servletPath).append(toParameterString(httpServletRequest)).toString();
				session.setAttribute(SessionSecurityConstants.KEY_LAST_VISIT_URL, fullURL);
			}
			return Action.LOGIN;
		}
		// 将当前登陆者信息保存到线程上线问中
		LoginContext loginContext = new LoginContext();
		loginContext.setUserId(userId);
		loginContext.setUserName(userName);
		loginContext.setLanguage(language == null ? "" : language.toString());
		loginContext.setIp(ServletActionContext.getRequest().getRemoteAddr());
		LoginContextHolder.put(loginContext);
		String result = invocation.invoke();
		LoginContextHolder.clear();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private String toParameterString(HttpServletRequest httpServletRequest){
		Enumeration<String> paramEnumeration = httpServletRequest.getParameterNames();
		if(!paramEnumeration.hasMoreElements()){
			return "";
		}
		StringBuffer stringBuffer = new StringBuffer();
		while(paramEnumeration.hasMoreElements()){
			String paramName = paramEnumeration.nextElement();
			stringBuffer.append("&");
			stringBuffer.append(paramName);
			stringBuffer.append("=");
			stringBuffer.append(httpServletRequest.getParameter(paramName));
		}
		stringBuffer.replace(0, 1, "?");
		return stringBuffer.toString();
	}

}
