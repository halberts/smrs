package com.smrs.security.webapp.action;
 
import org.apache.commons.lang.StringUtils;

import com.jof.framework.util.Pager;
import com.smrs.security.model.GroupModel;
import com.smrs.security.model.GroupUser;
import com.smrs.security.vo.UserInGroupSearchModel;

/**
 * 查询组组内人员
 * @author WangJian
 */
public class SearchUserInGroupAction extends BaseSecurityAction {
 
	private static final long serialVersionUID = -1856165685226757919L;
	
 
	private GroupModel group; 
	private Pager<GroupUser> pagerUserInGroup = new Pager<GroupUser>();
	private GroupUser userInGroup = new GroupUser();
	private Long rows = 0L;
	private Long page = 0L;
	private Long total = 0L;
	private Long records = 0L;
	private String sord;
	private String sidx;
	private String searchField;
	private String searchString;
	private String searchOper;
	private String order;
	private boolean loadonce = false;
	private boolean search = true; 
	
	public Pager<GroupUser> getPagerUserInGroup() {
		return pagerUserInGroup;
	}
	public void setPagerUserInGroup(Pager<GroupUser> pagerUserInGroup) {
		this.pagerUserInGroup = pagerUserInGroup;
	}
	public GroupUser getUserInGroup() {
		return userInGroup;
	}
	public void setUserInGroup(GroupUser userInGroup) {
		this.userInGroup = userInGroup;
	}
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
	public String getSearchField() {
		return searchField;
	}
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	public String getSearchOper() {
		return searchOper;
	}
	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public boolean isLoadonce() {
		return loadonce;
	}
	public void setLoadonce(boolean loadonce) {
		this.loadonce = loadonce;
	}
	public boolean isSearch() {
		return search;
	}
	public void setSearch(boolean search) {
		this.search = search;
	}
	public GroupModel getGroup() {
		return group;
	} 
	public void setGroup(GroupModel group) {
		this.group = group;
	}


	public String execute() throws Exception { 
		UserInGroupSearchModel userSearchModel = new UserInGroupSearchModel();
		pagerUserInGroup.setCurrentPage(page);
		pagerUserInGroup.setPageSize(rows);
		if(StringUtils.isNotEmpty(sidx)){
			pagerUserInGroup.setOrderProperty(sidx);
			pagerUserInGroup.setOrder(StringUtils.defaultIfEmpty(sord,"asc"));
		}
		userSearchModel.setGroupUser(userInGroup);
		userSearchModel.setPager(pagerUserInGroup);
		this.pagerUserInGroup = groupService.getUsersByGroupId(userSearchModel,group.getId());
		this.setTotal(this.pagerUserInGroup.getTotalPages());
		this.setRecords(this.pagerUserInGroup.getTotalRecords());
		return SUCCESS; 
		
	} 

}
