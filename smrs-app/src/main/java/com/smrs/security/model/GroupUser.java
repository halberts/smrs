package com.smrs.security.model;


/**
 *  组中用户信息
 * @author WangJian
 *
 */
public class GroupUser extends UserModel {
	private static final long serialVersionUID = -112280423769600328L;
	private int isAdmin;

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}
}
