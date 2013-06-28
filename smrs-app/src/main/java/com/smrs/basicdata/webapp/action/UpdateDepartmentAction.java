package com.smrs.basicdata.webapp.action;

import com.jof.framework.util.ExecuteResult;
import com.smrs.basicdata.model.Department;


/**
 * @author WangXuzheng
 *
 */
public class UpdateDepartmentAction extends BaseBasicDataAction {
	private static final long serialVersionUID = -9026835841958339874L;
	private Department department=new Department();
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
		ExecuteResult<Department> result = departmentService.updateDepartment(department);
		if(!result.isSuccess()){
			this.addActionErrorsFromResult(result);
			return INPUT;
		}
		return INPUT;
	}
}
