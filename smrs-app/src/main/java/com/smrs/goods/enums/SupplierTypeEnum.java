package com.smrs.goods.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 部门状态枚举列表
 * @author WangXuzheng
 *
 */
public enum SupplierTypeEnum {
	normal(1,"普通客户"),wholesale(2,"批发商"),agent(3,"代理商");
	private Integer id;
	private String name;
	
	private static final Map<Integer, SupplierTypeEnum> CACHE = new HashMap<Integer, SupplierTypeEnum>(){
		private static final long serialVersionUID = -8986866330615001847L;
		{
			for(SupplierTypeEnum enu : SupplierTypeEnum.values()){
				put(enu.getId(), enu);
			}
		}
	};
	private SupplierTypeEnum(Integer status, String description) {
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
	
	public static SupplierTypeEnum toEnum(Integer status){
		return CACHE.get(status);
	}
}
