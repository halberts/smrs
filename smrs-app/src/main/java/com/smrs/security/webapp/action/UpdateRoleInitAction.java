package com.smrs.security.webapp.action;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.smrs.security.model.ResourceModel;
import com.smrs.security.model.RoleModel;

/**
 * 更新角色信息初始化
 * @author WangXuzheng
 *
 */
public class UpdateRoleInitAction extends BaseSecurityAction{
	private static final long serialVersionUID = -4764880714915143344L;
	private RoleModel role;
	private long roleId;
	public RoleModel getRole() {
		return role;
	}
	public void setRole(RoleModel role) {
		this.role = role;
	}
	
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	
	public String execute() throws Exception {
		role = roleService.getRoleById(roleId);
		List<ResourceModel> resources = resourceService.getResourceByRole(new Long[]{roleId});
		Set<ResourceModel> resourceSet = new HashSet<ResourceModel>(resources);
		role.setResources(resourceSet);
		return SUCCESS;
	}
}
