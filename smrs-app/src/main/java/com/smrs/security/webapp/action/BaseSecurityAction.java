package com.smrs.security.webapp.action;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jof.framework.security.AbstractAuthenticator;
import com.jof.framework.security.Authentication;
import com.jof.framework.security.DefaultUrlAuthenticator;
import com.jof.framework.security.SessionSecurityConstants;
import com.smrs.basicdata.service.DepartmentService;
import com.smrs.basicdata.service.StoreService;
import com.smrs.enums.GenderTypeEnum;
import com.smrs.security.enums.ClerkTypeEnum;
import com.smrs.security.enums.UserTypeEnum;
import com.smrs.security.model.GroupModel;
import com.smrs.security.model.ResourceModel;
import com.smrs.security.service.GroupService;
import com.smrs.security.service.ResourceService;
import com.smrs.security.service.RoleService;
import com.smrs.security.service.UserService;
import com.smrs.util.DictConstants;
import com.smrs.webapp.action.BaseConsoleAction;

/**
 * Security模块的Action基类
 * @author WangXuzheng
 *
 */
public class BaseSecurityAction extends BaseConsoleAction {
	private static final long serialVersionUID = -6329296055785356921L; 
	/**
	 * 模块列表
	 */
//	protected Map<String, String> modules;
//	public void setModules(Map<String, String> modules) {
//		this.modules = modules;
//	}
//	public Map<String, String> getModules() {
//		return modules;
//	}
	protected List<GroupModel> allGroupList = new ArrayList<GroupModel>();
	protected List<GroupModel> selectedGroupList = new ArrayList<GroupModel>();
	
	protected List<UserTypeEnum> userTypeList = DictConstants.getInstance().getUserTypeList();
	protected List<ClerkTypeEnum> clerkTypeList=DictConstants.getInstance().getClerkTypeList();
	protected List<GenderTypeEnum> genderTypeList = DictConstants.getInstance().getGenderTypeList();
	protected List<ResourceModel> allResourceList = new ArrayList<ResourceModel>();
	
	public List<ResourceModel> getAllResourceList() {
		return allResourceList;
	}


	public void setAllResourceList(List<ResourceModel> allResourceList) {
		this.allResourceList = allResourceList;
	}


	public List<GenderTypeEnum> getGenderTypeList() {
		return genderTypeList;
	}

	
	public List<GroupModel> getAllGroupList() {
		return allGroupList;
	}


	public void setAllGroupList(List<GroupModel> allGroupList) {
		this.allGroupList = allGroupList;
	}


	public List<GroupModel> getSelectedGroupList() {
		return selectedGroupList;
	}


	public void setSelectedGroupList(List<GroupModel> selectedGroupList) {
		this.selectedGroupList = selectedGroupList;
	}


	public List<ClerkTypeEnum> getClerkTypeList() {
		return clerkTypeList;
	}

	public List<UserTypeEnum> getUserTypeList() {
		return userTypeList;
	}
	
	@Autowired
	protected StoreService storeService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	} 
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}
	
	public boolean hasComponentAuth(String componentCode){
		return getAuthenticator().hasComponentAuth(componentCode);
	}
	public boolean hasUrlAuth(String url){
		return getAuthenticator().hasUrlAuth(url);
	}
	
	private AbstractAuthenticator getAuthenticator(){
		Authentication authentication = (Authentication) getSession()
				.getAttribute(SessionSecurityConstants.KEY_AUTHENTICATION);
		AbstractAuthenticator authenticator = new DefaultUrlAuthenticator(authentication);
		return authenticator;
	}
}
