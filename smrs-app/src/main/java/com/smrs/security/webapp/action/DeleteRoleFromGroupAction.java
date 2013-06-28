package com.smrs.security.webapp.action;


import com.jof.framework.util.ExecuteResult;
import com.smrs.security.model.GroupModel;
import com.smrs.security.model.RoleModel;

/**
 * 删除组中的角色
 * @author WangJian
 *
 */
public class DeleteRoleFromGroupAction extends BaseSecurityAction{
 
	private static final long serialVersionUID = -7325242971704987505L;
	private String roleId;
	private GroupModel group;


	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public GroupModel getGroup() {
		return group;
	}

	public void setGroup(GroupModel group) {
		this.group = group;
	}

	@Override
	public String execute() throws Exception {
		ExecuteResult<RoleModel> result = groupService.deleteRoleFromGroup(group.getId(), roleId);
		if(!result.isSuccess()){
			addErrorsFromResult(result);
		}else{
			addActionMessage("角色移除成功");
		} 
		return INPUT;
	}

}
