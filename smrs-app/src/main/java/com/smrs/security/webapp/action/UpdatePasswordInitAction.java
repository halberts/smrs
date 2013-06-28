package com.smrs.security.webapp.action;

import org.apache.commons.lang3.StringUtils;

import com.haier.openplatform.security.SessionSecurityConstants;

/**
 * 修改密码操作
 * @author WangXuzheng
 *
 */
public class UpdatePasswordInitAction extends BaseSecurityAction {
	private static final long serialVersionUID = 4304643774798876770L;
	@Override
	public String execute() throws Exception {
		String error = (String)this.getSession().getAttribute(SessionSecurityConstants.KEY_PASSWORD_TIP);
		if(StringUtils.isNotEmpty(error)){
			this.addActionError(error);
		}
		return SUCCESS;
	}
	
}
