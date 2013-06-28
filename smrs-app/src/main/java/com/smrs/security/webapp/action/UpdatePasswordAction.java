package com.smrs.security.webapp.action;


import com.jof.framework.security.LoginContextHolder;
import com.jof.framework.security.SessionSecurityConstants;
import com.jof.framework.util.PasswordUtil;
import com.smrs.security.model.UserModel;

/**
 * 修改密码操作
 * @author WangXuzheng
 *
 */
public class UpdatePasswordAction extends BaseSecurityAction {
	private static final long serialVersionUID = -7723968029600820710L;
	/**
	 * 旧密码
	 */
	private String password;
	/**
	 * 新密码
	 */
	private String newPassword;
	/**
	 * 确认新密码
	 */
	private String confirmPassword;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	@Override
	public String execute() throws Exception {
		Long userId = userId();
		UserModel user = userService.getUserById(userId);
		if(user == null || !user.getPassword().equals(PasswordUtil.encrypt(password))){
			this.addActionError(getText("error.password.old"));
			return INPUT;
		}
		if(user.getPassword().equals(PasswordUtil.encrypt(newPassword))){
			this.addActionError(getText("error.password.samePassword"));
			return INPUT;
		}
		this.userService.updatePassword(userId, confirmPassword);
		getSession().removeAttribute(SessionSecurityConstants.KEY_PASSWORD_TIP);//删除密码修改标记符
		return SUCCESS;
	}
	
	protected Long userId(){
		Long userId = LoginContextHolder.get().getUserId();
		return userId;
	}
	@Override
	public void validate() {
		super.validate();
		if(newPassword==null)
			return;
		if(!newPassword.equals(confirmPassword)){
			this.addActionError(getText("error.password.confirmPassword"));
		}
		/*else if(!PasswordUtil.isValidPassword(newPassword)){
			this.addActionError("帐号密码至少8位，须符合大小写字母、数字、特殊字符四选三的要求.");
		}
		*/
	}
	
}
