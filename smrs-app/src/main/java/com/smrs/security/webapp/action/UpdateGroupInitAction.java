package com.smrs.security.webapp.action;

import java.util.ArrayList;
import java.util.List;

import com.smrs.security.model.GroupModel;
import com.smrs.security.model.ResourceModel;

/**
 * 修改组信息初始化
 * @author WangJian
 *
 */
public class UpdateGroupInitAction extends BaseSecurityAction{

	private static final long serialVersionUID = -691636839686864805L;
	private GroupModel group; 
	
	
	public GroupModel getGroup() {
		return group;
	}
	public void setGroup(GroupModel group) {
		this.group = group;
	} 

	@Override
	public String execute() throws Exception { 
		this.group = groupService.getGroupById(group.getId()); 
		this.allResourceList = resourceService.getAll();
		return SUCCESS;
	}
}
