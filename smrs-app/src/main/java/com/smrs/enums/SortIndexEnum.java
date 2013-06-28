package com.smrs.enums;

import java.util.HashMap;
import java.util.Map;

import com.smrs.basicdata.enums.DictTypeEnum;

public enum SortIndexEnum {
	sort01("1","1"),sort02("2","2"),sort03("3","3"),sort04("4","4"),sort05("5","5"),sort06("6","6"),sort07("7","7"),sort08("8","8"),sort09("9","9");
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
	
	private SortIndexEnum(String status, String name) {
		this.id = status;
		this.name = name;
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
