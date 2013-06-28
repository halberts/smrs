package com.jof.framework.webapp.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haier.openplatform.util.HOPConstant;
import com.haier.openplatform.webapp.action.BaseAppAction;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.PreResultListener;

/**
 * Thread safe class
 *
 * @author mk
 *
 */
public class FillErrInActionResults implements PreResultListener {

	private static final Logger LOGGER = LoggerFactory.getLogger( FillErrInActionResults.class );
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.PreResultListener#beforeResult(com.opensymphony.xwork2.ActionInvocation, java.lang.String)
	 */
	@Override
	public void beforeResult(ActionInvocation invocation, String resultCode) {
		try {
			Object hopActions = invocation.getAction();
			//TODOMK: Is it invoked while error happening
			if (hopActions instanceof BaseAppAction) {
				((BaseAppAction) hopActions).addActionError(HOPConstant.getLocalInfo(HOPConstant.ACTION_ERROR));
			}
		} catch (Exception e) {
			LOGGER.error("Can not set error processing logic in Struts",e);
		}
	}

}
