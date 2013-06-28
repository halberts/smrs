package com.smrs.logtrace.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户状态枚举
 * @author jonathan
 *
 */
public enum LogTraceTypeEnum {
	ALL('A',"所有日志"), LOGIN('L',"登录日志"),SMS('S',"短信SMS日志"),GOODS('G',"产品日志");
	private static final Map<Character, LogTraceTypeEnum> CACHE = new HashMap<Character, LogTraceTypeEnum>(){
		private static final long serialVersionUID = -8986866330615001847L;
		{
			for(LogTraceTypeEnum enu : LogTraceTypeEnum.values()){
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

	private LogTraceTypeEnum(Character status, String description) {
		this.id = status;
		this.name = description;
	}
	
	public Character getStatus() {
		return id;
	}
	public String getDescription() {
		return name;
	}
	
	public static LogTraceTypeEnum toEnum(Character status){
		return CACHE.get(status);
	}
}
