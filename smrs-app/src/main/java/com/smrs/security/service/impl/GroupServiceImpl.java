package com.smrs.security.service.impl;

import java.util.List;

import com.jof.framework.util.ExecuteResult;
import com.jof.framework.util.Pager;

import com.smrs.security.dao.GroupDAO;
import com.smrs.security.dao.RoleDAO;
import com.smrs.security.dao.UserDAO;
import com.smrs.security.model.GroupModel;
import com.smrs.security.model.GroupUser;
import com.smrs.security.model.RoleModel;
import com.smrs.security.model.UserModel;
import com.smrs.security.service.GroupService;
import com.smrs.security.vo.GroupSearchModel;
import com.smrs.security.vo.RoleSearchModel;
import com.smrs.security.vo.UserInGroupSearchModel;

/**
 * @author WangJian
 * 
 */
public class GroupServiceImpl implements GroupService {
	private GroupDAO groupDAO;
	private UserDAO userDAO;
	private RoleDAO roleDAO;

	public RoleDAO getRoleDAO() {
		return roleDAO;
	} 
	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	} 
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public GroupDAO getGroupDAO() {
		return groupDAO;
	} 
	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}  

	@Override
	public Pager<GroupModel> getAllGroupsByPager(GroupSearchModel model) {
		return groupDAO.getAllGroupsByPager(model);
	}

	@Override
	public Pager<GroupModel> getAdminGroupsByPager(GroupSearchModel model,
			Long userId) {
		return groupDAO.getAdminGroupsByPager(model, userId);
	}

	public Pager<GroupModel> getGroupsByNamePager(final GroupSearchModel model){
		return groupDAO.getGroupsByNamePager(model);
		//return null;
	}
	@Override
	public ExecuteResult<GroupModel> createGroup(GroupModel group) {
		ExecuteResult<GroupModel> executeResult = new ExecuteResult<GroupModel>();
		if (!groupDAO.isExistGroup(group.getName())) {
			groupDAO.save(group);
		} else {
			executeResult.addErrorMessage("名称为" + group.getName() + "的组已经存在");
		}
		return executeResult;
	}

	@Override
	public ExecuteResult<GroupModel> deleteGroup(String groupIds) {
		ExecuteResult<GroupModel> executeResult = new ExecuteResult<GroupModel>();
		String[] groupId = groupIds.split(",");
		String faildDeleteGroup = "";
		for (int i = 0; i < groupId.length; i++) {
			Long groupid = Long.parseLong(groupId[i]);
			boolean isExistUserOrRole = false;
			if (groupDAO.isExistUser(groupid) || groupDAO.isExistRole(groupid)) {
				GroupModel group = groupDAO.get(groupid);
				faildDeleteGroup = "[" + group.getName()
						+ "]删除失败:原因为存在用户或者角色的组是禁止删除的<br/><br/>"
						+ faildDeleteGroup;
				executeResult.addErrorMessage(faildDeleteGroup);
				isExistUserOrRole = true;
			}
			if (!isExistUserOrRole) {
				groupDAO.delete(groupid);
			}
		}
		return executeResult;
	}

	@Override
	public ExecuteResult<GroupModel> updateGroup(GroupModel group, String groupName) {
		ExecuteResult<GroupModel> executeResult = new ExecuteResult<GroupModel>();
		if (groupDAO.isExistGroup(group.getName())
				&& !group.getName().equals(groupName)) { 
			executeResult.addErrorMessage("名称为" + group.getName() + "的组已经存在");
		} else {
			GroupModel dbGroup = groupDAO.get(group.getId());
			dbGroup.setName(group.getName());
			dbGroup.setDescription(group.getDescription());
			groupDAO.update(dbGroup); 
		}
		return executeResult;
	}

	@Override
	public GroupModel getGroupById(Long id) {
		return groupDAO.get(id);
	}

	@Override
	public ExecuteResult<GroupModel> addUserToGroup(Long groupId, String userId) {
		ExecuteResult<GroupModel> result = new ExecuteResult<GroupModel>();
		String faildAddUser = "";
		String[] userid = userId.split(",");
		for (int i = 0; i < userid.length; i++) {
			if (groupDAO.searchUserFromGroup(groupId, userid[i])) {
				UserModel user = userDAO.get(Long.parseLong(userid[i]));
				faildAddUser = "添加人员[" + user.getName()
						+ "]失败:原因为用户已存在<br/><br/>" + faildAddUser;
			} else {
				groupDAO.addUserToGroup(groupId, userid[i]);
			}
		}
		if (faildAddUser != null && !("").equals(faildAddUser)) {
			result.addErrorMessage(faildAddUser);
		}
		return result;
	}

	@Override
	public ExecuteResult<GroupModel> addRoleToGroup(Long groupId, String roleId) {
		ExecuteResult<GroupModel> result = new ExecuteResult<GroupModel>();
		String faildAddRole = "";
		String[] roleid = roleId.split(",");
		for (int i = 0; i < roleid.length; i++) {
			if (groupDAO.searchRoleFromGroup(groupId, roleid[i])) {
				RoleModel role = roleDAO.get(Long.parseLong(roleid[i]));
				faildAddRole = "添加角色[" + role.getName()
						+ "]失败:原因为角色已存在<br/><br/>" + faildAddRole;
			} else {
				groupDAO.addRoleToGroup(groupId, roleid[i]);
			}
		}
		if (faildAddRole != null && !"".equals(faildAddRole)) {
			result.addErrorMessage(faildAddRole);
		}
		return result;

	}

	@Override
	public ExecuteResult<GroupModel> deleteUserFromGroup(Long groupId, String userId) {
		ExecuteResult<GroupModel> result = new ExecuteResult<GroupModel>();
		String[] userid = userId.split(",");
		for (int i = 0; i < userid.length; i++) {
			groupDAO.deleteUserFromGroup(groupId, userid[i]);
		}
		return result;
	}

	@Override
	public ExecuteResult<GroupModel> deleteUserFromAllGroup(Long userId) {
		ExecuteResult<GroupModel> result = new ExecuteResult<GroupModel>();
		groupDAO.deleteUserFromAllGroup(userId);		
		return result;
	}
	
	
	@Override
	public ExecuteResult<RoleModel> deleteRoleFromGroup(Long groupId, String roleId) {
		ExecuteResult<RoleModel> result = new ExecuteResult<RoleModel>();
		String[] roleid = roleId.split(",");
		for (int i = 0; i < roleid.length; i++) {
			groupDAO.deleteRoleFromGroup(groupId, roleid[i]);
		}
		return result;
	}

	@Override
	public Pager<GroupUser> getUsersByGroupId(UserInGroupSearchModel model, Long id) {
		
		return groupDAO.getUsersByGroupId(model, id);
	}

	@Override
	public Pager<GroupUser> getAdminByGroupId(UserInGroupSearchModel model,
			Long id) {
		return groupDAO.getAdminByGroupId(model, id);
	}

	@Override
	public Pager<RoleModel> getRolesByGroupId(RoleSearchModel model, Long id) {
		return groupDAO.getRolesByGroupId(model, id);
	}

	@Override
	public ExecuteResult<GroupModel> addAdminToGroup(Long groupId, String userId) {
		ExecuteResult<GroupModel> result = new ExecuteResult<GroupModel>();
		if (!userId.isEmpty()) {
			String[] userid = userId.split(",");
			for (int i = 0; i < userid.length; i++) {
				groupDAO.addAdminToGroup(groupId, userid[i]);
			}
		}
		return result;
	}

	@Override
	public ExecuteResult<GroupModel> deleteAdminFromGroup(Long groupId, String userId) {
		ExecuteResult<GroupModel> result = new ExecuteResult<GroupModel>();
		if (!userId.isEmpty()) {
			String[] userid = userId.split(",");
			for (int i = 0; i < userid.length; i++) {
				groupDAO.deleteAdminFromGroup(groupId, userid[i]);
			}
		}
		return result;
	}   

	@Override
	public List<RoleModel> getRolesByUserId(Long userId) {
		return groupDAO.getRolesByUserId(userId);
	} 
	
	public List<GroupModel> getAllActiveGroupList(){
		List<GroupModel> list= groupDAO.getAllActive();
		return list;
	}
	
}
