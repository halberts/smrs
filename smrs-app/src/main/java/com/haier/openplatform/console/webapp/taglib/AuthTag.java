package com.haier.openplatform.console.webapp.taglib;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;

import org.apache.struts2.views.jsp.StrutsBodyTagSupport;

import com.haier.openplatform.security.Authentication;
import com.haier.openplatform.security.SessionSecurityConstants;

/**
 * 权限标签
 * @author WangXuzheng
 *
 */
public class AuthTag extends StrutsBodyTagSupport{
	private static final long serialVersionUID = -7946116604888661949L;
	private Set<String> authCodeSet = new HashSet<String>();
	public void setCode(String code) {
		String[] codes = code.split(",");
		authCodeSet.addAll(Arrays.asList(codes));
	}

	public int doStartTag() throws JspException {
		//SKIP_BODY EVAL_BODY_BUFFERED EVAL_BODY_INCLUDE
		HttpSession httpSession = pageContext.getSession();
		Authentication authentication = (Authentication)httpSession.getAttribute(SessionSecurityConstants.KEY_AUTHENTICATION);
		if(authentication == null){
			return SKIP_BODY;
		}
		if(hasAuth(authentication)){
			return EVAL_BODY_INCLUDE;
		}
		return SKIP_BODY;
	}
	
	private boolean hasAuth(Authentication authentication){
		for(String auth: authCodeSet){
			if(authentication.getComponentResources().contains(auth)){
				return true;
			}
		}
		return false;
	}
}
