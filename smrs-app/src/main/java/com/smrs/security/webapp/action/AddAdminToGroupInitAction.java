package com.smrs.security.webapp.action;


import com.jof.framework.util.Pager;
import com.smrs.security.model.GroupModel;
import com.smrs.security.model.UserModel;
import com.smrs.security.vo.UserSearchModel;

/**
 * 添加管理员到组初始化
 * @author WangJian
 *
 */
public class AddAdminToGroupInitAction extends BaseSecurityAction{
 
	private static final long serialVersionUID = -9130277147452020062L;
	private GroupModel group; 
	private Pager<UserModel> pagerGroupUser = new Pager<UserModel>();
	private UserModel user = new UserModel(); 
	
	
	public Pager<UserModel> getPagerGroupUser() {
		return pagerGroupUser;
	}
	public void setPagerGroupUser(Pager<UserModel> pagerGroupUser) {
		this.pagerGroupUser = pagerGroupUser;
	}
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}
	public GroupModel getGroup() {
		return group;
	} 
	public void setGroup(GroupModel group) {
		this.group = group;
	}


	public String execute() throws Exception { 
		UserSearchModel userSearchModel = new UserSearchModel();
		if(pagerGroupUser.getPageSize()==20){
			pagerGroupUser.setPageSize(10L);
		}  
		userSearchModel.setUser(user);
		userSearchModel.setPager(pagerGroupUser); 
		this.pagerGroupUser = userService.searchUser(userSearchModel);
		return SUCCESS;
	} 
}
