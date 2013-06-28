package com.smrs.basicdata.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户状态枚举
 * @author jonathan
 *
 */
public enum DictTypeEnum {
	AREA_PROVINCE("1001","省市"),AREA_CITY("1002","市"),AREA_ZONE("1003","区县");
	private static final Map<String, DictTypeEnum> CACHE = new HashMap<String, DictTypeEnum>(){
		private static final long serialVersionUID = -8986866330615001847L;
		{
			for(DictTypeEnum enu : DictTypeEnum.values()){
				put(enu.getId(), enu);
			}
		}
	};
	private String id;
	private String name;
	
	private DictTypeEnum(String status, String description) {
		this.id = status;
		this.name = description;
	}
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public static DictTypeEnum toEnum(Character status){
		return CACHE.get(status);
	}
}
