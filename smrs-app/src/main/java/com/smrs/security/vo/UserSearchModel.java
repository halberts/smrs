package com.smrs.security.vo;


import com.jof.framework.util.SearchModel;
import com.smrs.security.model.UserModel;

/**
 * 用户信息查询参数封装对象
 * @author WangXuzheng
 *
 */
public class UserSearchModel extends SearchModel<UserModel> {
	private static final long serialVersionUID = -9109823988334129284L;
	private UserModel user = new UserModel();
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}
}
