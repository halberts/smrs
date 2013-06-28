package com.smrs.security.webapp.action;


import com.jof.framework.util.ExecuteResult;
import com.smrs.security.model.UserModel;

/**
 * 用户密码找回
 * @author wangjian
 *
 */
public class RetrievePasswordAction extends BaseSecurityAction{ 
	
	
	private static final long serialVersionUID = 9030881429164648611L; 
	private UserModel user; 
	 
	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}
  
	@Override
	public String execute() throws Exception {   
		ExecuteResult<UserModel> nameequalsemail = userService.getUserEmailByName(user.getName(),user.getEmail());
		if(!nameequalsemail.isSuccess()){
			addActionErrorsFromResult(nameequalsemail); 
		}else{ 
			ExecuteResult<UserModel> updateencode = userService.updateUserEncode(user.getName());
			if(!updateencode.isSuccess()){
				addActionErrorsFromResult(updateencode);
			}else{
				addActionMessage("请查看邮件,根据邮件提示更改密码."); 
			}
		}
		return INPUT;
	}
}
