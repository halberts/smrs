package com.smrs.security.webapp.action;

import com.smrs.security.model.ResourceModel;

/**
 * @author WangXuzheng
 * @author lupeng
 * 2012-1-5
 */
public class UpdateResourceInitAction extends BaseSecurityAction {
	private static final long serialVersionUID = 8235019707215056317L;
	private ResourceModel resource = new ResourceModel();
	public ResourceModel getResource() {
		return resource;
	}

	public void setResource(ResourceModel resource) {
		this.resource = resource;
	}
	
	@Override
	public String execute() throws Exception {
		resource = resourceService.getResourceById(resource.getId());
		return SUCCESS;
	}
}
