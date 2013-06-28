package com.smrs.webapp.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.jof.framework.security.SessionSecurityConstants;
import com.jof.framework.webapp.action.BaseAppAction;
import com.smrs.basicdata.service.DepartmentService;
import com.smrs.enums.SortIndexEnum;
import com.smrs.enums.StatusEnum;
import com.smrs.logtrace.service.LogTraceService;
import com.smrs.model.BaseModel;
import com.smrs.security.model.UserModel;
import com.smrs.security.service.GroupService;
import com.smrs.security.service.ResourceService;
import com.smrs.security.service.RoleService;
import com.smrs.security.service.UserService;
import com.smrs.util.DictConstants;

/**
 * 
 * @author jonathan
 *
 */
public class BaseConsoleAction extends BaseAppAction {
	private static final long serialVersionUID = 8161343308502020496L;
	protected Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 模块列表
	 */
	protected Map<String, String> modules;
	protected UserService userService;
	protected ResourceService resourceService;
	protected RoleService roleService;
	protected DepartmentService departmentService;
	protected GroupService groupService;
	protected String actionCommand;
	protected LogTraceService logTraceService;
	
	protected Map<Character, String> statusMap=DictConstants.getInstance().getStatusMap();
	protected List<StatusEnum> statusList = DictConstants.getInstance().getStatusList();
	
	protected List<SortIndexEnum> sortIndexList = DictConstants.getInstance().getSortIndexList();
	
	public String getActionCommand() {
		return actionCommand;
	}
	public void setActionCommand(String actionCommand) {
		this.actionCommand = actionCommand;
	}
	
	public List<SortIndexEnum> getSortIndexList() {
		return sortIndexList;
	}

	public Map<Character, String> getStatusMap() {
		return statusMap;
	}

	public List<StatusEnum> getStatusList(){
		return statusList;
	}

	protected String SEARCH="search"; 
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	public Map<String, String> getModules() {
		return modules;
	}
	public void setModules(Map<String, String> modules) {
		this.modules = modules;
	}
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
	
	
	
	public void setLogTraceService(LogTraceService logTraceService) {
		this.logTraceService = logTraceService;
	}
	public UserModel getUserModelFromSession(){
		UserModel userModel = (UserModel)this.getSession().getAttribute(SessionSecurityConstants.KEY_USER_MODEL);
		return userModel;
	}
	
	protected void writeOutJson(Object obj) {
		//logger.debug("Obj:" + obj);
		Gson gson = new Gson();
		String resultJson = gson.toJson(obj);
		//logger.debug("result:" + result);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control","no-store");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(resultJson);
		} catch (IOException e) {
			//e.printStackTrace();
			//logger.debug(e,e);
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}
	

}
