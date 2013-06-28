package com.smrs.security.webapp.action;


import com.jof.framework.util.ExecuteResult;
import com.smrs.security.model.GroupModel;

/**
 * 将角色添加到组中
 * @author WangJian
 *
 */
public class AddRoleToGroupAction extends BaseSecurityAction{ 
	
	private static final long serialVersionUID = 5344374508145820676L;
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
			ExecuteResult<GroupModel> result =  groupService.addRoleToGroup(group.getId(), roleId);  
			if(!result.isSuccess()){
				addErrorsFromResult(result);
			}else{
				addActionMessage("添加成功");
			}
			return INPUT;
	}
}
