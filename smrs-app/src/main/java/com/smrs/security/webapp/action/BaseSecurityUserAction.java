package com.smrs.security.webapp.action;



import java.util.LinkedHashSet;

import org.apache.commons.lang.StringUtils;

import com.smrs.basicdata.model.StoreModel;
import com.smrs.security.model.GroupModel;
import com.smrs.security.model.UserModel;

/**
 * Security模块的Action基类
 * @author WangXuzheng
 *
 */
public class BaseSecurityUserAction extends BaseSecurityAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Long[] selectedGroups;
	
	protected Long[] leftAllList;
	
	protected UserModel user = new UserModel();
	
	protected String storeIds = "";

	
	
	public Long[] getLeftAllList() {
		return leftAllList;
	}

	public void setLeftAllList(Long[] leftAllList) {
		this.leftAllList = leftAllList;
	}

	public String getStoreIds() {
		return storeIds;
	}

	public void setStoreIds(String storeIds) {
		this.storeIds = storeIds;
	}
	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}
	
	public Long[] getSelectedGroups() {
		return selectedGroups;
	}

	public void setSelectedGroups(Long[] selectedGroups) {
		this.selectedGroups = selectedGroups;
	}
	
	public void configGroupInfo(){
		//String[] strID=roleIds.split(",");
		groupService.deleteUserFromAllGroup(user.getId());
		if(this.selectedGroups != null){
			LinkedHashSet<GroupModel> list = new LinkedHashSet<GroupModel>();
			for(Long id : selectedGroups){
				if(id==null || id.equals(0l)){
					continue;
				}
				GroupModel model = new GroupModel();
				model.setId(id);
				
				//role.setId(Long.parseLong(roleId));
				//getUser().getRoles().add(role);
				//list.add(model);
				//GroupModel group = groupService.getGroupById(id);
				//group.getUsers().add(user);
				//groupService.updateGroup(group);
				groupService.addUserToGroup(id, user.getId().toString());
			}
			user.setGroups(list);
		}
	}
	
	public void configStoreInfo(){
		String[] strID=storeIds.split(",");
		if(strID != null){
			for(String storeId : strID){
				if(StringUtils.isEmpty(storeId)){
					continue;
				}
				StoreModel store = new StoreModel();
				store.setId(Long.parseLong(storeId));
				getUser().getStores().add(store);
			}
		}
	}
}
