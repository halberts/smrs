package com.smrs.security.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.smrs.basicdata.model.StoreModel;

import com.smrs.security.model.GroupModel;
import com.smrs.security.model.RoleModel;
import com.smrs.security.model.UserModel;

/**
 * @author WangXuzheng
 * @author lupeng 2012-1-9
 */
public class UpdateUserInitAction extends BaseSecurityAction {
	private static final long serialVersionUID = 1610319171366612494L;
	private UserModel user = new UserModel();

	private Map<Long, String> checkedRoleMap = new HashMap<Long, String>();


	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public Map<Long, String> getCheckedRoleMap() {
		return checkedRoleMap;
	}
	
	
	
	@Override
	public String execute() throws Exception {
		allGroupList = groupService.getAllActiveGroupList();
		Long userId = user.getId();
		user = userService.getUserById(userId);
		
		Set<StoreModel> userStores = user.getStores(); 
		Set<GroupModel> groups=user.getGroups();
		allGroupList.removeAll(groups);
		
		if(groups!=null)
			logger.info(groups.toString());
		//List<GroupModel> allGroupList = groupService.get
		/*
		if(CollectionUtils.isNotEmpty(userStores)){
			Iterator<StoreModel> iterator= userStores.iterator();
			while(iterator.hasNext()){
				logger.info("userOne="+iterator.next());
			}
		}
		*/
		List<StoreModel> allStores = storeService.getAll();
		List<StoreModel> selectedStores = new ArrayList<StoreModel>();
		selectedStores.addAll(userStores);
		//configCheckedStore(allStores,selectedStores);
		
		
		//List<RoleModel> userRoles = roleService.getUserRoles(userId);
		//roles = roleService.getRoles();
		//configCheckedRole(roles, userRoles);
		//List<Department> depts = departmentService.getUserDepartments(userId);
		//user.setDepartments(new HashSet<Department>(depts));
		
		return SUCCESS;
	}
	
	/**
	 * 设置选中role的map
	 * @param allRoles
	 * @param selectedRoles
	 */
	public  void configCheckedStore(List<StoreModel> allStores,List<StoreModel> selectedStores){
		for(StoreModel store : allStores){
			checkedRoleMap.put(store.getId(), "");
		}
		for(StoreModel store : selectedStores){
			checkedRoleMap.put(store.getId(), " checked=\"checked\" ");
		}
	}
	
	
	/**
	 * 设置选中role的map
	 * @param allRoles
	 * @param selectedRoles
	 */
	public  void configCheckedRole(List<RoleModel> allRoles,List<RoleModel> selectedRoles){
		for(RoleModel role : allRoles){
			checkedRoleMap.put(role.getId(), "");
		}
		for(RoleModel department : selectedRoles){
			checkedRoleMap.put(department.getId(), " checked=\"checked\" ");
		}
	}
}
