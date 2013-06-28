package com.smrs.relationship.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户状态枚举
 * @author WangXuzheng
 *
 */
public enum CustomerLevelEnum {
	A('A',"A"),B('B',"B"),C('C',"C"),D('D',"D"),E('E',"E");
	private static final Map<Character, CustomerLevelEnum> CACHE = new HashMap<Character, CustomerLevelEnum>(){
		private static final long serialVersionUID = -8986866330615001847L;
		{
			for(CustomerLevelEnum enu : CustomerLevelEnum.values()){
				put(enu.id, enu);
			}
		}
	};
	private Character id;
	private String name;
	
	public Character getId() {
		return id;
	}


	public void setId(Character id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	private CustomerLevelEnum(Character status, String description) {
		this.id = status;
		this.name = description;
	}

	
	public static CustomerLevelEnum toEnum(Character status){
		return CACHE.get(status);
	}
}
