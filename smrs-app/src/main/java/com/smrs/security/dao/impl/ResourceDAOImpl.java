package com.smrs.security.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.jof.framework.dao.hibernate.BaseDAOHibernateImpl;
import com.jof.framework.util.Pager;
import com.smrs.security.dao.ResourceDAO;
import com.smrs.security.enums.ResourceStatusEnum;
import com.smrs.security.enums.ResourceTypeEnum;
import com.smrs.security.model.ResourceModel;
import com.smrs.security.model.RoleModel;

/**
 * @author WangXuzheng
 * @author lupeng
 */
public class ResourceDAOImpl extends BaseDAOHibernateImpl<ResourceModel,Long> implements ResourceDAO {
	@Override
	public ResourceModel getResourceByName(String name) {
		return this.findUniqueBy("name", name);
	}
	
	@Override
	public ResourceModel getResourceByCode(String code) {
		return this.findUniqueBy("code", code);
	}

	@Override
	public ResourceModel get(Long id) {
		ResourceModel result = super.get(id);
		if(result != null){
			Hibernate.initialize(result.getParent());
		}
		return result;
	}

	@Override
	public List<ResourceModel> getChildren(Long resourceId) {
		final Map<String, Object> param = new HashMap<String, Object>();
		param.put("parentId", resourceId);
		return this.findByNamedQuery("resource.getChildren", param);
	}

	@Override
	public LinkedList<ResourceModel> getParentsChain(Long resourceId) {
		LinkedList<ResourceModel> resources = new LinkedList<ResourceModel>();
		ResourceModel resource = get(resourceId);
		if(resource == null){
			return resources;
		}
		ResourceModel tmpResource = resource;
		while(tmpResource.getParent() != null && !tmpResource.getParent().getId().equals(tmpResource.getId())){
			resources.addFirst(tmpResource.getParent());
			tmpResource = tmpResource.getParent();
			initProxyObject(tmpResource);
		}
		resources.addLast(resource);
		return resources;
	}

	@Override
	public List<ResourceModel> getRoots() {
		return this.findByNamedQuery("resource.getRoots", new HashMap<String, Object>());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResourceModel> getResourceByRoleIds(final Long[] roleIds) {
		if(roleIds == null || roleIds.length == 0){
			return new ArrayList<ResourceModel>(0);
		}
		Criteria criteria = this.createCriteria();
		criteria.setFetchMode("roles", FetchMode.JOIN);
		criteria.createAlias("roles", "roles");
		criteria.add(Restrictions.in("roles.id", roleIds));
		distinct(criteria);
		return criteria.list();
	}
	  
	@Override
	public List<ResourceModel> getGroupResourceByUserId(final Long userId) {  
		Map<String, Long> values = new HashMap<String, Long>();
		values.put("userId", userId); 
		return this.findByNamedQuery("resource.getGroupResourceByUserId", values);    
		 
	}
	 
	@SuppressWarnings("unchecked")
	@Override
	public List<ResourceModel> getUserDisplayedURLResourcesByModuleAndRoles(List<RoleModel> roles,String moduleName) {
		if(roles.isEmpty()){
			return new ArrayList<ResourceModel>(0);
		}
		Criteria criteria = this.createCriteria();
		criteria.setFetchMode("roles", FetchMode.JOIN);
		criteria.createAlias("roles", "roles");
		criteria.add(Restrictions.eq("moduleName", moduleName));
		criteria.add(Restrictions.eq("status", ResourceStatusEnum.ACTIVE.getStatus()));
		criteria.add(Restrictions.eq("type", ResourceTypeEnum.URL_RESOURCE.getType()));
		criteria.add(Restrictions.in("roles.id", convertToArray(roles)));
		criteria.addOrder(Order.asc("orderIndex"));
		distinct(criteria);
		return criteria.list();
	}
	
	private Long[] convertToArray(List<RoleModel> roles){
		final int len = roles.size();
		Long[] result = new Long[len];
		for(int i = 0; i < len; i++){
			result[i] = roles.get(i).getId();
		}
		return result;
	}

	@Override
	public Pager<ResourceModel> searchResources(Pager<ResourceModel> pager, final ResourceModel resource) {
		return this.findPage(pager, this.createCriterions(resource));
	}
	
	private Criterion[] createCriterions(ResourceModel resource){
		List<Criterion> criterions = new ArrayList<Criterion>();
		if(StringUtils.isNotBlank(resource.getName())){
			criterions.add(Restrictions.like("name", resource.getName(),MatchMode.ANYWHERE));
		}
		if(resource.getType()!=null){
			criterions.add(Restrictions.eq("type", resource.getType()));
		}
		return this.list2Array(criterions);
	}

	@Override
	public List<ResourceModel> getDescendants(Long userId,String code) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("userId", String.valueOf(userId));
		values.put("code", code);
		values.put("type", String.valueOf(ResourceTypeEnum.URL_RESOURCE.getType()));
		values.put("status", String.valueOf(ResourceStatusEnum.ACTIVE.getStatus()));
		return this.findByNamedQuery("resource.getDescendants", values);
	}

	@Override
	public List<String> getmoduleNames() { 
		String hql = "select distinct resource.moduleName from Resource resource";   
		return findByHQL(hql);
	}
}
