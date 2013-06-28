package com.jof.framework.security.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.haier.openplatform.security.Authentication;
import com.haier.openplatform.security.AbstractAuthenticator;
import com.haier.openplatform.security.DefaultUrlAuthenticator;
import com.haier.openplatform.security.SessionSecurityConstants;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 权限认证过滤器
 * 
 * @author jonathan_bian
 * 
 */
public abstract class AbstractAuthenticationInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = -8395130260933016257L;
	/**
	 * 无权限跳转的页面
	 */
	private String noAuthPage = "noAuth";

	public void setNoAuthPage(String noAuthPage) {
		this.noAuthPage = noAuthPage;
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest httpServletRequest = ServletActionContext.getRequest();
		HttpSession httpSession = httpServletRequest.getSession();
		Authentication authentication = (Authentication) httpSession
				.getAttribute(SessionSecurityConstants.KEY_AUTHENTICATION);
		if (authentication == null) {
			Long userId = (Long)httpSession.getAttribute(SessionSecurityConstants.KEY_USER_ID);
			authentication = getAuthentication(userId);
			httpSession.setAttribute(SessionSecurityConstants.KEY_AUTHENTICATION, authentication);
		}
		String url = httpServletRequest.getServletPath();
		AbstractAuthenticator authenticator = getAuthenticator(authentication);
		if(!authenticator.hasUrlAuth(url)){
			return noAuthPage;
		}
		return invocation.invoke();
	}

	/**
	 * 获取用户授权
	 * 
	 * @param userId
	 * @return
	 */
	public abstract Authentication getAuthentication(Long userId);
	
	/**
	 * 获取认证校验器
	 * @param authentication
	 * @return
	 */
	protected AbstractAuthenticator getAuthenticator(Authentication authentication){
		return new DefaultUrlAuthenticator(authentication);
	}
}
