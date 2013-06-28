package com.smrs.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户状态枚举
 * @author WangXuzheng
 *
 */
public enum GenderTypeEnum {
	MALE('M',"男"),FEMALE('F',"女");
	private static final Map<Character, GenderTypeEnum> CACHE = new HashMap<Character, GenderTypeEnum>(){
		private static final long serialVersionUID = -8986866330615001847L;
		{
			for(GenderTypeEnum enu : GenderTypeEnum.values()){
				put(enu.id, enu);
			}
		}
	};
	private Character id;
	private String name;
	
	public Character getId() {
		return id;
	}


	public void setId(Character id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	private GenderTypeEnum(Character status, String description) {
		this.id = status;
		this.name = description;
	}

	
	public static GenderTypeEnum toEnum(Character status){
		return CACHE.get(status);
	}
}
