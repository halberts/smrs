package com.smrs.security.webapp.action;


import com.jof.framework.util.ExecuteResult;
import com.smrs.security.model.GroupModel;
import com.smrs.security.model.ResourceModel;

/**
 * 更新组信息
 * @author WangJian
 *
 */
public class UpdateGroupAction extends BaseSecurityAction{
 
	private static final long serialVersionUID = 2512575443173069617L;
	private GroupModel group; 
	private String oldName;
 	private Long[] selectedResources;
	public Long[] getSelectedResources() {
		return selectedResources;
	}

	public void setSelectedResources(Long[] selectedResources) {
		this.selectedResources = selectedResources;
	}

	
	 
	public String getOldName() {
		return oldName;
	}
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	public GroupModel getGroup() {
		return group;
	}
	public void setGroup(GroupModel group) {
		this.group = group;
	}
	
	@Override
	public String execute() throws Exception { 
		if(selectedResources!=null){
			for(int index=0;index<this.selectedResources.length;index++){
				ResourceModel resource = new ResourceModel();
				resource.setId(selectedResources[index]);
				group.getResources().add(resource);
			}
		}
		ExecuteResult<GroupModel> executeResult = groupService.updateGroup(group,oldName);
		if(!executeResult.isSuccess()){
			addErrorsFromResult(executeResult);
		}else{
			addActionMessage("更新成功");
		}
		return INPUT;
	}
}
