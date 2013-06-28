package com.smrs.security.webapp.action;


import java.util.ArrayList;
import java.util.List;

import com.jof.framework.util.Pager;
import com.smrs.security.model.GroupModel;
import com.smrs.security.model.ResourceModel;
import com.smrs.security.model.UserModel;

 

/**
 * 创建组初始化
 * @author WangJian
 *
 */
public class CreateGroupInitAction extends BaseSecurityAction{ 
	
	private static final long serialVersionUID = 125277556991705156L;   
	private Pager<UserModel> pagerUser = new Pager<UserModel>();
	

	protected UserModel user = new UserModel(); 
	
	protected GroupModel group = new GroupModel();
	
	public GroupModel getGroup() {
		return group;
	}

	public void setGroup(GroupModel group) {
		this.group = group;
	}

	
	public Pager<UserModel> getPagerUser() {
		return pagerUser;
	}

	public void setPagerUser(Pager<UserModel> pagerUser) {
		this.pagerUser = pagerUser;
	} 
	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}
	@Override
	public String execute() throws Exception {   
		/*
		if(pagerUser.getPageSize()==20){
			pagerUser.setPageSize(10L);
		}    
		UserSearchModel userSearchModel = new UserSearchModel();
		userSearchModel.setUser(user); 
		userSearchModel.setPager(pagerUser); 
		this.pagerUser = userService.searchUser(userSearchModel);
		*/
		this.allResourceList = resourceService.getAll();
		return SUCCESS;
	}
}
