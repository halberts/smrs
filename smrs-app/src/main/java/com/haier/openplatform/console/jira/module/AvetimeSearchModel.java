package com.haier.openplatform.console.jira.module;

import com.haier.openplatform.util.Pager;
import com.haier.openplatform.util.SearchModel;

public class AvetimeSearchModel extends SearchModel<AvetimeModel>{
	private static final long serialVersionUID = -3157928768978363923L;
	private AvetimeModel avetimeModel =new AvetimeModel();
	public AvetimeModel getAvetimeModel() {
		return avetimeModel;
	}
	public void setAvetimeModel(AvetimeModel avetimeModel) {
		this.avetimeModel = avetimeModel;
	}
	
}
