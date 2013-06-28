package com.smrs.security.model;

import java.util.HashSet;
import java.util.Set;

import com.smrs.model.BaseModel;

/**
 * 系统菜单
 * @author WangXuzheng
 *
 */
public class ResourceModel extends BaseModel<Long> {
	private static final long serialVersionUID = -4061243420033359942L;
	private ResourceModel parent;
	private String name;
	private String code;
	private String description;
	private String url;
	private String type;
	
	private String moduleName;
	private String configuration;
	private Integer orderIndex;
	private Set<GroupModel> groups = new HashSet<GroupModel>();
	private Set<ResourceModel> childResources = new HashSet<ResourceModel>();

	public ResourceModel getParent() {
		return parent;
	}
	public void setParent(ResourceModel parent) {
		this.parent = parent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}


	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getConfiguration() {
		return configuration;
	}
	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}
	public Integer getOrderIndex() {
		return orderIndex;
	}
	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	
	public Set<GroupModel> getGroups() {
		return groups;
	}
	public void setGroups(Set<GroupModel> groups) {
		this.groups = groups;
	}

	public Set<ResourceModel> getChildResources() {
		return childResources;
	}
	public void setChildResources(Set<ResourceModel> childResources) {
		this.childResources = childResources;
	}
}
