package com.smrs.security.webapp.action;

import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.jof.framework.util.ExecuteResult;
import com.smrs.basicdata.model.StoreModel;

import com.smrs.security.model.GroupModel;
import com.smrs.security.model.UserModel;

/**
 * @author WangXuzheng
 *
 */
public class UpdateUserAction extends BaseSecurityUserAction {
	private static final long serialVersionUID = 1610319171366612494L;
	

	@Override
	public String execute() throws Exception {
		// 设置角色信息
		//configRoleInfo();
		// 设置部门关联信息
		configStoreInfo();
		//保存修改
		configGroupInfo();
		this.addActionMessage("更新成功");
		ExecuteResult<UserModel> result = userService.updateUser(user);
		if(!result.isSuccess()){
			this.addActionErrorsFromResult(result);
			return INPUT;
		}
		user=userService.getUserById(user.getId());
		allGroupList = groupService.getAllActiveGroupList();
		//Set<StoreModel> userStores = user.getStores(); 
		Set<GroupModel> groups=user.getGroups();
		allGroupList.removeAll(groups);
		
		if(groups!=null){
			logger.info(groups.toString());
		}
		return INPUT;
	}
	
	
	

	/*
	private void configRoleInfo() {
		String[] strID = roleIds.split(",");
		if(strID != null){
			for(String roleId : strID){
				if(StringUtils.isEmpty(roleId)){
					continue;
				}
				RoleModel role = new RoleModel();
				role.setId(Long.parseLong(roleId));
				//getUser().getRoles().add(role);
			}
		}
	}
	*/
}
