package com.jof.framework.webapp.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 分页标签
 * @author WangXuzheng
 *
 */
public class PaginationTag extends AbstractUITag{
	private static final long serialVersionUID = -2258670737433410659L;
	/**
	 * 用来接收分页参数的Pager对象变量名
	 */
	private String pager;
	/**
	 * 查询时提交查询条件的form id
	 */
	private String formId;
	public String getPager() {
		return pager;
	}
	public void setPager(String pager) {
		this.pager = pager;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest request,
			HttpServletResponse response) {
		return new Pagination(stack, request, response);
	}
	protected void populateParams() {
		super.populateParams();
		Pagination paginationComponent = (Pagination)component;
		paginationComponent.setFormId(formId);
		paginationComponent.setPager(pager);
	}
}
