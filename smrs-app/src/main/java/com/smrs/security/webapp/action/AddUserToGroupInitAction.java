package com.smrs.security.webapp.action;


import java.util.List;

import com.jof.framework.util.Pager;
import com.smrs.security.model.GroupModel;
import com.smrs.security.model.UserModel;
import com.smrs.security.vo.UserSearchModel;

/**
 * 用户添加到组初始化
 * @author WangJian
 *
 */
public class AddUserToGroupInitAction extends BaseSecurityAction{
 
	private static final long serialVersionUID = -6463722237191644260L;
	private Long rows = 0L;
	private Long page = 0L;
	private Long total = 0L;
	private Long records = 0L;
	private String sord;
	private String sidx; 
	private Pager<UserModel> pagerUser = new Pager<UserModel>();
	private UserModel user = new UserModel();
	private GroupModel group; 
	private Pager<UserModel> pagerUserInGroup = new Pager<UserModel>();
	private UserModel userInGroup = new UserModel();
	
	
	public Long getRows() {
		return rows;
	}

	public void setRows(Long rows) {
		this.rows = rows;
	}

	public Long getPage() {
		return page;
	}

	public void setPage(Long page) {
		this.page = page;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getRecords() {
		return records;
	}

	public void setRecords(Long records) {
		this.records = records;
	}

	public String getSord() {
		return sord; 
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public GroupModel getGroup() {
		return group;
	}

	public void setGroup(GroupModel group) {
		this.group = group;
	}

	public Pager<UserModel> getPagerUser() {
		return pagerUser;
	}

	public void setPagerUser(Pager<UserModel> pagerUser) {
		this.pagerUser = pagerUser;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	
	public Pager<UserModel> getPagerUserInGroup() {
		return pagerUserInGroup;
	}

	public void setPagerUserInGroup(Pager<UserModel> pagerUserInGroup) {
		this.pagerUserInGroup = pagerUserInGroup;
	}

	public UserModel getUserInGroup() {
		return userInGroup;
	}

	public void setUserInGroup(UserModel userInGroup) {
		this.userInGroup = userInGroup;
	}

	@Override
	public String execute() throws Exception {  
		UserSearchModel userSearchModel = new UserSearchModel();
		userSearchModel.setUser(user);
		pagerUser.setCurrentPage(page);
		pagerUser.setPageSize(rows);
		userSearchModel.setPager(pagerUser);
		this.pagerUser = userService.searchUser(userSearchModel);
		this.setTotal(pagerUser.getTotalPages());
		this.setRecords(pagerUser.getTotalRecords());
		if(pagerUser.getRecords()!=null){
			List<UserModel> list= pagerUser.getRecords();
			for(UserModel one:list){
				one.setGroups(null);
			}
		}
		
		
		/*
		UserSearchModel userInGroupSearchModel = new UserSearchModel(); 
		userInGroupSearchModel.setUser(userInGroup);
		userInGroupSearchModel.setPager(pagerUserInGroup);
		this.pagerUserInGroup = groupService.getUsersByGroupId(userInGroupSearchModel,group.getId());
		*/
		return SUCCESS;
	}
}
