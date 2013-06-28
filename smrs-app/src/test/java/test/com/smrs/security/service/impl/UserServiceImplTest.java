package test.com.smrs.security.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import test.com.smrs.BaseTestCase;

import com.haier.openplatform.test.dbunit.annotation.IUnitDataSet;
import com.jof.framework.util.ExecuteResult;
import com.smrs.security.model.GroupModel;
import com.smrs.security.model.UserModel;
import com.smrs.security.service.UserService;

@IUnitDataSet
public class UserServiceImplTest extends BaseTestCase{
	
	@Autowired
	UserService userService;
	
	//@Test
	public void testLogin(){
		String userName="admin";
		String password="11111";
		String ipAddress="127.0.0.1";
		ExecuteResult<UserModel> result = userService.login(userName, password, ipAddress);		
	}
	
	@Test
	public void testUpdateUser(){
		String userName="admin";
		String password="11111";
		String ipAddress="127.0.0.1";
		UserModel userModel = userService.getUserById(11l);
		GroupModel model = new GroupModel();
		model.setId(1l);
		Set<GroupModel> groups = new HashSet<GroupModel>();
		groups.add(model);
		userModel.setGroups(groups);
		//userModel.setClerkType(clerkType);
		userModel.setCreationDate(new Date());
		userService.updateUser(userModel);
		
		//ExecuteResult<UserModel> result = userService.login(userName, password, ipAddress);		
	}

	
}
