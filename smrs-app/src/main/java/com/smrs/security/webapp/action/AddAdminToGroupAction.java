package com.smrs.security.webapp.action;


import com.jof.framework.util.ExecuteResult;
import com.smrs.security.model.GroupModel;

/**
 * 添加组的管理员
 * @author WangJian
 *
 */
public class AddAdminToGroupAction extends BaseSecurityAction{
 
	private static final long serialVersionUID = 8591387604590131201L;
	private GroupModel group; 
	private String admin;
	
	public GroupModel getGroup() {
		return group;
	}
	public void setGroup(GroupModel group) {
		this.group = group;
	} 
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	@Override
	public String execute() throws Exception { 
		ExecuteResult<GroupModel> executeResult =  groupService.addAdminToGroup(group.getId(), admin); 
		if(!executeResult.isSuccess()){
			addActionErrorsFromResult(executeResult);
		}else{
			addActionMessage("添加组管理员成功");
		}
		 return INPUT;
	}
}
