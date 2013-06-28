package com.smrs.security.webapp.action;


import com.jof.framework.util.Pager;
import com.smrs.security.model.GroupModel;
import com.smrs.security.model.GroupUser;
import com.smrs.security.vo.UserInGroupSearchModel;

/**
 * 搜索组内的人员附带是否是组的管理员
 * @author WangJian
 *
 */
public class SearchAdminInGroupAction extends BaseSecurityAction{
	
	
	private static final long serialVersionUID = -358530489548839264L;   
		private GroupModel group; 
		private Pager<GroupUser> pagerGroupUser = new Pager<GroupUser>();
		private GroupUser groupUser = new GroupUser();
		   
		public Pager<GroupUser> getPagerGroupUser() {
			return pagerGroupUser;
		}

		public void setPagerGroupUser(Pager<GroupUser> pagerGroupUser) {
			this.pagerGroupUser = pagerGroupUser;
		}

		public GroupUser getGroupUser() {
			return groupUser;
		}

		public void setGroupUser(GroupUser groupUser) {
			this.groupUser = groupUser;
		}

		public GroupModel getGroup() {
			return group;
		}


		public void setGroup(GroupModel group) {
			this.group = group;
		}


		public String execute() throws Exception { 
			UserInGroupSearchModel userInGroupSearchModel = new UserInGroupSearchModel();
			pagerGroupUser.setPageSize(10L);
			userInGroupSearchModel.setGroupUser(groupUser);
			userInGroupSearchModel.setPager(pagerGroupUser);
			this.pagerGroupUser = groupService.getAdminByGroupId(userInGroupSearchModel,group.getId());
			return SUCCESS;
		} 

	}

	 