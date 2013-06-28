package com.haier.openplatform.console.jira.module;

import com.haier.openplatform.util.SearchModel;

public class UserSatisfactionSearchModel extends SearchModel<UserSatisfactionModel>{
	private static final long serialVersionUID = -3157928768978363923L;
	private UserSatisfactionModel model = new UserSatisfactionModel();
	public UserSatisfactionModel getModel() {
		return model;
	}
	public void setModel(UserSatisfactionModel model) {
		this.model = model;
	} 
}
