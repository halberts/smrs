package com.smrs.security.webapp.action;


import com.jof.framework.util.Pager;
import com.smrs.security.model.ResourceModel;
import com.smrs.security.model.RoleModel;

/**
 * 创建角色初始化action
 * @author WangXuzheng
 *
 */
public class CreateRoleInitAction extends BaseSecurityAction {
	private static final long serialVersionUID = -6991777968099326201L;
	private RoleModel role = new RoleModel();
	private Pager<ResourceModel> pager = new Pager<ResourceModel>();
	private ResourceModel resource = new ResourceModel();
	public RoleModel getRole() {
		return role;
	}

	public void setRole(RoleModel role) {
		this.role = role;
	}

	public Pager<ResourceModel> getPager() {
		return pager;
	}

	public void setPager(Pager<ResourceModel> pager) {
		this.pager = pager;
	}

	@Override
	public String execute() throws Exception {
		this.pager=resourceService.searchResources(pager,resource);
		return SUCCESS;
	}
}
