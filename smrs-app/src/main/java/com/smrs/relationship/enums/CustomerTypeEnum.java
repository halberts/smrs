package com.smrs.relationship.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 部门状态枚举列表
 * @author WangXuzheng
 *
 */
public enum CustomerTypeEnum {
	normal(1,"普通客户"),wholesale(2,"批发商"),agent(3,"代理商");
	private Integer id;
	private String name;
	
	private static final Map<Integer, CustomerTypeEnum> CACHE = new HashMap<Integer, CustomerTypeEnum>(){
		private static final long serialVersionUID = -8986866330615001847L;
		{
			for(CustomerTypeEnum enu : CustomerTypeEnum.values()){
				put(enu.getId(), enu);
			}
		}
	};
	private CustomerTypeEnum(Integer status, String description) {
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
	
	public static CustomerTypeEnum toEnum(Integer status){
		return CACHE.get(status);
	}
}
