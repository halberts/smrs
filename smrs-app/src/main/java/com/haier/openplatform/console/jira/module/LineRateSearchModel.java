package com.haier.openplatform.console.jira.module;

import com.haier.openplatform.util.SearchModel;

public class LineRateSearchModel extends SearchModel<LineRateModel>{
	private static final long serialVersionUID = -3157928768978363923L;
	private LineRateModel lineRateModel =new LineRateModel();
	public LineRateModel getLineRateModel() {
		return lineRateModel;
	}
	public void setLineRateModel(LineRateModel lineRateModel) {
		this.lineRateModel = lineRateModel;
	}
	
}
