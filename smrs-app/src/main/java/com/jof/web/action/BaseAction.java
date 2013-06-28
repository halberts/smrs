package com.jof.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jof.common.JofConstant;
import com.jof.framework.util.PageInfo;
import com.opensymphony.xwork2.ActionSupport;
import com.smrs.security.model.UserModel;
import com.smrs.security.service.UserService;



public class BaseAction extends ActionSupport {
	
	protected final static String toInput="toInput";
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private static final long serialVersionUID = 1L;
	protected PageInfo pageInfo = new PageInfo();
	private Long currentPage;
	private Long pageSize = pageInfo.getPageSize();
	
	

	//private transient HttpServletRequest request;
	
	//private transient HttpSession session;
	private String menuClick;
	
	private transient UserService userService;

	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	


	public HttpSession getSession() {
		return getRequest().getSession();
	}

	public void setMenuClick(String sMenuClick) {
		menuClick = sMenuClick;
	}

	public String getMenuClick() {
		return menuClick;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String checkRole(String actionName) {
		UserModel user = (UserModel) this.getSession().getAttribute(JofConstant.USER_SESSION_KEY);
		if (this.getMenuClick() != null && "true".equals(this.getMenuClick())) {
			//logger.debug("userService = " + this.userService);
			
			/*
			String delegateResult = getUserService().getUserDelegatePrivilege(user, actionName);
			logger.debug(" delegateResult = " + delegateResult);
			this.getSession().setAttribute(JofConstant.sSESSION_ACTION_NAME,actionName);
			this.getSession().setAttribute(JofConstant.sSESSION_DELEGATE_RESULT, delegateResult);
			*/
			// this.getSession().setAttribute(SRSConstant.sSESSION_ORGANIZATION_ID,
			// user.getOrganizationId());
			/*
			if (getUserService().isNeedDelegateRedirect(delegateResult)){			
			    return "toDelegeteAction";
			}
			*/
		}
		return null;
	}
	/*
	protected void writeOutJson(Object obj) {
		//logger.debug("Obj:" + obj);
		Gson gson = new Gson();
		String result = gson.toJson(obj);
		//logger.debug("result:" + result);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control","no-store");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(result);
		} catch (IOException e) {
			//e.printStackTrace();
			logger.debug(e,e);
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}
	*/
	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public Long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Long currentPage) {
		this.pageInfo.setCurrentPage(currentPage);
		this.currentPage = currentPage;
	}

	public Long getPageSize() {
		return pageSize;
	}

	public void setPageSize(Long iPageSize) {
		if (iPageSize != null) {
			this.pageInfo.setPageSize(iPageSize);
		}
		this.pageSize = iPageSize;
	}
	
	public UserModel getUserFromSession(){
		UserModel sessionUser=null;
		HttpSession session = this.getRequest().getSession();
		Object obj = session.getAttribute(JofConstant.USER_SESSION_KEY);
		if(obj!=null){
			sessionUser=(UserModel)obj;
		}
		return sessionUser;
	}
}