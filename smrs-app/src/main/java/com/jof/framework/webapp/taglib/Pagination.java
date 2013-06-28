package com.jof.framework.webapp.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.UIBean;
import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 分页组件
 * @author WangXuzheng
 *
 */
@StrutsTag(name = "pagination", tldTagClass = "com.haier.openplatform.webapp.taglib.PaginationTag", description = "Render Pagination component", allowDynamicAttributes = true)
public class Pagination extends UIBean{
	private static final String TEMPLATE = "pagination";
	/**
	 * 用来接收分页参数的Pager对象变量名
	 */
	private String pager;
	/**
	 * 查询时提交查询条件的form id
	 */
	private String formId;
	public Pagination(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		super(stack, request, response);
	}
	public String getPager() {
		return pager;
	}
	@StrutsTagAttribute(description = "分页参数对象名称.")
	public void setPager(String pager) {
		this.pager = pager;
	}

	public String getFormId() {
		return formId;
	}
	@StrutsTagAttribute(description = "查询列表的表单 id")
	public void setFormId(String formId) {
		this.formId = formId;
	}

	@Override
	protected String getDefaultTemplate() {
		return TEMPLATE;
	}
	
	@Override
	public void evaluateExtraParams(){
		super.evaluateExtraParams();
		addParameter("pager", findValue(pager));
		addParameter("pagerName", pager);//分页对象变量名
		addParameter("formId", findString(formId));
	}
}
