package com.smrs.basicdata.webapp.action;

import com.jof.framework.util.ExecuteResult;
import com.smrs.basicdata.model.Department;


/**
 * @author WangXuzheng
 *
 */
public class DeleteDepartmentAction extends SearchDepartmentAction {
	private static final long serialVersionUID = 682321030704151503L;
	private Long id = 0L;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String execute() throws Exception {
		ExecuteResult<Department> result = departmentService.deleteDepartment(id);
		if(!result.isSuccess()){
			this.addActionErrorsFromResult(result);
		}
		super.execute();
		return SUCCESS;
	}
}
