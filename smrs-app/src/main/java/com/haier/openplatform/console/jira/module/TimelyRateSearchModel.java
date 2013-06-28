package com.haier.openplatform.console.jira.module;

import com.haier.openplatform.util.SearchModel;

public class TimelyRateSearchModel extends SearchModel<TimelyRateModel>{
	private static final long serialVersionUID = -3157928768978363923L;
	private TimelyRateModel model = new TimelyRateModel();
	public TimelyRateModel getModel() {
		return model;
	}
	public void setModel(TimelyRateModel model) {
		this.model = model;
	} 
}
