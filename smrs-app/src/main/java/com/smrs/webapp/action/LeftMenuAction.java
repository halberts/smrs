package com.smrs.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haier.openplatform.security.LoginContextHolder;
import com.smrs.security.model.ResourceModel;
import com.smrs.security.service.ResourceService;

/**
 * 渲染左侧动态菜单action
 * 
 * @author WangXuzheng
 * 
 */
public class LeftMenuAction extends BaseConsoleAction {
	private static final long serialVersionUID = 429284484122515865L;
	private ResourceService resourceService;
	private String namespace;
	/**
	 * 是否使用当前模块特有的左侧菜单
	 */
	private List<ResourceModel> resources = new ArrayList<ResourceModel>();
	private Map<String, String> customizedMap = new HashMap<String, String>();
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public List<ResourceModel> getResources() {
		return resources;
	}
	public void setResources(List<ResourceModel> resources) {
		this.resources = resources;
	}
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}
	
	public void setCustomizedMap(Map<String, String> customizedMap) {
		this.customizedMap = customizedMap;
	}
	@Override
	public String execute() throws Exception {
		if(customizedMap.get(this.namespace)!=null){
			return "customized";//使用该模块特有的left.jsp文件
		}
		Long userId = LoginContextHolder.get().getUserId();
		resources = resourceService.getUserDisplayedURLResources(userId,namespace);
		return SUCCESS;
	}
}
