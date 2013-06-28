package com.smrs.security.webapp.action;


import com.jof.framework.security.LoginContextHolder;
import com.smrs.security.model.UserModel;

/**
 * @author WangXuzheng
 *
 */
public class ViewUserAction extends BaseSecurityAction{
	private static final long serialVersionUID = -800474916204314741L;
	private UserModel user = new UserModel();
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}
	@Override
	public String execute() throws Exception {
		if(user.getId() == null || user.getId() <=0 ){
			user.setId(LoginContextHolder.get().getUserId());
		}
		UserModel userBean = userService.getUserById(user.getId());
		setUser(userBean);
		return SUCCESS;
	}
}
