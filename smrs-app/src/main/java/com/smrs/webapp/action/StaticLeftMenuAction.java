package com.smrs.webapp.action;


/**
 * 渲染左侧动态菜单action
 * 
 * @author WangXuzheng
 * 
 */
public class StaticLeftMenuAction extends BaseConsoleAction {
	private static final long serialVersionUID = 429284484122515865L;
	private String namespace;
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
}
