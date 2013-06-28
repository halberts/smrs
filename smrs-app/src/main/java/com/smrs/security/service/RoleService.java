package com.smrs.security.service;

import java.util.List;

import com.jof.framework.util.ExecuteResult;
import com.jof.framework.util.Pager;
import com.smrs.security.model.RoleModel;
import com.smrs.security.vo.RoleSearchModel;

/**
 * @author WangXuzheng
 *
 */
public interface RoleService {
	/**
	 * 创建角色
	 * @param role
	 * @return
	 */
	public ExecuteResult<RoleModel> createRole(RoleModel role);
	/**
	 * 更新角色
	 * @param role
	 * @return
	 */
	public ExecuteResult<RoleModel> updateRole(RoleModel role);
	
	/**
	 * 删除角色信息
	 * @param roleId
	 * @return
	 */
	public ExecuteResult<RoleModel> deleteRole(Long roleId);
	
	/**
	 * 查询角色信息
	 * @param searchModel
	 * @return
	 */
	public Pager<RoleModel> searchRoles(RoleSearchModel searchModel);
	
	/**
	 * 通过角色id获取角色信息
	 * @param roleId
	 * @return
	 */
	public RoleModel getRoleById(Long roleId);
	
	/**
	 * 获取系统所有角色列表
	 * @return
	 */
	public List<RoleModel> getRoles();
	
	/**
	 * 获取用户所拥有的角色信息
	 * @param userId
	 * @return
	 */
	public List<RoleModel> getUserRoles(Long userId);
}
