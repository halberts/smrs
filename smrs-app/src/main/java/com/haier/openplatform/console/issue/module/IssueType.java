package com.haier.openplatform.console.issue.module;

/**
 * @author shanjing
 * 异常类型枚举
 */
public enum IssueType {

	已知异常(1), 未知异常(2), 超时异常(3), 追踪(4), 调试(5);

	private int id;

	private IssueType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static IssueType getIssueType(int id) {
		for (IssueType it : IssueType.values()) {
			if (it.getId() == id) {
				return it;
			}
		}
		return null;
	}

}
