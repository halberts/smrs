package com.smrs.security.webapp.action;


import com.jof.framework.util.ExecuteResult;
import com.smrs.security.model.ResourceModel;

/**
 * @author WangXuzheng
 * @author lupeng 2012-1-9
 */
public class DeleteResourceAction extends SearchResourceAction {
	private static final long serialVersionUID = 7074521343850760058L;
	private long resourceId;

	public long getResourceId() {
		return resourceId;
	}

	public void setResourceId(long resourceId) {
		this.resourceId = resourceId;
	}

	@Override
	public String execute() throws Exception {
		ExecuteResult<ResourceModel> result = resourceService.deleteResource(resourceId);
		if(!result.isSuccess()){
			addErrorsFromResult(result);
		}
		super.execute();
		return SUCCESS;
	}
}
