package com.smrs.basicdata.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户状态枚举
 * @author WangXuzheng
 *
 */
public enum StoreLevelEnum {
	A("A","A级"),B("B","B级"),C("C","C级"),D("D","D级"),E("E","E级");
	private static final Map<String, StoreLevelEnum> CACHE = new HashMap<String, StoreLevelEnum>(){
		private static final long serialVersionUID = -8986866330615001847L;
		{
			for(StoreLevelEnum enu : StoreLevelEnum.values()){
				put(enu.getId(), enu);
			}
		}
	};
	private String id;
	private String name;
	
	private StoreLevelEnum(String status, String description) {
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


	public static StoreLevelEnum toEnum(Character status){
		return CACHE.get(status);
	}
}
