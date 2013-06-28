package com.smrs.basicdata.enums;

/**
 * 部门状态枚举列表
 * @author WangXuzheng
 *
 */
public enum StoreTypeEnum {
	major(1,"总仓"),region(2,"区域仓"),normal(3,"门店");
	private Integer id;
	private String description;
	private StoreTypeEnum(Integer status, String description) {
		this.id = status;
		this.description = description;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}
}
