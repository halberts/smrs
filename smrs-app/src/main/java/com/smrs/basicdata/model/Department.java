package com.smrs.basicdata.model;

import java.util.HashSet;
import java.util.Set;

import com.haier.openplatform.domain.BaseDomain;
import com.smrs.security.model.UserModel;

/**
 * 部门
 * @author WangXuzheng
 *
 */
public class Department extends BaseDomain<Long> {
	private static final long serialVersionUID = -2102767193865123749L;
	private String code;
	private String name;
	private int status;
	private String description;
	private Department parent;
	private Set<UserModel> users = new HashSet<UserModel>();
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Department getParent() {
		return parent;
	}
	public void setParent(Department parent) {
		this.parent = parent;
	}
	public Set<UserModel> getUsers() {
		return users;
	}
	public void setUsers(Set<UserModel> users) {
		this.users = users;
	}
}
