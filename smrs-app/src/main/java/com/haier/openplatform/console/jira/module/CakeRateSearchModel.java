package com.haier.openplatform.console.jira.module;

import com.haier.openplatform.util.SearchModel;

public class CakeRateSearchModel extends SearchModel<CakeRateModel>{
	private static final long serialVersionUID = -3157928768978363923L;
	private CakeRateModel cakeRateModel=new CakeRateModel();
	public CakeRateModel getCakeRateModel() {
		return cakeRateModel;
	}
	public void setCakeRateModel(CakeRateModel cakeRateModel) {
		this.cakeRateModel = cakeRateModel;
	}
	
}
