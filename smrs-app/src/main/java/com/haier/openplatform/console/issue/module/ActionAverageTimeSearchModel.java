package com.haier.openplatform.console.issue.module;

import java.util.Date;
import java.util.List;

import com.haier.openplatform.util.SearchModel;

/**
 * Action平均时间搜索模型
 * @author WangJian
 *
 */
public class ActionAverageTimeSearchModel extends SearchModel<ActionAverageTime> {
	private static final long serialVersionUID = -2157939733333326567L;
	private long appId;
	private String className;
	private String methodName;
	private int type;
	private Date from;
	private Date to;
	//为了一次计算多个method的平均响应时间
	private List<ActionAverageTimeSearchModel> searchModelList;
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
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public List<ActionAverageTimeSearchModel> getSearchModelList() {
		return searchModelList;
	}
	public void setSearchModelList(
			List<ActionAverageTimeSearchModel> searchModelList) {
		this.searchModelList = searchModelList;
	}
	
}
