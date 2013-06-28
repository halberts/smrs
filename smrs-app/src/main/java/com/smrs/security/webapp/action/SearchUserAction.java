package com.smrs.security.webapp.action;


import java.util.List;

import com.jof.framework.util.Pager;
import com.smrs.security.model.UserModel;
import com.smrs.security.vo.UserSearchModel;

/**
 * 查询用户信息
 * @author WangXuzheng
 *
 */
public class SearchUserAction extends BaseSecurityAction {
	private static final long serialVersionUID = -8198577508211846862L;
	private Pager<UserModel> pager = new Pager<UserModel>();
	private UserModel user = new UserModel();
	
	public Pager<UserModel> getPager() {
		return pager;
	}

	public void setPager(Pager<UserModel> pager) {
		this.pager = pager;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	@Override
	public String execute() throws Exception {
		UserSearchModel userSearchModel = new UserSearchModel();
		userSearchModel.setUser(user);
		userSearchModel.setPager(pager);
		this.pager = userService.searchUser(userSearchModel);
		if(pager.getRecords()!=null){
			List<UserModel> list= pager.getRecords();
			for(UserModel one:list){
				one.setGroups(null);
			}
		}
		
		return SUCCESS;
	}
}
