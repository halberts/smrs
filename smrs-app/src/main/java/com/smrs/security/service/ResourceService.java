package com.smrs.security.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.jof.framework.util.ExecuteResult;
import com.jof.framework.util.Pager;
import com.smrs.security.model.ResourceModel;

/**
 * @author WangXuzheng
 * @author lupeng
 */
public interface ResourceService {
	/**
	 * 创建资源
	 * @param resource
	 */
	public ExecuteResult<ResourceModel> createResource(ResourceModel resource);
	
	/**
	 * 更新资源信息
	 * @param resource
	 */
	public ExecuteResult<ResourceModel> updateResource(ResourceModel resource);
	
	/**
	 * 删除资源
	 * @param resourceId
	 * @return
	 */
	public ExecuteResult<ResourceModel> deleteResource(Long resourceId);
	
	/**
	 * 通过id获取资源信息
	 * @param resourceId
	 * @return
	 */
	public ResourceModel getResourceById(Long resourceId);
	
	/**
	 * 获取资源的子资源
	 * @param resourceId
	 * @return
	 */
	public List<ResourceModel> getChilden(Long resourceId);
	
	/**
	 * 获取某个节点的所有直接父节点列表
	 * @param resourceId
	 * @return
	 */
	public LinkedList<ResourceModel> getParentsChain(Long resourceId);
	
	/**
	 * 获取系统根资源
	 * @return
	 */
	public List<ResourceModel> getRoot();
	
	/**
	 * 查询指定角色下的资源列表
	 * @param roleIds
	 * @return
	 */
	public List<ResourceModel> getResourceByRole(Long[] roleIds);
	
	/**
	 * 获取系统中所有的资源列表
	 * @return
	 */
	public List<ResourceModel> getAll();
	
	/**
	 * 获取指定模块下用户可见的所有资源列表
	 * @param userId 用户ID信息
	 * @param moduleName
	 * @return
	 */
	public List<ResourceModel> getUserDisplayedURLResources(Long userId,String moduleName);
	
	/**
	 * 查询资源
	 */
	public Pager<ResourceModel> searchResources(Pager<ResourceModel> pager,ResourceModel resource);
	
	/**
	 * 查询某个用户所用于的资源所有子节点(包含间接子节点和自身)
	 * @param userId
	 * @param code
	 * @return
	 */
	public Map<ResourceModel,List<ResourceModel>> getUserResourceDescendants(Long userId,List<String> codeList);
	  
	/**
	 * 查询摸个用户对应的资源列表
	 * @param userId
	 * @return
	 */
	public List<ResourceModel> getGroupResourceByUserId(Long userId); 
}
