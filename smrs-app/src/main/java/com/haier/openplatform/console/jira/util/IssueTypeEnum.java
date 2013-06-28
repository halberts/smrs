package com.haier.openplatform.console.jira.util;

public enum IssueTypeEnum {

	ISSUE_CONSOLE_EXCEPTION("21","监控异常"),ISSUE_SERVER_HEALTH("22","服务器健康"),
	ISSUE_AVERAGE_TIME("23","平均响应时间"),ISSUE_CODE_SPECIFICATION("24","代码规范问题"),
	ISSUE_TEST_COVER("25","测试覆盖率"),ISSUE_TEST_SUCCESS("26","测试成功率"),
	ISSUE_CODE_NOTE("27","代码注释问题"),ISSUE_CODE_DUPLICATION("28","代码重复问题");
	
	private String id;
	private String description;
	private IssueTypeEnum(String id, String description) {
		this.id = id;
		this.description = description;
	}
	public String getId() {
		return id;
	}
	public String getDescription() {
		return description;
	}
}
