package com.haier.openplatform.console.jira.module;

import com.haier.openplatform.util.SearchModel;

public class TimeWorkedSearchModel extends SearchModel<TimeWorkedModel>{
	
	private static final long serialVersionUID = 8764081427762002171L;
	private TimeWorkedModel model = new TimeWorkedModel();
	public TimeWorkedModel getModel() {
		return model;
	}
	public void setModel(TimeWorkedModel model) {
		this.model = model;
	}
	
	
	
}
