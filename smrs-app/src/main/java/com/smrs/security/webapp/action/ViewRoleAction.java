package com.smrs.security.webapp.action;

import java.util.HashSet;
import java.util.List;

import com.smrs.security.model.ResourceModel;
import com.smrs.security.model.RoleModel;

/**
 * 查看角色信息
 * @author WangXuzheng
 *
 */
public class ViewRoleAction extends BaseSecurityAction {
	private static final long serialVersionUID = -4349401215519616855L;
	/**
	 * 要查看的角色id
	 */
	private Long roleId;
	private RoleModel role = new RoleModel();
	
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public RoleModel getRole() {
		return role;
	}

	public void setRole(RoleModel role) {
		this.role = role;
	}

	@Override
	public String execute() throws Exception {
		role = roleService.getRoleById(roleId);
		if(role == null){
			return "list";//角色列表页面
		}
		List<ResourceModel> resources = resourceService.getResourceByRole(new Long[]{roleId});
		role.setResources(new HashSet<ResourceModel>(resources));
		return SUCCESS;
	}
}
