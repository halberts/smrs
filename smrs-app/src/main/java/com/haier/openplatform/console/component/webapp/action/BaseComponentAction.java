package com.haier.openplatform.console.component.webapp.action;

import com.haier.openplatform.console.component.service.ComponentService;
import com.smrs.webapp.action.BaseConsoleAction;

public class BaseComponentAction extends BaseConsoleAction{
	private static final long serialVersionUID = -7763734369037603010L;
	protected ComponentService componentService;

	public ComponentService getComponentService() {
		return componentService;
	}

	public void setComponentService(ComponentService componentService) {
		this.componentService = componentService;
	}
}
