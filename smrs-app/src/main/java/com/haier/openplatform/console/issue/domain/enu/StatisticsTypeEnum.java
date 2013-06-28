package com.haier.openplatform.console.issue.domain.enu;

/**
 * 系统信息统计维度枚举
 * @author Zhangtao
 *
 */
public enum StatisticsTypeEnum {
	CLASS(1,"class"),SONAR(2,"sonar");
	private int type;
	private String description;
	private StatisticsTypeEnum(int type, String description) {
		this.type = type;
		this.description = description;
	}
	public int getType() {
		return type;
	}
	public String getDescription() {
		return description;
	}
}
