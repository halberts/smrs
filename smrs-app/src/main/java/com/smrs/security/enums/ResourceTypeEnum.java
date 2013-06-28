package com.smrs.security.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 资源类型枚举
 * @author WangXuzheng
 *
 */
public enum ResourceTypeEnum {
	URL_RESOURCE("0","URL资源"),COMPONENT_RESOURCE("1","组件资源");
	private static final Map<String,ResourceTypeEnum> CACHE = new HashMap<String,ResourceTypeEnum>(){
		private static final long serialVersionUID = 2334886698187804809L;
		{
			for(ResourceTypeEnum typeEnum : ResourceTypeEnum.values()){
				put(typeEnum.getType(), typeEnum);
			}
		}
	};
	private String type;
	private String description;
	private ResourceTypeEnum(String type,String description){
		this.type = type;
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public String getDescription() {
		return description;
	}
	
	public static ResourceTypeEnum toEnum(String type){
		return CACHE.get(type);
	}
	
	public static boolean isValidType(String type){
		return toEnum(type)!= null;
	}
}
