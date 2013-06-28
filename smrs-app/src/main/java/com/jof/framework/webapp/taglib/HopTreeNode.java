package com.jof.framework.webapp.taglib;

import java.util.Collection;

public class HopTreeNode {
	private String id;	// id标识符
	private String title;	// 显示名称
	private Boolean isParent;	// 是否显示为文件夹
	private String icon;	// 自定义图标
	private Boolean checked;	// 是否选中
	private Collection<HopTreeNode> children;	// 子节点
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public Collection<HopTreeNode> getChildren() {
		return children;
	}
	public void setChildren(Collection<HopTreeNode> children) {
		this.children = children;
	}
	
	
	
}
