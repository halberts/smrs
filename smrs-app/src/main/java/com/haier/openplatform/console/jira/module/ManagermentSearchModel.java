package com.haier.openplatform.console.jira.module;

import com.haier.openplatform.util.SearchModel;

public class ManagermentSearchModel extends SearchModel<ManagermentModel>{
	
	private static final long serialVersionUID = 8764081427762002171L;
	private ManagermentModel managermodel = new ManagermentModel();
	
	public ManagermentModel getManagermodel() {
		return managermodel;
	}
	public void setManagermodel(ManagermentModel managermodel) {
		this.managermodel = managermodel;
	}
	
	
}
