package com.smrs.basicdata.webapp.action;

import com.smrs.basicdata.model.Department;

/**
 * @author WangXuzheng
 *
 */
public class UpdateDepartmentInitAction extends BaseBasicDataAction {
	private static final long serialVersionUID = -7066510738193425636L;
	private Department department = new Department();
	public Department getDepartment() {
		return department;
	}
	private String titleName="部门";
	
	public String getTitleName() {
		return titleName;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	@Override
	public String execute() throws Exception {
		department = departmentService.getDepartmentById(department.getId());
		return SUCCESS;
	}
}
