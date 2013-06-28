package com.smrs.security.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户账号类型
 * @author WangXuzheng
 *
 */
public enum ClerkTypeEnum {
	//ACTIVE(1,"域账号"),INACTIVE(0,"普通用户");
	REGULAR(1,"固定店员"),FLOATING(2,"流动店员");
	
	
	private static final Map<Integer, ClerkTypeEnum> CACHE = new HashMap<Integer, ClerkTypeEnum>(){
		private static final long serialVersionUID = -8986866330615001847L;
		{
			for(ClerkTypeEnum enu : ClerkTypeEnum.values()){
				put(enu.id, enu);
			}
		}
	};
	private Integer id;
	private String name;
	private ClerkTypeEnum(Integer id, String name) {
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


	public static ClerkTypeEnum toEnum(Integer status){
		return CACHE.get(status);
	}
}
