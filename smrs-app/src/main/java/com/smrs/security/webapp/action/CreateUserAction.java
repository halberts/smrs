package com.smrs.security.webapp.action;

import org.apache.commons.lang.StringUtils;

import com.jof.framework.util.ExecuteResult;
import com.smrs.basicdata.model.StoreModel;
import com.smrs.security.model.GroupModel;
import com.smrs.security.model.RoleModel;
import com.smrs.security.model.UserModel;

/**
 * 创建用户
 * @author lupeng
 *
 */
public class CreateUserAction extends CreateUserInitAction {
	protected String titleName="门店";

	
	
	private static final long serialVersionUID = 3692522833173250166L;
	private String confirmPassword;
	


	private String roleIds;
	private String departmentIds;
	private String storeIds;
	
	public String getStoreIds() {
		return storeIds;
	}

	public void setStoreIds(String storeIds) {
		this.storeIds = storeIds;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getDepartmentIds() {
		return departmentIds;
	}

	public void setDepartmentIds(String departmentIds) {
		this.departmentIds = departmentIds;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}


	@Override
	public String execute() throws Exception {
		// 设置角色信息
		//configRoleInfo();
		// 设置部门信息
		//configDepartmentInfo();
		// 保存
		//设置门店信息
		configStoreInfo();
		configGroupInfo();
		UserModel tempUser=getUser();
		ExecuteResult<UserModel> result = userService.createUser(tempUser);
		if(!result.isSuccess()){
			addErrorsFromResult(result);
			return INPUT;
		} 
		return INPUT;
	}
	

	


	@Override
	public void validate() {
		if(!StringUtils.equals(confirmPassword, getUser().getPassword())){
			addFieldError("confirmPassword", "确认密码和密码不一致。");
		}
		/*
		else if(!PasswordUtil.isValidPassword(getUser().getPassword())){
			this.addActionError("帐号密码至少8位，须符合大小写字母、数字、特殊字符四选三的要求.");
		}
		*/
	}
	
	public String getTitleName() {
		return titleName;
	}
}
