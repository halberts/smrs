package com.haier.openplatform.console.component.module;

import com.haier.openplatform.util.SearchModel;

public class ModelSearchModel extends SearchModel<Model>{
	private static final long serialVersionUID = -3157928768978363923L;
	private Model model = new Model();
	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	} 
}
