package com.jof.framework.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.jof.framework.security.SessionSecurityConstants;
import com.jof.framework.util.ExecuteResult;
import com.jof.framework.webapp.interceptor.FillErrInActionResults;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 所有action的基类
 * 
 * @author WangXuzheng
 * 
 */
public class BaseAppAction extends ActionSupport {
	private static final long serialVersionUID = -5605735709149626243L;
	/**
	 * Each action is thread safe class.<br/>
	 * Each action should at last be added below listner. <br/>
	 * Listner will be excuted just between action and generation of view.<br/>
	 * <b>Does not support chain actions</b>
	 * 
	 * @author mk
	 */
	{
		if (ActionContext.getContext() != null
				&& ServletActionContext.getActionMapping() != null) {
			// register listener for error handling.
			ServletActionContext.getContext().getActionInvocation()
					.addPreResultListener(new FillErrInActionResults());
		}
	}

	/**
	 * 将业务层返回的errorMessage和field写入到value stack中
	 * 
	 * @param result
	 */
	public void addActionErrorsFromResult(ExecuteResult<?> result) {
		for (String error : result.getErrorMessages()) {
			this.addActionError(error);
		}
	}

	public void addFieldErrorsFromResult(ExecuteResult<?> result) {
		for (String field : result.getFieldErrors().keySet()) {
			this.addFieldError(field, result.getFieldErrors().get(field));
		}
	}

	public void addErrorsFromResult(ExecuteResult<?> result) {
		addActionErrorsFromResult(result);
		addFieldErrorsFromResult(result);
	}

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	protected HttpSession getSession() {
		return getRequest().getSession();
	}

	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	
	public String getUserNameFromSession(){
	   String username=(String)this.getSession().getAttribute(SessionSecurityConstants.KEY_USER_NAME);
	   return username;
	}
	

}
