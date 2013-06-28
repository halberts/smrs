package com.smrs.security.webapp.action;


import com.jof.framework.util.ExecuteResult;
import com.smrs.security.model.ResourceModel;

/**
 * @author WangXuzheng
 * 2012-1-5
 */
public class UpdateResourceAction extends BaseSecurityAction {
	private static final long serialVersionUID = 8235019707215056317L;
	private ResourceModel resource=new ResourceModel();
	public ResourceModel getResource() {
		return resource;
	}

	public void setResource(ResourceModel resource) {
		this.resource = resource;
	}
	
	@Override
	public String execute() throws Exception {
		ExecuteResult<ResourceModel> result = resourceService.updateResource(resource);
		if(!result.isSuccess()){
			this.addActionErrorsFromResult(result);
			return INPUT;
		}
		return INPUT;
	}
}
