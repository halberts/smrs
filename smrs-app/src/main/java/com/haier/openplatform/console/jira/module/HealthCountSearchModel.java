package com.haier.openplatform.console.jira.module;

import com.haier.openplatform.util.SearchModel;

public class HealthCountSearchModel extends SearchModel<HealthCountModel>{
	private static final long serialVersionUID = -3157928768978363923L;
	private  HealthCountModel  healthCountModel =new  HealthCountModel();
	public HealthCountModel getHealthCountModel() {
		return healthCountModel;
	}
	public void setHealthCountModel(HealthCountModel healthCountModel) {
		this.healthCountModel = healthCountModel;
	}
	
}
