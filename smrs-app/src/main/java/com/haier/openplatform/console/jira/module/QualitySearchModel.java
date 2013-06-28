package com.haier.openplatform.console.jira.module;

import com.haier.openplatform.util.SearchModel;

public class QualitySearchModel extends SearchModel<QualityModel>{
	
	private static final long serialVersionUID = 8764081427762002171L;
	private QualityModel qualitymodel = new QualityModel();
	
	public QualityModel getQualitymodel() {
		return qualitymodel;
	}
	public void setQualitymodel(QualityModel qualitymodel) {
		this.qualitymodel = qualitymodel;
	}
	
	
	
}
