package com.smrs.security.webapp.action;


import org.apache.commons.lang.StringUtils;

import com.jof.framework.util.ExecuteResult;
import com.smrs.security.model.ResourceModel;
import com.smrs.security.model.RoleModel;

/**
 * 更新角色信息
 * @author WangXuzheng
 *
 */
public class UpdateRoleAction extends BaseSecurityAction {
	private static final long serialVersionUID = 4536346225032014486L;
	private RoleModel role = new RoleModel();
	/**
	 * 角色关联的资源列表
	 */
	private String resourceIds = "";
	public RoleModel getRole() {
		return role;
	}

	public void setRole(RoleModel role) {
		this.role = role;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	@Override
	public String execute() throws Exception {
		//设置角色关联的资源
		configResourceInfoToRole();
		ExecuteResult<RoleModel> executeResult = roleService.updateRole(role);
		if(!executeResult.isSuccess()){
			this.addActionErrorsFromResult(executeResult);
		}
		return INPUT;
	}

	private void configResourceInfoToRole() {
		String[] resIds = this.resourceIds.split(",");
		for(String res : resIds){
			if(StringUtils.isEmpty(res)){
				continue;
			}
			ResourceModel resource = new ResourceModel();
			resource.setId(Long.valueOf(res));
			role.getResources().add(resource);
		}
	}
}
