package com.smrs.basicdata.webapp.action;

import com.jof.framework.util.ExecuteResult;
import com.smrs.basicdata.model.Department;


/**
 * 创建部门action
 * @author WangXuzheng
 *
 */
public class CreateDepartmentAction extends BaseBasicDataAction {
	private static final long serialVersionUID = 8900303964679796305L;
	private Department department = new Department();
	private String titleName="部门";
	
	public String getTitleName() {
		return titleName;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String execute() throws Exception {
		ExecuteResult<Department> executeResult = departmentService.createDepartment(department);
		if(!executeResult.isSuccess()){
			addActionErrorsFromResult(executeResult);
			return INPUT;
		}
		return INPUT;
	}
}
