package com.smrs.security.webapp.action;


import com.jof.framework.util.Pager;
import com.smrs.security.model.ResourceModel;

/**
 * @author lupeng 2012-1-5
 */
public class SearchResourceAction extends BaseSecurityAction {
	private static final long serialVersionUID = -522813327706559035L;

	private Pager<ResourceModel> pager = new Pager<ResourceModel>();
	private ResourceModel resource = new ResourceModel();
	public Pager<ResourceModel> getPager() {
		return pager;
	}

	public void setPager(Pager<ResourceModel> pager) {
		this.pager = pager;
	}

	public ResourceModel getResource() {
		return resource;
	}

	public void setResource(ResourceModel resource) {
		this.resource = resource;
	}

	@Override
	public String execute() throws Exception {
		this.pager=resourceService.searchResources(pager,resource);
		return SUCCESS;
	}

}
