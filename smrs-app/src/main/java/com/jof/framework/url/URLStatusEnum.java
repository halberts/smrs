package com.jof.framework.url;

import java.util.HashMap;
import java.util.Map;

public enum URLStatusEnum {
	
	ENABLE(1,"可用"),DISABLE(2,"禁止");

	private static final Map<Integer,URLStatusEnum> MAP = new HashMap<Integer,URLStatusEnum>(){
		private static final long serialVersionUID = 1802140338455124215L;
		{
			for(URLStatusEnum enu : URLStatusEnum.values()){
				put(enu.getStatus(),enu);
			}
		}
	};
	private Integer status;
	private String desc;
	
	private URLStatusEnum(Integer status,String desc){
		this.status = status;
		this.desc = desc;
	}
	
	public static URLStatusEnum toEnum(Integer status){
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
