package com.smrs.security.webapp.action;

import org.apache.commons.lang.StringUtils;

import com.jof.framework.util.ExecuteResult;
import com.smrs.security.model.ResourceModel;
import com.smrs.security.model.RoleModel;

/**
 * 
 * @author wuzefei
 *
 */
public class CreateRoleAction extends BaseSecurityAction{
	private static final long serialVersionUID = 4696032643814449685L;
	private RoleModel role = new RoleModel();
	private String resourceIds = "";//资源ID
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
		String[] resId = resourceIds.split(",");
		// 设置资源信息
		for(String roleId : resId){
			if(StringUtils.isEmpty(roleId)){
				continue;
			}
			ResourceModel resource = new ResourceModel();
			resource.setId(Long.parseLong(roleId));
			getRole().getResources().add(resource);
		}
		ExecuteResult<RoleModel> executeResult = roleService.createRole(getRole());
		if(!executeResult.isSuccess()){
			addErrorsFromResult(executeResult);
		}
		return INPUT;
	}
}
