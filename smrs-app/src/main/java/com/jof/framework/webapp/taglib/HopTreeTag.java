package com.jof.framework.webapp.taglib;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 树形标签
 * @author WuWeiguang
 *
 */
public class HopTreeTag extends AbstractUITag{
	
	private static final long serialVersionUID = 1989502467907571595L;

	/**
	 * 第一次获取树根节点的地址
	 */
	private String url;
	
	/**
	 * 点击某个节点时异步获取子节点的url
	 */
	private String expandUrl;
	
	/**
	 * 树形组件的id
	 */
	private String id;
	
	/**
	 * 勾选框类型
	 */
	private String chkType;
	
	/**
	 * 是否异步操作，即点击节点时是否异步请求子节点
	 */
	private Boolean async;
	
	/**
	 * 自定义配置
	 */
	private String setting;	
	
	
	
	
	public String getExpandUrl() {
		return expandUrl;
	}

	public void setExpandUrl(String expandUrl) {
		this.expandUrl = expandUrl;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChkType() {
		return chkType;
	}

	public void setChkType(String chkType) {
		this.chkType = chkType;
	}

	public Boolean getAsync() {
		return async;
	}

	public void setAsync(Boolean async) {
		this.async = async;
	}

	public String getSetting() {
		return setting;
	}

	public void setSetting(String setting) {
		this.setting = setting;
	}
	
	

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		
		return new HopTree(stack, req, res);
	}

	@Override
	protected void populateParams() {
		super.populateParams();
		
		HopTree hoptree = (HopTree)component;
		hoptree.setUrl(url);
		hoptree.setExpandUrl(expandUrl);
		hoptree.setId(id);
		hoptree.setChkType(chkType);
		hoptree.setAsync(async);
		hoptree.setSetting(setting);
		
	}
	
	
}
