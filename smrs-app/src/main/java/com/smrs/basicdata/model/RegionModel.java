package com.smrs.basicdata.model;

import com.smrs.model.BaseModel;

public class RegionModel extends BaseModel<Long>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String description;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
