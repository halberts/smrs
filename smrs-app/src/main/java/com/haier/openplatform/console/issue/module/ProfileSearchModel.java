package com.haier.openplatform.console.issue.module;

import java.util.Date;

import com.haier.openplatform.console.issue.domain.ProfileInfo;
import com.haier.openplatform.util.SearchModel;

/**
 * @author WangXuzheng
 *
 */
public class ProfileSearchModel extends SearchModel<ProfileInfo> {
	private static final long serialVersionUID = -7059018957643372437L;
	private long appId;
	private int type;
	private Date from;
	private Date to;
	private String className;
	public long getAppId() {
		return appId;
	}
	public void setAppId(long appId) {
		this.appId = appId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public Date getTo() {
		return to;
	}
	public void setTo(Date to) {
		this.to = to;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
}
