package com.smrs.security.webapp.action;


import com.jof.framework.util.Pager;
import com.smrs.security.model.RoleModel;
import com.smrs.security.vo.RoleSearchModel;

/**
 * 查询角色信息
 * @author WangXuzheng
 *
 */
public class SearchRoleAction extends BaseSecurityAction {
	private static final long serialVersionUID = -7889308010706653843L;
	private RoleModel role = new RoleModel();
	private Pager<RoleModel> pager = new Pager<RoleModel>();
	public RoleModel getRole() {
		return role;
	}

	public Pager<RoleModel> getPager() {
		return pager;
	}

	public void setPager(Pager<RoleModel> pager) {
		this.pager = pager;
	}

	public void setRole(RoleModel role) {
		this.role = role;
	}

	@Override
	public String execute() throws Exception {
		RoleSearchModel searchModel = new RoleSearchModel();
		searchModel.setPager(pager);
		searchModel.setRole(role);
		this.pager = roleService.searchRoles(searchModel);
		return SUCCESS;
	}
}
