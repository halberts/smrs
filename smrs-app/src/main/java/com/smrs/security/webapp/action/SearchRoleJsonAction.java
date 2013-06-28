package com.smrs.security.webapp.action;

import org.apache.commons.lang.StringUtils;

import com.jof.framework.util.Pager;
import com.smrs.security.model.RoleModel;
import com.smrs.security.vo.RoleSearchModel;

/**
 * Ajax查询角色信息
 * @author WangJian
 *
 */
public class SearchRoleJsonAction extends BaseSecurityAction {
	private static final long serialVersionUID = -7889308010706653843L;
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
	private RoleModel role = new RoleModel();
	private Pager<RoleModel> pager = new Pager<RoleModel>();
	
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

	public RoleModel getRole() {
		return role;
	}
	
	public Pager<RoleModel> getPager() {
		return pager;
	}

	public void setPager(Pager<RoleModel> pager) {
		this.pager = pager;
	}

	public void setRole(RoleModel role) {
		this.role = role;
	}

	@Override
	public String execute() throws Exception {
		RoleSearchModel searchModel = new RoleSearchModel();
		getPager().setCurrentPage(page);
		getPager().setPageSize(rows);
		if(StringUtils.isNotEmpty(sidx)){
			getPager().setOrderProperty(sidx);
			getPager().setOrder(StringUtils.defaultIfEmpty(sord,"asc"));
		}
		searchModel.setPager(pager);
		searchModel.setRole(role);
		this.pager = roleService.searchRoles(searchModel);
		this.setTotal(this.getPager().getTotalPages());
		this.setRecords(this.getPager().getTotalRecords());
		return SUCCESS;
	}
}
