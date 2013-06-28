package com.smrs.security.dao;

import java.util.LinkedList;
import java.util.List;

import com.jof.framework.dao.BaseDAO;
import com.jof.framework.util.Pager;
import com.smrs.security.model.ResourceModel;
import com.smrs.security.model.RoleModel;

/**
 * 资源资源
 * @author WangXuzheng
 * @author lupeng
 */
public interface ResourceDAO extends BaseDAO<ResourceModel,Long>{
	/**
	 * 通过资源名称获取资源信息
	 * @param name
	 * @return
	 */
	public ResourceModel getResourceByName(String name);
	
	/**
	 * 通过资源code获取资源信息
	 * @param code
	 * @return
	 */
	public ResourceModel getResourceByCode(String code);
	
	/**
	 * 查询子资源
	 * @param resourceId
	 * @return
	 */
	public List<ResourceModel> getChildren(Long resourceId);
	
	/**
	 * 获取某个节点的所有直接父节点列表
	 * @param resourceId
	 * @return
	 */
	public LinkedList<ResourceModel> getParentsChain(Long resourceId);
	
	/**
	 * 获取根节点资源信息列表
	 * @return
	 */
	public List<ResourceModel> getRoots();
	
	/**
	 * 获取指定id下的所有资源列表
	 * @param roleIds
	 * @return
	 */
	public List<ResourceModel> getResourceByRoleIds(Long[] roleIds); 
	 
	/**
	 * 获取指定用户id下的所有资源列表
	 * @param roleIds
	 * @return
	 */
	public List<ResourceModel> getGroupResourceByUserId(Long userId);
	
	/**
	 * 获取指定模块下的所有资源列表
	 * @param roles
	 * @param moduleName
	 * @return
	 */
	public List<ResourceModel> getUserDisplayedURLResourcesByModuleAndRoles(List<RoleModel> roles,String moduleName);
	/**
	 * 查询资源
	 * @param pager
	 * @param resource
	 * @return
	 */
	public Pager<ResourceModel> searchResources(Pager<ResourceModel> pager,ResourceModel resource);
	
	/**
	 * 获取某个资源下的所有子节点(包含间接子节点和自身)
	 * @param userId
	 * @param code
	 * @return
	 */
	public List<ResourceModel> getDescendants(Long userId,String code);
	/**
	 * 获取所有moduleName的列表
	 * @return
	 */
	public List<String> getmoduleNames();
}
