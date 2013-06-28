package com.smrs.security.vo;


import com.jof.framework.util.SearchModel;
import com.smrs.security.model.GroupUser;
/**
 * 组中的用户查询条件封装
 * @author WangJian
 *
 */
public class UserInGroupSearchModel extends SearchModel<GroupUser> {
 
	private static final long serialVersionUID = 1370323653648491015L;
	private GroupUser groupUser = new GroupUser();
	
	public GroupUser getGroupUser() {
		return groupUser;
	}
	public void setGroupUser(GroupUser groupUser) {
		this.groupUser = groupUser;
	} 
}
