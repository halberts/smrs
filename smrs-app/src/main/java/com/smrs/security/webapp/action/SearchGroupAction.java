package com.smrs.security.webapp.action;

import java.util.List;

import com.jof.framework.security.LoginContextHolder;
import com.jof.framework.util.Pager;
import com.smrs.security.model.GroupModel;
import com.smrs.security.model.RoleModel;
import com.smrs.security.vo.GroupSearchModel;

/**
 * 查询组信息
 * @author WangJian
 */
public class SearchGroupAction extends BaseSecurityAction {
 
	private static final long serialVersionUID = -3891161453585065295L; 
	private Pager<GroupModel> pager = new Pager<GroupModel>(); 
	private GroupModel group = new GroupModel(); 
	
	public Pager<GroupModel> getPager() {
		return pager;
	}
	public void setPager(Pager<GroupModel> pager) {
		this.pager = pager;
	}
  
	public GroupModel getGroup() {
		return group;
	}
	public void setGroup(GroupModel group) {
		this.group = group;
	}
	 
	@Override
	public String execute() throws Exception {  
		if(pager.getPageSize()==20){
			pager.setPageSize(10L);
		} 
		GroupSearchModel groupSearchModel = new GroupSearchModel();
		groupSearchModel.setGroup(group);
		groupSearchModel.setPager(pager);
		//Long userid = LoginContextHolder.get().getUserId(); 
		//boolean isAuth = false;
		/*
		List<RoleModel> listRole = groupService.getRolesByUserId(userid);
		
		for(int i=0;i<listRole.size();i++){
			if(listRole.get(i).getId() ==1 || listRole.get(i).getId() == 4){
				this.pager = groupService.getAllGroupsByPager(groupSearchModel);
				isAuth = true; 
				break;
				}
		} 
		
		if(!isAuth){ 
			this.pager = groupService.getAdminGroupsByPager(groupSearchModel,LoginContextHolder.get().getUserId()); 
		}
		*/
		pager = groupService.getGroupsByNamePager(groupSearchModel);
		return SUCCESS;
	}

}
