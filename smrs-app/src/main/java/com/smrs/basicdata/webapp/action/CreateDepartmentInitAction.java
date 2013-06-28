package com.smrs.basicdata.webapp.action;

/**
 * @author WangXuzheng
 *
 */
public class CreateDepartmentInitAction extends BaseBasicDataAction {
	private static final long serialVersionUID = 8091724236627289921L;
	private String titleName="部门";
	
	public String getTitleName() {
		return titleName;
	}
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	public void CreateDepartmentInitAction(){
		System.out.println("###############CreateDepartmentInitAction inint");
	}
}
