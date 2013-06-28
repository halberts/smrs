package com.smrs.security.service;

import java.util.List;

import com.jof.framework.util.ExecuteResult;
import com.jof.framework.util.Pager;
import com.smrs.security.model.GroupModel;
import com.smrs.security.model.GroupUser;
import com.smrs.security.model.RoleModel;
import com.smrs.security.vo.GroupSearchModel;
import com.smrs.security.vo.RoleSearchModel;
import com.smrs.security.vo.UserInGroupSearchModel;

/** 
 * @author WangJian
 *
 */
public interface GroupService { 
	/**
	 * 根据组的ID查找组信息
	 * @param
	 * @return
	 */
	public GroupModel getGroupById(Long id);  
	/**
	 * 分页查找组信息
	 * @param
	 * @return
	 */
	public Pager<GroupModel> getAllGroupsByPager(GroupSearchModel model);
	/**
	 * 分页查找当前登录人员所管理的组信息
	 * @param
	 * @return
	 */
	public Pager<GroupModel> getAdminGroupsByPager(GroupSearchModel model, Long userId); 
	/**
	 * 新建组
	 * @param
	 * @return
	 */
	public ExecuteResult<GroupModel> createGroup(GroupModel group);
	/**
	 * 删除组
	 * @param
	 * @return
	 */
	public ExecuteResult<GroupModel> deleteGroup(String groupIds);
	/**
	 * 更新组信息
	 * @param
	 * @return
	 */
	public ExecuteResult<GroupModel> updateGroup(GroupModel group,String groupName);  
	/**
	 * 向组中添加用户
	 * @param
	 * @return
	 */
	public ExecuteResult<GroupModel> addUserToGroup(Long groupId,String userId);
	/**
	 * 向组中添加角色
	 * @param
	 * @return
	 */
	public ExecuteResult<GroupModel> addRoleToGroup(Long groupId, String roleId);
	/**
	 * 删除组中的用户
	 * @param
	 * @return
	 */
	public ExecuteResult<GroupModel> deleteUserFromGroup(Long groupId, String userId);
	/**
	 * 删除组中的角色
	 * @param
	 * @return
	 */
	public ExecuteResult<RoleModel> deleteRoleFromGroup(Long groupId, String roleId); 
	/**
	 * 根据组ID查询组内用户
	 * @param
	 * @return
	 */
	public Pager<GroupUser> getUsersByGroupId(UserInGroupSearchModel model, Long id); 
	/**
	 * 获取组内管理员
	 * @param
	 * @return
	 */
	public Pager<GroupUser> getAdminByGroupId(UserInGroupSearchModel model, Long id);
	/**
	 * 根据组ID查询组内角色
	 * @param
	 * @return
	 */
	public Pager<RoleModel> getRolesByGroupId(RoleSearchModel model, Long id);
	/**
	 * 添加组管理员
	 * @param
	 * @return
	 */
	public ExecuteResult<GroupModel> addAdminToGroup(Long groupId, String userId);
	/**
	 * 删除组管理员
	 * @param
	 * @return
	 */
	public ExecuteResult<GroupModel> deleteAdminFromGroup(Long groupId, String userId);   
	/**
	 * 根据用户ID取出用户对应组中的角色
	 * @param
	 * @return
	 */ 
	public List<RoleModel> getRolesByUserId(Long userId); 
	
	public Pager<GroupModel> getGroupsByNamePager(final GroupSearchModel model);
	
	public List<GroupModel> getAllActiveGroupList();
	
	public ExecuteResult<GroupModel> deleteUserFromAllGroup(Long userId);
}
