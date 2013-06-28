package com.smrs.security.webapp.action;


import org.apache.commons.lang.StringUtils;

import com.jof.framework.util.ExecuteResult;
import com.smrs.security.model.GroupModel;
import com.smrs.security.model.ResourceModel;

/**
 * 创建组
 * @author WangJian
 *
 */
public class CreateGroupAction extends CreateGroupInitAction{

 	private static final long serialVersionUID = 2350720793674995546L;
 	private String resourceIds;
 	private	GroupModel group; 

 	private Long[] selectedResources;
	public Long[] getSelectedResources() {
		return selectedResources;
	}

	public void setSelectedResources(Long[] selectedResources) {
		this.selectedResources = selectedResources;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}
	public GroupModel getGroup() {
		return group;
	} 

	public void setGroup(GroupModel group) {
		this.group = group;
	}

	@Override
	public String execute() throws Exception {
		
		String[] resId = resourceIds.split(",");
		// 设置资源信息
		for(String roleId : resId){
			if(StringUtils.isEmpty(roleId)){
				continue;
			}
			ResourceModel resource = new ResourceModel();
			resource.setId(Long.parseLong(roleId));
			group.getResources().add(resource);
		}
		if(selectedResources!=null){
		for(int index=0;index<this.selectedResources.length;index++){
			ResourceModel resource = new ResourceModel();
			resource.setId(selectedResources[index]);
			group.getResources().add(resource);
		}
		}
		ExecuteResult<GroupModel> executeResult = groupService.createGroup(group); 
		if(!executeResult.isSuccess()){
			addActionErrorsFromResult(executeResult);
		}else{
			addActionMessage("添加成功"); 
		} 
		this.allResourceList = resourceService.getAll();
		return INPUT;
	}

}
