package com.smrs.security.webapp.action;

import org.apache.commons.lang.StringUtils;

import com.jof.framework.util.Pager;
import com.smrs.security.model.GroupModel;
import com.smrs.security.model.RoleModel;
import com.smrs.security.vo.RoleSearchModel;
 

public class SearchRoleInGroupAction extends BaseSecurityAction{
	 
	private static final long serialVersionUID = -8331652582935644944L;
	private GroupModel group;  
	private Pager<RoleModel> pagerRoleInGroup = new Pager<RoleModel>();
	private RoleModel roleInGroup = new RoleModel();
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
	 
	public GroupModel getGroup() {
		return group;
	}

	public void setGroup(GroupModel group) {
		this.group = group;
	}

	public Pager<RoleModel> getPagerRoleInGroup() {
		return pagerRoleInGroup;
	}

	public void setPagerRoleInGroup(Pager<RoleModel> pagerRoleInGroup) {
		this.pagerRoleInGroup = pagerRoleInGroup;
	}

	public RoleModel getRoleInGroup() {
		return roleInGroup;
	}

	public void setRoleInGroup(RoleModel roleInGroup) {
		this.roleInGroup = roleInGroup;
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
	
	public String execute() throws Exception { 
		RoleSearchModel roleSearchModel = new RoleSearchModel(); 
		pagerRoleInGroup.setCurrentPage(page);
		pagerRoleInGroup.setPageSize(rows);
		if(StringUtils.isNotEmpty(sidx)){
			pagerRoleInGroup.setOrderProperty(sidx);
			pagerRoleInGroup.setOrder(StringUtils.defaultIfEmpty(sord,"asc"));
		}
		roleSearchModel.setRole(roleInGroup);
		roleSearchModel.setPager(pagerRoleInGroup);
		this.pagerRoleInGroup = groupService.getRolesByGroupId(roleSearchModel,group.getId());
		this.setTotal(this.pagerRoleInGroup.getTotalPages());
		this.setRecords(this.pagerRoleInGroup.getTotalRecords());
		return SUCCESS;
	} 

}
