package com.smrs.security.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 资源状态枚举
 * @author lupeng
 * 2012-1-6
 */
public enum ResourceStatusEnum {
	ACTIVE(1,"显示"),INACTIVE(0,"隐藏");
	private static final Map<Integer, ResourceStatusEnum> CACHE = new HashMap<Integer, ResourceStatusEnum>(){
		private static final long serialVersionUID = -8986866330615001847L;
		{
			for(ResourceStatusEnum enu : ResourceStatusEnum.values()){
				put(enu.getStatus(), enu);
			}
		}
	};
	private Integer status;
	private String description;
	private ResourceStatusEnum(Integer status, String description) {
		this.status = status;
		this.description = description;
	}
	public Integer getStatus() {
		return status;
	}
	public String getDescription() {
		return description;
	}
	public static ResourceStatusEnum toEnum(Integer status){
		return CACHE.get(status);
	}
}
