package com.smrs.security.dao;

import java.util.List;

import com.jof.framework.dao.BaseDAO;
import com.jof.framework.util.Pager;
import com.smrs.security.model.RoleModel;
import com.smrs.security.vo.RoleSearchModel;

/**
 * 角色DAO
 * @author WangXuzheng
 *
 */
public interface RoleDAO extends BaseDAO<RoleModel,Long> {
	/**
	 * 查询角色信息
	 * @param searchModel
	 * @return
	 */
	public Pager<RoleModel> searchRoles(RoleSearchModel searchModel);
	
	/**
	 * 通过名称查询role信息
	 * @param name
	 * @return
	 */
	public RoleModel getRoleByName(String name);
	
	/**
	 * 获取用户所拥有的角色信息
	 * @param userId
	 * @return
	 */
	public List<RoleModel> getUserRoles(Long userId);
}
