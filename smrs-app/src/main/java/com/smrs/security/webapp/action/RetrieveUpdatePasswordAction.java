package com.smrs.security.webapp.action;


import com.jof.framework.util.ExecuteResult;
import com.smrs.security.model.UserModel;

/**
 * 用户找回密码后,点击接收的URL对密码进行更新
 * @author wangjian
 */
public class RetrieveUpdatePasswordAction extends BaseSecurityAction{
	
	
	private static final long serialVersionUID = -2586173644336236187L; 
	private String encode;
	private String name;
	private String password;
	private String confirmpassword;
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String execute() throws Exception {    
	ExecuteResult<UserModel> result = userService.confirmUpdatePassword(name,encode,password,confirmpassword); 
		if(!result.isSuccess()){
			addActionErrorsFromResult(result);  
		}else{
			addActionMessage("修改密码成功！"); 
		}  
		return INPUT; 
	}
}
