package com.smrs.security.webapp.action;

import com.haier.openplatform.util.PasswordUtil;
import com.smrs.security.model.UserModel;


/**
 * 重置密码
 * @author WangXuzheng
 *
 */
public class ResetPasswordAction extends BaseSecurityAction {
	private static final long serialVersionUID = -7723968029634820710L;
	private Long userId;
	private String newPassword;
	private String confirmPassword;
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String execute() throws Exception {
		UserModel user = userService.getUserById(userId);
		if(user.getPassword().equals(PasswordUtil.encrypt(newPassword))){
			this.addActionError(getText("error.password.samePassword"));
			return INPUT;
		}
		this.userService.updatePassword(userId, confirmPassword);
		return INPUT;
	}
	@Override
	public void validate() {
		super.validate();
		if(!newPassword.equals(confirmPassword)){
			this.addActionError(getText("error.password.confirmPassword"));
		} 
		/*else if(!PasswordUtil.isValidPassword(newPassword)){
			this.addActionError("帐号密码至少8位，须符合大小写字母、数字、特殊字符四选三的要求.");
		}
		*/
	}
}
