package com.smrs.basicdata.webapp.action;

import com.jof.framework.util.Pager;
import com.smrs.basicdata.model.Department;


/**
 * 部门信息检索action
 * @author WangXuzheng
 *
 */
public class SearchDepartmentAction extends BaseBasicDataAction {
	private static final long serialVersionUID = -1636112558471041725L;
	private Pager<Department> pager = new Pager<Department>();
	private Department department = new Department();
	private String titleName="部门";
	
	public String getTitleName() {
		return titleName;
	}
	public Pager<Department> getPager() {
		return pager;
	}

	public void setPager(Pager<Department> pager) {
		this.pager = pager;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String execute() throws Exception {
		this.pager = departmentService.searchDepartments(pager, department);
		return SUCCESS;
	}
}
