package com.haier.openplatform.console.issue.module;

/**
 * @author shanjing 结果集：异常列表
 */
public class Issue {

	/**
	 * 监控ID
	 */
	private Long issueId;

	/**
	 * 异常类型
	 */
	private String issueType;

	/**
	 * 异常名称
	 */
	private String exceptionName;

	/**
	 * 对应Service名字
	 */
	private String serviceName;

	/**
	 * 异常发生的节点
	 */
	private String nodeName;

	/**
	 * 执行时间
	 */
	private Long executeTime;

	/**
	 * 实际发生时间
	 */
	private String createTime;

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public String getExceptionName() {
		return exceptionName;
	}

	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public Long getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(Long executeTime) {
		this.executeTime = executeTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Issue [issueId=" + issueId + ", issueType=" + issueType + ", exceptionName=" + exceptionName
				+ ", serviceName=" + serviceName + ", nodeName=" + nodeName + ", executeTime=" + executeTime
				+ ", createTime=" + createTime + "]";
	}

}
