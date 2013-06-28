package com.smrs.security.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户状态枚举
 * @author WangXuzheng
 *
 */
public enum UserStatusEnum {
	ACTIVE("Y","启用"),INACTIVE("N","禁用");
	private static final Map<String, UserStatusEnum> CACHE = new HashMap<String, UserStatusEnum>(){
		private static final long serialVersionUID = -8986866330615001847L;
		{
			for(UserStatusEnum enu : UserStatusEnum.values()){
				put(enu.getStatus(), enu);
			}
		}
	};
	private String status;
	private String description;
	private UserStatusEnum(String status, String description) {
		this.status = status;
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public String getDescription() {
		return description;
	}
	public static UserStatusEnum toEnum(String status){
		return CACHE.get(status);
	}
}
