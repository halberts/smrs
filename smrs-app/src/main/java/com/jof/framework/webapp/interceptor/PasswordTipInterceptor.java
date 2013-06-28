package com.jof.framework.webapp.interceptor;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.haier.openplatform.security.SessionSecurityConstants;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 密码过期提醒拦截器
 * @author WangXuzheng
 *
 */
public class PasswordTipInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 3733275271814852803L;
	/**
	 * 密码过期或未修改默认密码时的提示页面
	 */
	private String updatePasswordPage = "updatePassword";
	private Set<String> ignoralUrls = new HashSet<String>(){
		private static final long serialVersionUID = 8618012828622753716L;
		{	//默认被忽略拦截的链接
			this.add(".*updatePassword\\..+");
			this.add(".*updatePasswordInit\\..+");
			this.add(".*leftMenu\\..+");
		}
	};
	public void setUpdatePasswordPage(String updatePasswordPage) {
		this.updatePasswordPage = updatePasswordPage;
	}
	public Set<String> getIgnoralUrls() {
		return ignoralUrls;
	}

	public void setIgnoralUrls(Set<String> ignoralUrls) {
		this.ignoralUrls = ignoralUrls;
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest httpServletRequest = ServletActionContext.getRequest();
		String url = httpServletRequest.getRequestURI();
		if(isIgnoralUrl(url)){//忽略特定的url
			return invocation.invoke();
		}
		HttpSession httpSession = httpServletRequest.getSession();
		String tipResult = (String)httpSession.getAttribute(SessionSecurityConstants.KEY_PASSWORD_TIP);
		if(StringUtils.isNotEmpty(tipResult)){
			return updatePasswordPage;
		}
		return invocation.invoke();
	}
	
	private boolean isIgnoralUrl(String url){
		for(String ignoralUrl : ignoralUrls){
			if(url.matches(ignoralUrl)){
				return true;
			}
		}
		return false;
	}
}
