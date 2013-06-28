package com.smrs.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户状态枚举
 * @author WangXuzheng
 *
 */
public enum ActionCommandEnum {
	update("update"),add("add"),delete("delete"),query("query"),search("search"),create("create");
	
	private static final Map<String, ActionCommandEnum> CACHE = new HashMap<String, ActionCommandEnum>(){
	private static final long serialVersionUID = -8986866330615001847L;
		{
			for(ActionCommandEnum enu : ActionCommandEnum.values()){
				put(enu.getAction(), enu);
			}
		}
	};
	private String action;
	
	
	private ActionCommandEnum(String status) {
		this.action = status;
		
	}
	public String getAction() {
		return action;
	}
	
	public static ActionCommandEnum toEnum(String status){
		return CACHE.get(status);
	}
	
}
