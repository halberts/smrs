package com.jof.framework.webapp.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.UIBean;
import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

import com.opensymphony.xwork2.util.ValueStack;


/**
 * 树形组件
 * @author WuWeiguang
 *
 */

@StrutsTag(name = "hoptree", tldTagClass = "com.haier.openplatform.webapp.taglib.HopTreeTag", description = "Render Treeview component", allowDynamicAttributes = true)
public class HopTree extends UIBean {
	
	private static final String TEMPLATE = "hoptree";
	
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
	
	public HopTree(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
	}
	
	public String getUrl() {
		return url;
	}

	@StrutsTagAttribute(description = "首次获取所有节点的地址")
	public void setUrl(String url) {
		this.url = url;
	}

	public String getExpandUrl() {
		return expandUrl;
	}

	@StrutsTagAttribute(description = "展开节点时异步请求的地址")
	public void setExpandUrl(String expandUrl) {
		this.expandUrl = expandUrl;
	}

	public String getId() {
		return id;
	}
	
	@StrutsTagAttribute(description = "树形标签id")
	public void setId(String id) {
		this.id = id;
	}

	public String getChkType() {
		return chkType;
	}

	@StrutsTagAttribute(description = "勾选框类型")
	public void setChkType(String chkType) {
		this.chkType = chkType;
	}

	public Boolean getAsync() {
		return async;
	}

	@StrutsTagAttribute(description = "展开节点时是否支持异步请求")
	public void setAsync(Boolean async) {
		this.async = async;
	}

	public String getSetting() {
		return setting;
	}

	@StrutsTagAttribute(description = "自定义树形配置")
	public void setSetting(String setting) {
		this.setting = setting;
	}


	@Override
	protected String getDefaultTemplate() {
		return TEMPLATE;
	}

	@Override
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
		addParameter("url", findString(url));
		addParameter("expandUrl", findString(expandUrl));
		addParameter("id", findString(id));
		addParameter("chkType", findString(chkType));
		addParameter("async", findString(String.valueOf(async)));
		if(setting != null) {
			addParameter("setting", findString(setting));
		} else {
			addParameter("setting", "");
		}
		
		
	}

	
	
	
}
