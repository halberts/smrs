package com.smrs.security.webapp.action;

import org.apache.commons.lang.StringUtils;

import com.jof.framework.util.ExecuteResult;
import com.smrs.security.enums.ResourceTypeEnum;
import com.smrs.security.model.ResourceModel;

/**
 * 新建菜单项
 * @author WangXuzheng
 *
 */
public class CreateResourceAction extends BaseSecurityAction{
	private static final long serialVersionUID = 5190449132193835235L;
	private ResourceModel resource;
	
	public ResourceModel getResource() {
		return resource;
	}

	public void setResource(ResourceModel resource) {
		this.resource = resource;
	}


	public String execute() throws Exception {
		ExecuteResult<ResourceModel> executeResult = resourceService.createResource(resource);
		if(!executeResult.isSuccess()){
			addErrorsFromResult(executeResult);
			return INPUT;
		}
		return INPUT;
	}

	@Override
	public void validate() {
		super.validate();
		// 组件类资源必须指定code
		if(ResourceTypeEnum.toEnum(resource.getType()) == ResourceTypeEnum.COMPONENT_RESOURCE){
			if(StringUtils.isEmpty(resource.getCode())){
				this.addActionError("必须指定资源code!");
			}
		}
	}
}
