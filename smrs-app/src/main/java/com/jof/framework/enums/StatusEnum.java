package com.jof.framework.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户状态枚举
 * @author WangXuzheng
 *
 */
public enum StatusEnum {
	ACTIVE('Y',"正常"),INACTIVE('N',"禁用");
	private static final Map<Character, StatusEnum> CACHE = new HashMap<Character, StatusEnum>(){
		private static final long serialVersionUID = -8986866330615001847L;
		{
			for(StatusEnum enu : StatusEnum.values()){
				put(enu.getStatus(), enu);
			}
		}
	};
	private Character id;
	private String name;
	
	
	public Character getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	private StatusEnum(Character status, String description) {
		this.id = status;
		this.name = description;
	}
	
	public Character getStatus() {
		return id;
	}
	public String getDescription() {
		return name;
	}
	
	public static StatusEnum toEnum(Character status){
		return CACHE.get(status);
	}
}
