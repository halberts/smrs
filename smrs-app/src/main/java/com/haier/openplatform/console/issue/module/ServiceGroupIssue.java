package com.haier.openplatform.console.issue.module;

/**
 * @author shanjing 
 * 结果集：Service按分组统计错误数量
 */
public class ServiceGroupIssue {

	/**
	 * 应用系统名称
	 */
	private String groupName;

	/**
	 * 错误数量
	 */
	private Long issueNum;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Long getIssueNum() {
		return issueNum;
	}

	public void setIssueNum(Long issueNum) {
		this.issueNum = issueNum;
	}

	@Override
	public String toString() {
		return "ServiceGroupIssue [groupName=" + groupName + ", issueNum=" + issueNum + "]";
	}

}
