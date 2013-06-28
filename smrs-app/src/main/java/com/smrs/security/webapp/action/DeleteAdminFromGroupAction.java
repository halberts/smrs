package com.smrs.security.webapp.action;


import com.jof.framework.util.ExecuteResult;
import com.smrs.security.model.GroupModel;

/**
 * 删除组内管理员
 * @author WangJian
 *
 */
public class DeleteAdminFromGroupAction extends BaseSecurityAction{
 
	private static final long serialVersionUID = -1764434575732620190L;
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
		ExecuteResult<GroupModel> executeResult =  groupService.deleteAdminFromGroup(group.getId(), admin);
		if(!executeResult.isSuccess()){
			addActionErrorsFromResult(executeResult);
		}else{
			addActionMessage("删除成功");
		}
		 return INPUT;
	}
}
