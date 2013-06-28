package com.haier.openplatform.console.issue.module;

import java.util.List;

/**
 * 
 * @author AaronG
 * 
 */
public class IssueSummaryModel {
	private List<Long> issueIds;
	private long count;
	private double duration;
	private String apiName;
	private String ownerName;
	private String ownerEmail;

	public IssueSummaryModel(String apiName, String ownerName,
			String ownerEmail, long count, double duration, List<Long> issueIds) {
		this.apiName = apiName;
		this.ownerName = ownerName;
		this.ownerEmail = ownerEmail;
		this.count = count;
		this.duration = duration;
		this.issueIds = issueIds;
	}
	
	public void setCount(long count) {
		this.count = count;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public String getOwnerName() {
		return ownerName;
	}
	
	public String getOwnerEmail() {
		return ownerEmail;
	}

	public String getApiName() {
		return apiName;
	}

	public List<Long> getIssueIds() {
		return issueIds;
	}

	public long getCount() {
		return count;
	}

	public double getDuration() {
		return duration;
	}

}
