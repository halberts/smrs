package com.smrs.security.webapp.action;

import org.apache.commons.lang.StringUtils;

import com.jof.framework.security.LoginContext;
import com.jof.framework.security.SessionSecurityConstants;


/**
 * 退出系统登录
 * @author WangXuzheng
 *
 */
public class LogoutAction extends BaseSecurityAction {
	private static final long serialVersionUID = 7521641081600825642L;

	@Override
	public String execute() throws Exception {
		LoginContext loginContext = new LoginContext();
		loginContext.setIp(getRequest().getRemoteAddr());
		loginContext.setUserName((String)getSession().getAttribute(SessionSecurityConstants.KEY_USER_NAME));
		loginContext.setUserId((Long)getSession().getAttribute(SessionSecurityConstants.KEY_USER_ID));
		if(StringUtils.isNotEmpty(loginContext.getUserName())){
			userService.logout(loginContext);
		}
		getSession().invalidate();
		return SUCCESS;
	}
}
