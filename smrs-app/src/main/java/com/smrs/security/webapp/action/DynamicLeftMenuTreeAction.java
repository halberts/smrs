package com.smrs.security.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haier.openplatform.security.LoginContextHolder;
import com.smrs.security.model.ResourceModel;
import com.smrs.webapp.action.BaseConsoleAction;


/**
 * 渲染左侧动态菜单action
 * 
 * @author WangXuzheng
 * 
 */
public class DynamicLeftMenuTreeAction extends BaseConsoleAction {
	private static final long serialVersionUID = -9178755738368005178L;
	private List<String> codeList = new ArrayList<String>();
	private Map<ResourceModel, List<ResourceModel>> resourceMap = new HashMap<ResourceModel, List<ResourceModel>>();
	public List<String> getCodeList() {
		return codeList;
	}
	public void setCodeList(List<String> codeList) {
		this.codeList = codeList;
	}
	public Map<ResourceModel, List<ResourceModel>> getResourceMap() {
		return resourceMap;
	}
	public void setResourceMap(Map<ResourceModel, List<ResourceModel>> resourceMap) {
		this.resourceMap = resourceMap;
	}

	@Override
	public String execute() throws Exception {
		Long userId = LoginContextHolder.get().getUserId();
		this.resourceMap = resourceService.getUserResourceDescendants(userId, codeList);
		return SUCCESS;
	}
}
