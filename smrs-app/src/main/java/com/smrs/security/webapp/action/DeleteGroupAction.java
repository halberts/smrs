package com.smrs.security.webapp.action;

import com.jof.framework.util.ExecuteResult;



/**
 * 删除组
 * @author WangJian
 *
 */
public class DeleteGroupAction extends BaseSecurityAction{
 
	private static final long serialVersionUID = -7248387999687112737L;
	private String groupIds;
 

	public String getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}


	@Override
	public String execute() throws Exception {  
		ExecuteResult<?> result = groupService.deleteGroup(groupIds);    
		if(!result.isSuccess()){
			addActionErrorsFromResult(result);
		}else{
			addActionMessage("删除成功");
		} 
		return INPUT;
	}
}
