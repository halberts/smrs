package com.smrs.security.webapp.action;


import com.jof.framework.util.ExecuteResult;
import com.smrs.security.model.GroupModel;

/**
 * 将用户添加到组
 * @author WangJian
 *
 */
public class AddUserToGroupAction extends BaseSecurityAction{
 
	private static final long serialVersionUID = -3071645600656656842L;
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
		ExecuteResult<GroupModel> result = groupService.addUserToGroup(group.getId(), userId);  
		if(!result.isSuccess()){
			addActionErrorsFromResult(result); 
		}else{
			addActionMessage("组内添加人员成功");
		}
		return INPUT;
	}
}
