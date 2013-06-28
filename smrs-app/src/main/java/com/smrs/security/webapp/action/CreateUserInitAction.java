package com.smrs.security.webapp.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.smrs.security.model.RoleModel;
import com.smrs.security.model.UserModel;

/**
 * @author jonathan_bian
 *
 */
public class CreateUserInitAction extends BaseSecurityUserAction {
	private static final long serialVersionUID = -2063916068066578057L;
	
	private List<RoleModel> roles;
	
	public List<RoleModel> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleModel> roles) {
		this.roles = roles;
	}



	@Override
	public String execute() throws Exception {
		allGroupList = groupService.getAllActiveGroupList();
		//roles = roleService.getRoles();
		//user.getRoles().addAll(roles);
		Date date = new Date();  
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd hh:mm"); 
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 365);
		Date endDate = dft.parse(dft.format(calendar.getTime()));
		user.setExpiredTime(endDate); 
		return SUCCESS;
	}
}