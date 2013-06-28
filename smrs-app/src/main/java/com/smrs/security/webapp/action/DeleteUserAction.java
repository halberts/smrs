package com.smrs.security.webapp.action;

import com.jof.framework.util.ExecuteResult;

/**
 * 删除用户信息
 * @author WangXuzheng
 *
 */
public class DeleteUserAction extends SearchUserAction {
	private static final long serialVersionUID = -1579674572610394248L;
	private long userId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String execute() throws Exception {
		ExecuteResult<?> result = userService.deleteUser(userId);
		if(!result.isSuccess()){
			this.addActionErrorsFromResult(result);
		}
		super.execute();
		return SUCCESS;
	}
	
}
