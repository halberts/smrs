package com.smrs.basicdata.enums;

/**
 * 部门状态枚举列表
 * @author WangXuzheng
 *
 */
public enum DepartmentStatusEnum {
	ENABLED(1,"启用"),DISABLED(0,"禁用");
	private int status;
	private String description;
	private DepartmentStatusEnum(int status, String description) {
		this.status = status;
		this.description = description;
	}
	public int getStatus() {
		return status;
	}
	public String getDescription() {
		return description;
	}
}
