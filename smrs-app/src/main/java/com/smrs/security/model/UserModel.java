package com.smrs.security.model;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import com.smrs.basicdata.model.StoreModel;
import com.smrs.model.BaseModel;

/**
 *  用户信息
 * @author WangXuzheng
 *
 */
public class UserModel extends BaseModel<Long> {
	private static final long serialVersionUID = -112280423769600328L;

	private String trueName;	
	private String mobile;	
	
	private String gender;
	private String userType;
	private String clerkType;
	private String userDiv;
	private String userJob;
	private String memo;
	private String responsibility;
	

	private String name;
	private String password;
	
	private String email;
	private Integer type;
	private String nickName;
	private String lastLoginIp;
	private String currentLoginIp;
	private Date lastLoginTime;
	private Integer loginAttemptTimes;
	private Character passwordModifiedFlag;//是否修改了系统初始化密码
	private Date passwordExpireTime;//密码到期时间
	private Date loginFaildTime;//登陆失败时间
	//private Set<RoleModel> roles = new HashSet<RoleModel>();
	//private Set<Department> departments = new HashSet<Department>();
	
	private Set<GroupModel> groups = new LinkedHashSet<GroupModel>(0);
	private Set<StoreModel> stores = new LinkedHashSet<StoreModel>(0);
	

	public Set<StoreModel> getStores() {
		return stores;
	}
	public void setStores(Set<StoreModel> stores) {
		this.stores = stores;
	}
	private String encode;//用户找回密码验证码
	private Date expiredTime;
	
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getResponsibility() {
		return responsibility;
	}
	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}
	
	public Date getExpiredTime() {
		return expiredTime;
	}
	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}
	public String getEncode() {
		return encode;
	}
	public void setEncode(String encode) {
		this.encode = encode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getCurrentLoginIp() {
		return currentLoginIp;
	}
	public void setCurrentLoginIp(String currentLoginIp) {
		this.currentLoginIp = currentLoginIp;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Integer getLoginAttemptTimes() {
		return loginAttemptTimes;
	}
	public void setLoginAttemptTimes(Integer loginAttemptTimes) {
		this.loginAttemptTimes = loginAttemptTimes;
	}
	public Date getLoginFaildTime() {
		return loginFaildTime;
	}
	public void setLoginFaildTime(Date loginFaildTime) {
		this.loginFaildTime = loginFaildTime;
	}
	public Character getPasswordModifiedFlag() {
		return passwordModifiedFlag;
	}
	public void setPasswordModifiedFlag(Character passwordModifiedFlag) {
		this.passwordModifiedFlag = passwordModifiedFlag;
	}
	public Date getPasswordExpireTime() {
		return passwordExpireTime;
	}
	public void setPasswordExpireTime(Date passwordExpireTime) {
		this.passwordExpireTime = passwordExpireTime;
	}
	/*
	public Set<RoleModel> getRoles() {
		return roles;
	}
	public void setRoles(Set<RoleModel> roles) {
		this.roles = roles;
	}
	*/
	/*
	public Set<Department> getDepartments() {
		return departments;
	}
	public void setDepartments(Set<Department> departments) {
		this.departments = departments;
	}
	*/
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getClerkType() {
		return clerkType;
	}
	public void setClerkType(String clerkType) {
		this.clerkType = clerkType;
	}
	public String getUserDiv() {
		return userDiv;
	}
	public void setUserDiv(String userDiv) {
		this.userDiv = userDiv;
	}
	public String getUserJob() {
		return userJob;
	}
	public void setUserJob(String userJob) {
		this.userJob = userJob;
	}
	public Set<GroupModel> getGroups() {
		return groups;
	}
	public void setGroups(Set<GroupModel> groups) {
		this.groups = groups;
	}
}
