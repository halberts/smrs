package com.smrs.security.dao;

import java.util.List;

import com.jof.framework.dao.BaseDAO;
import com.jof.framework.util.Pager;
import com.smrs.security.model.GroupModel;
import com.smrs.security.model.GroupUser;
import com.smrs.security.model.RoleModel;
import com.smrs.security.model.UserModel;
import com.smrs.security.vo.GroupSearchModel;
import com.smrs.security.vo.RoleSearchModel;
import com.smrs.security.vo.UserInGroupSearchModel;

/**
 * @author WangJian
 *
 */
public interface GroupDAO extends BaseDAO<GroupModel, Long>{   
	/**
	 * 分页查找所有组
	 * @param
	 * @retuen
	 */
	public Pager<GroupModel> getAllGroupsByPager(GroupSearchModel model);   
	/**
	 * 分页查找当前登录人员为管理员的所有组
	 * @param
	 * @retuen
	 */
	public Pager<GroupModel> getAdminGroupsByPager(GroupSearchModel model,Long userId);    
	/**
	 * 根据用户ID查找用户
	 * @param userid
	 * @return
	 */
	public UserModel getUserById(Long userid);
	/**
	 * 根据组ID获取组内的用户
	 * @param userid
	 * @return
	 */
	public Pager<GroupUser> getUsersByGroupId(UserInGroupSearchModel model, Long groupId) ;
	/**
	 * 查询组内管理员
	 * @param userid
	 * @return
	 */
	public Pager<GroupUser> getAdminByGroupId(UserInGroupSearchModel model, Long groupId) ; 
	/**
	 * 根据组ID获取组内的角色
	 * @param userid
	 * @return
	 */
	public Pager<RoleModel> getRolesByGroupId(RoleSearchModel model, Long groupId);
	/**
	 * 根据组ID获取组内的角色
	 * @param groupId
	 * @return
	 */
	public List<RoleModel> getRolesByGroupId(Long groupId);
	/**
	 * 根据组ID获取组内的用户
	 * @param groupId
	 * @return
	 */
	public List<UserModel> getUsersByGroupId(Long groupId);
	/**
	 * 根据组合用户ID 删除组中的用户
	 * @param groupId
	 * @param userId
	 */
	public void deleteUserFromGroup(Long groupId, String userId);
	/**
	 * 根据组合用户ID 删除组中的角色
	 * @param groupId
	 * @param userId
	 */
	public void deleteRoleFromGroup(Long groupId, String roleId);
	/**
	 * 将人员添加到组
	 * @param groupId
	 * @param userId
	 */
	public void addUserToGroup(Long groupId, String userId);
	/**
	 * 将角色添加到组
	 * @param groupId
	 * @param userId
	 */
	public void addRoleToGroup(Long groupId, String roleId);
	/**
	 * 给组添加管理员
	 * @param groupId
	 * @param userId
	 */
	public void addAdminToGroup(Long groupId,String userId);
	/**
	 * 删除组内管理员
	 * @param groupId
	 * @param userId
	 */
	public void deleteAdminFromGroup(Long groupId, String userId);
	/**
	 * 根据组ID以及用户ID查找用户组表中是否存在记录
	 * @param groupId
	 * @param userId
	 */
	public boolean searchUserFromGroup(Long groupId, String userId);
	/**
	 * 根据组ID以及角色ID查找角色组表中是否存在记录
	 * @param groupId
	 * @param userId
	 */
	public boolean searchRoleFromGroup(Long groupId, String roleId); 
	/**
	 * 查找组内是否存在人员
	 * @param userid
	 * @return
	 */
	public boolean isExistUser(Long id);
	/**
	 * 查找组内是否存在角色
	 * @param userid
	 * @return
	 */
	public boolean isExistRole(Long id);
	/**
	 * 查找组名称为groupName的组是否存在
	 * @param userid
	 * @return
	 */
	public boolean isExistGroup(String groupName); 
	
	/**
	 * 根据用户ID查找用户对应组中的角色
	 * @param userid
	 * @return
	 */
	public List<RoleModel> getRolesByUserId(Long userId);
	
	public Pager<GroupModel> getGroupsByNamePager(final GroupSearchModel model);
	
	public void deleteUserFromAllGroup(Long userId);
}
