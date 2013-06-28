package com.smrs.security.model;

import java.util.HashSet;
import java.util.Set;

import com.smrs.model.BaseModel;

/**
 * 组
 * @author WangJian
 *
 */
public class GroupModel extends BaseModel<Long>{ 
	private static final long serialVersionUID = -4502159145110404481L;
	private String description; 
	private String name; 
	private Set<UserModel> users = new HashSet<UserModel>();
	//private Set<RoleModel> roles = new HashSet<RoleModel>();
	
	private Set<ResourceModel> resources = new HashSet<ResourceModel>();
	

	public Set<ResourceModel> getResources() {
		return resources;
	}
	public void setResources(Set<ResourceModel> resources) {
		this.resources = resources;
	}
	public Set<UserModel> getUsers() {
		return users;
	}
	public void setUsers(Set<UserModel> users) {
		this.users = users;
	} 
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	} 
}
