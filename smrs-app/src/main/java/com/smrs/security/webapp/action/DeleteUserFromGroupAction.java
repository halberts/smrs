package com.smrs.security.webapp.action;


import com.jof.framework.util.ExecuteResult;
import com.smrs.security.model.GroupModel;

/**
 * 删除组中的用户
 * @author WangJian
 *
 */
public class DeleteUserFromGroupAction extends BaseSecurityAction{
 
	private static final long serialVersionUID = -6344810040977226559L;
	private String userId;
	private GroupModel group;
	
 

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public GroupModel getGroup() {
		return group;
	}

	public void setGroup(GroupModel group) {
		this.group = group;
	}

	@Override
	public String execute() throws Exception {
		ExecuteResult<GroupModel> result = groupService.deleteUserFromGroup(group.getId(), userId);
		if(!result.isSuccess()){
			addErrorsFromResult(result);
		}else{
			addActionMessage("人员移除成功");
		} 
		return INPUT;
	}
}
