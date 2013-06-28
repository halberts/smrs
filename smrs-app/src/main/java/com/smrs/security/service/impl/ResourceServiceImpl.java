package com.smrs.security.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jof.framework.security.LoginContextHolder;
import com.jof.framework.util.ExecuteResult;
import com.jof.framework.util.Pager;
import com.smrs.security.dao.GroupDAO;
import com.smrs.security.dao.ResourceDAO;
import com.smrs.security.enums.ResourceTypeEnum;
import com.smrs.security.model.ResourceModel;
import com.smrs.security.model.RoleModel;
import com.smrs.security.service.ResourceService;

/**
 * @author WangXuzheng
 * @author lupeng
 */
public class ResourceServiceImpl implements ResourceService {
	private ResourceDAO resourceDAO; 
	private GroupDAO groupDAO;
	 
	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}
	public void setResourceDAO(ResourceDAO resourceDAO) {
		this.resourceDAO = resourceDAO;
	} 

	@Override
	public ExecuteResult<ResourceModel> createResource(ResourceModel resource) {
		// 去空格
		resource.setName(StringUtils.trim(resource.getName()));
		resource.setCode(StringUtils.trim(resource.getCode()));
		
		ExecuteResult<ResourceModel> executeResult = new ExecuteResult<ResourceModel>();
		ResourceModel dbResource = resourceDAO.getResourceByName(StringUtils.trim(resource.getName()));
		if(dbResource != null){
			executeResult.addErrorMessage("资源["+resource.getName()+"]已经存在!");
			return executeResult;
		}
		// 资源编码唯一性检查
		if(StringUtils.isNotEmpty(StringUtils.trim(resource.getCode()))){
			if(resourceDAO.getResourceByCode(resource.getCode()) != null){
				executeResult.addErrorMessage("资源编码["+resource.getCode()+"]已经存在。");
				return executeResult;
			}
		}
		
		resource.setName(StringUtils.trim(resource.getName()));
		resource.setCode(StringUtils.trim(resource.getCode()));
		resource.setCreationDate(new Date());
		//resource.setModified(new Date());
		resource.setCreator(LoginContextHolder.get().getUserName());
		resource.setModifiedBy(LoginContextHolder.get().getUserName());
		
		if(resource.getParent() == null || resource.getParent().getId() == null){//普通资源
			resource.setParent(null);
			resourceDAO.save(resource);
			resource.setParent(resource);
			resourceDAO.update(resource);
		}else{//系统级资源
			ResourceModel parent = resourceDAO.load(resource.getParent().getId());
			resource.setParent(parent);
			resourceDAO.save(resource);
		}
		executeResult.setResult(resource);
		return executeResult;
	}

	@Override
	public ExecuteResult<ResourceModel> updateResource(ResourceModel resource) {
		// 去空格
		resource.setName(StringUtils.trim(resource.getName()));
		resource.setCode(StringUtils.trim(resource.getCode()));
		
		ExecuteResult<ResourceModel> executeResult = new ExecuteResult<ResourceModel>();
		ResourceModel dbResource = resourceDAO.get(resource.getId());
		if(dbResource == null){
			executeResult.addErrorMessage("资源["+resource.getName()+"]不存在或已经被删除.");
			return executeResult;
		}
		
		// 重名检查
		if(!resource.getName().equals(dbResource.getName())){
			ResourceModel m = resourceDAO.getResourceByName(resource.getName());
			if(m != null){
				executeResult.addErrorMessage("资源["+resource.getName()+"]已经存在.");
				return executeResult;
			}
		}
		
		// 资源编码唯一性检查
		if(ResourceTypeEnum.toEnum(resource.getType()) == ResourceTypeEnum.COMPONENT_RESOURCE 
				|| StringUtils.isNotEmpty(resource.getCode())){
			if(!dbResource.getCode().equals(resource.getCode())){
				if(resourceDAO.getResourceByCode(resource.getCode()) == null){
					dbResource.setCode(resource.getCode());
				}else{
					executeResult.addErrorMessage("资源编码["+resource+"]已经存在。");
					return executeResult;
				}
			}
		}
		
		// 设置父资源
		if(resource.getParent() == null || resource.getParent().getId() == null){//普通资源
			dbResource.setParent(dbResource);
		}else{//系统级资源
			ResourceModel parent = resourceDAO.load(resource.getParent().getId());
			dbResource.setParent(parent);
		}
		
		// 设置普通字段值
		dbResource.setName(resource.getName());
		dbResource.setCode(resource.getCode());
		dbResource.setDescription(resource.getDescription());
		dbResource.setModifiedBy(LoginContextHolder.get().getUserName());
		dbResource.setLastModifyDate(new Date());
		dbResource.setOrderIndex(resource.getOrderIndex());
		dbResource.setConfiguration(resource.getConfiguration());
		dbResource.setStatus(resource.getStatus());
		dbResource.setUrl(resource.getUrl());
		resourceDAO.update(dbResource);
		executeResult.setResult(dbResource);
		return executeResult;
	}

	@Override
	public ExecuteResult<ResourceModel> deleteResource(Long resourceId) {
		ExecuteResult<ResourceModel> executeResult = new ExecuteResult<ResourceModel>();
		ResourceModel resource = resourceDAO.get(resourceId);
		if(resource == null){
			executeResult.addErrorMessage("该资源不存在.");
			return executeResult;
		}
		//无子资源
		List<ResourceModel> children = resourceDAO.getChildren(resourceId);
		if(!children.isEmpty()){
			executeResult.addErrorMessage("该资源下有"+children.size()+"个子资源，不能删除。");
			return executeResult;
		}
		resourceDAO.delete(resource);
		return executeResult;
	}

	@Override
	public ResourceModel getResourceById(Long resourceId) {
		return resourceDAO.get(resourceId);
	}

	@Override
	public List<ResourceModel> getChilden(Long resourceId) {
		return resourceDAO.getChildren(resourceId);
	}

	@Override
	public LinkedList<ResourceModel> getParentsChain(Long resourceId) {
		return resourceDAO.getParentsChain(resourceId);
	}

	@Override
	public List<ResourceModel> getRoot() {
		return resourceDAO.getRoots();
	}

	@Override
	public List<ResourceModel> getResourceByRole(Long[] roleIds) {
		return resourceDAO.getResourceByRoleIds(roleIds);
	} 

	@Override
	public List<ResourceModel> getAll() {
		return resourceDAO.getAll();
	}

	@Override
	public List<ResourceModel> getUserDisplayedURLResources(Long userId,String moduleName) { 
		List<RoleModel> roles = groupDAO.getRolesByUserId(userId);
		return resourceDAO.getUserDisplayedURLResourcesByModuleAndRoles(roles,moduleName);
	}

	@Override
	public Pager<ResourceModel> searchResources(Pager<ResourceModel> pager,ResourceModel resource) {
		return resourceDAO.searchResources(pager,resource);
	}
	
	@Override
	public Map<ResourceModel, List<ResourceModel>> getUserResourceDescendants(Long userId, List<String> codeList) {
		Map<ResourceModel, List<ResourceModel>> result = new LinkedHashMap<ResourceModel, List<ResourceModel>>();
		for(String code : codeList){
			List<ResourceModel> resources = resourceDAO.getDescendants(userId, code);
			ResourceModel parent = rebuildParent(code,resources);
			result.put(parent, resources);
		}
		return result;
	}
	
	private ResourceModel rebuildParent(String code,List<ResourceModel> resources){
		ResourceModel parent = null;
		for(ResourceModel resource : resources){
			if(StringUtils.equals(code, resource.getCode())){
				parent = resource;
				break;
			}
		}
		if(parent == null){
			parent = resourceDAO.findUniqueBy("code", code);
		}else{
			resources.remove(parent);
		}
		return parent;
	}
	@Override
	public List<ResourceModel> getGroupResourceByUserId(Long userId) {
		return resourceDAO.getGroupResourceByUserId(userId);
	}
}
