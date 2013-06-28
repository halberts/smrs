package com.smrs.goods.enums;

import java.util.HashMap;
import java.util.Map;

import com.smrs.relationship.enums.CustomerLevelEnum;

/**
 * 部门状态枚举列表
 * @author WangXuzheng
 *
 */
public enum PhysicalTypeEnum {
	solid(1,"固体"),liquid(2,"液体"),gas(3,"气体");
	
	private static final Map<Integer, PhysicalTypeEnum> CACHE = new HashMap<Integer, PhysicalTypeEnum>(){
		private static final long serialVersionUID = -8986866330615001847L;
		{
			for(PhysicalTypeEnum enu : PhysicalTypeEnum.values()){
				put(enu.id, enu);
			}
		}
	};
	public static PhysicalTypeEnum toEnum(Integer status){
		return CACHE.get(status);
	}
	
	private Integer id;
	private String name;
	private PhysicalTypeEnum(Integer status, String description) {
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
	
	
}
