package com.haier.openplatform.console.issue.domain.enu;

/**
 * Action统计维度枚举
 * @author Zhangtao
 *
 */
public enum StatisticsTimeTypeEnum {
	WEEK(1,"周"),MONTH(2,"月"),YEAR(3,"年");
	private int type;
	private String description;
	private StatisticsTimeTypeEnum(int type, String description) {
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
