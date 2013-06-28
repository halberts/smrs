package com.jof.framework.url;

import java.util.HashMap;
import java.util.Map;

public enum URLCheckStatusEnum {
	
	NOT_CHECK(1,"未检测"),ACTIVE(2,"正常"),EXCEPTION(3,"异常");

	private static final Map<Integer,URLCheckStatusEnum> MAP = new HashMap<Integer,URLCheckStatusEnum>(){
		private static final long serialVersionUID = 1802140338455124215L;
		{
			for(URLCheckStatusEnum enu : URLCheckStatusEnum.values()){
				put(enu.getStatus(),enu);
			}
		}
	};
	private Integer status;
	private String desc;
	
	private URLCheckStatusEnum(Integer status,String desc){
		this.status = status;
		this.desc = desc;
	}
	
	public static URLCheckStatusEnum toEnum(Integer status){
		return MAP.get(status);
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
