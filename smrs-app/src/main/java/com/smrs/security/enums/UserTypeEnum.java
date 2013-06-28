package com.smrs.security.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户账号类型
 * @author WangXuzheng
 *
 */
public enum UserTypeEnum {
	//ACTIVE(1,"域账号"),INACTIVE(0,"普通用户");
	NORMAL(1,"普通店员"),MANAGER(2,"管理人员");
	
	
	private static final Map<Integer, UserTypeEnum> CACHE = new HashMap<Integer, UserTypeEnum>(){
		private static final long serialVersionUID = -8986866330615001847L;
		{
			for(UserTypeEnum enu : UserTypeEnum.values()){
				put(enu.id, enu);
			}
		}
	};
	private Integer id;
	private String name;
	private UserTypeEnum(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public static UserTypeEnum toEnum(Integer status){
		return CACHE.get(status);
	}
}
