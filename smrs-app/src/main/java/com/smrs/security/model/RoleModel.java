package com.smrs.security.model;

import java.util.HashSet;
import java.util.Set;

import com.smrs.model.BaseModel;

/**
 * 角色
 * @author WangXuzheng
 *
 */
public class RoleModel extends BaseModel<Long> {
	private static final long serialVersionUID = -3028144700670190729L;
	private String name;
	private String description;
	private Set<ResourceModel> resources = new HashSet<ResourceModel>();
	private Set<UserModel> users = new HashSet<UserModel>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
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
}
