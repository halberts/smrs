package com.smrs.security.webapp.action;

import com.smrs.security.model.UserModel;


/**
 * 重置密码初始化
 * @author WangXuzheng
 *
 */
public class ResetPasswordInitAction extends BaseSecurityAction {
	private static final long serialVersionUID = -6760296396266978502L;
	private Long userId = 0L;
	private UserModel user;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}

	@Override
	public String execute() throws Exception {
		this.user = userService.getUserById(userId);
		return SUCCESS;
	}
}
