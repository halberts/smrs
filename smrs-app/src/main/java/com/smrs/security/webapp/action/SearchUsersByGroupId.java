package com.smrs.security.webapp.action;


import com.jof.framework.util.Pager;
import com.smrs.security.model.GroupModel;
import com.smrs.security.model.GroupUser;
import com.smrs.security.vo.UserInGroupSearchModel;

/**
 * 根据组ID查询组内人员
 * @author WangJian
 *
 */
public class SearchUsersByGroupId extends BaseSecurityAction{
 
	private static final long serialVersionUID = -7560830500208134994L;
	private GroupModel group;
	private Pager<GroupUser> pagerUser = new Pager<GroupUser>();
	private UserInGroupSearchModel userSearchModel = new UserInGroupSearchModel();

	public GroupModel getGroup() {
		return group;
	} 
	public void setGroup(GroupModel group) {
		this.group = group;
	}
	public Pager<GroupUser> getPagerUser() {
		return pagerUser;
	}
	public void setPagerUser(Pager<GroupUser> pagerUser) {
		this.pagerUser = pagerUser;
	}
	public UserInGroupSearchModel getUserSearchModel() {
		return userSearchModel;
	}
	public void setUserSearchModel(UserInGroupSearchModel userSearchModel) {
		this.userSearchModel = userSearchModel;
	}
	@Override
	public String execute() throws Exception { 
		this.pagerUser = groupService.getUsersByGroupId(userSearchModel,group.getId());
		return INPUT;
	}
}
