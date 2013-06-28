package com.smrs.security.vo;


import com.jof.framework.util.SearchModel;
import com.smrs.security.model.RoleModel;

/**
 * 角色查询条件封装信息
 * @author WangXuzheng
 *
 */
public class RoleSearchModel extends SearchModel<RoleModel> {
	private static final long serialVersionUID = 2459641022906050183L;
	private RoleModel role = new RoleModel();
	public RoleModel getRole() {
		return role;
	}
	public void setRole(RoleModel role) {
		this.role = role;
	}
}
