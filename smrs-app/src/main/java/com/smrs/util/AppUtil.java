package com.smrs.util;

import java.util.Date;

import com.smrs.model.BaseModel;
import com.smrs.security.model.UserModel;

public class AppUtil {
	public static void setCreateUserInfo(UserModel userModel,BaseModel<Long> model){
		model.setCreationDate(new Date());
		model.setCreator(userModel.getName());
	}
	
	public static void setUpdateUserInfo(UserModel userModel,BaseModel<Long> model){
		model.setModifiedBy(userModel.getName());
		model.setLastModifyDate(new Date());
	}
	
}
