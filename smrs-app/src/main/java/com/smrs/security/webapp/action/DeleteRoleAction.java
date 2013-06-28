package com.smrs.security.webapp.action;


import com.jof.framework.util.ExecuteResult;
import com.smrs.security.model.RoleModel;

/**
 * 
 * @author wuzefei
 * @author WangXuzheng
 */
public class DeleteRoleAction extends SearchRoleAction{
	private static final long serialVersionUID = 7673020075105106834L;
	private long roleId = 0L;
	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	@Override
	public String execute() throws Exception {
		ExecuteResult<RoleModel> executeResult = roleService.deleteRole(roleId);
		if(!executeResult.isSuccess()){
			this.addActionErrorsFromResult(executeResult);
		}
		super.execute();
		return SUCCESS;
	}
}
