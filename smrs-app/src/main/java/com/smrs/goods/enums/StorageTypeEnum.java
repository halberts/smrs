package com.smrs.goods.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 部门状态枚举列表
 * @author jonathan
 *
 */
public enum StorageTypeEnum {
	normal(1,"常规"),freeze(2,"冷藏");
	private Integer id;
	private String name;
	private StorageTypeEnum(Integer status, String description) {
		this.id = status;
		this.name = description;
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
	
	private static final Map<Integer, StorageTypeEnum> CACHE = new HashMap<Integer, StorageTypeEnum>(){
		private static final long serialVersionUID = -8986866330615001847L;
		{
			for(StorageTypeEnum enu : StorageTypeEnum.values()){
				put(enu.id, enu);
			}
		}
	};
	public static StorageTypeEnum toEnum(Integer status){
		return CACHE.get(status);
	}
}
