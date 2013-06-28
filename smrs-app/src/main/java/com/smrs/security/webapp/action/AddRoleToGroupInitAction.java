package com.smrs.security.webapp.action;


import com.jof.framework.util.Pager;
import com.smrs.security.model.GroupModel;
import com.smrs.security.model.RoleModel;
import com.smrs.security.vo.RoleSearchModel;

/**
 *  角色添加到组初始化
 * @author WangJian
 *
 */
public class AddRoleToGroupInitAction extends BaseSecurityAction{
 
	private static final long serialVersionUID = 7444589692313876941L; 
	private Pager<RoleModel> pagerRole = new Pager<RoleModel>();
	private RoleModel role = new RoleModel();
	private GroupModel group;
	 
	public Pager<RoleModel> getPagerRole() {
		return pagerRole;
	}

	public void setPagerRole(Pager<RoleModel> pagerRole) {
		this.pagerRole = pagerRole;
	}

	public RoleModel getRole() {
		return role;
	}

	public void setRole(RoleModel role) {
		this.role = role;
	}

	public GroupModel getGroup() {
		return group;
	}

	public void setGroup(GroupModel group) {
		this.group = group;
	}
 
	@Override
	public String execute() throws Exception {
		if(pagerRole.getPageSize()==20){
			pagerRole.setPageSize(10L);
		}   
		RoleSearchModel roleSearchModel = new RoleSearchModel();
		roleSearchModel.setRole(role);
		roleSearchModel.setPager(pagerRole);
		this.pagerRole = roleService.searchRoles(roleSearchModel);
		return SUCCESS;
	}

}
